package team.cagayakegirls.mafglib.network;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fi.dy.masa.malilib.network.IPluginClientPlayHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import fi.dy.masa.malilib.MaLiLib;

/**
 * Bridges the Fabric-style MaLiLib networking API to NeoForge's event-based
 * payload registration lifecycle.
 */
public final class FoxifiedPlayPayloadRegistry
{
    private static final String PROTOCOL_VERSION = "1";
    private static final Map<Identifier, PayloadRegistration<?>> PAYLOADS = new ConcurrentHashMap<>();
    private static final Map<Identifier, IPayloadHandler<?>> CLIENT_HANDLERS = new ConcurrentHashMap<>();
    private static volatile boolean payloadRegistrationClosed;

    private FoxifiedPlayPayloadRegistry() {}

    public static void registerEvents(IEventBus modEventBus) {
        modEventBus.addListener(FoxifiedPlayPayloadRegistry::registerPayloads);
        modEventBus.addListener(FoxifiedPlayPayloadRegistry::registerClientHandlers);
    }

    public static <T extends CustomPacketPayload> boolean registerPayload(
            CustomPacketPayload.Type<T> id,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            int direction)
    {
        if (payloadRegistrationClosed)
        {
            MaLiLib.LOGGER.error("registerPlayPayload: channel ID [{}] was registered after the NeoForge payload registration event", id.id());
            return false;
        }

        return PAYLOADS.putIfAbsent(id.id(), new PayloadRegistration<>(id, codec, direction)) == null;
    }

    public static <T extends CustomPacketPayload> boolean registerClientHandler(
            CustomPacketPayload.Type<T> id,
            IPayloadHandler<T> handler)
    {
        PayloadRegistration<?> registration = PAYLOADS.get(id.id());

        if (registration == null || isClientbound(registration.direction()) == false)
        {
            return false;
        }

        return CLIENT_HANDLERS.putIfAbsent(id.id(), handler) == null;
    }

    public static void unregisterClientHandler(Identifier channel)
    {
        // NeoForge registrations are permanent. Removing the dispatch target
        // provides the same observable behaviour without mutating its registry.
        CLIENT_HANDLERS.remove(channel);
    }

    public static void registerPayloads(RegisterPayloadHandlersEvent event)
    {
        payloadRegistrationClosed = true;
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION).optional();

        for (PayloadRegistration<?> registration : PAYLOADS.values())
        {
            registerPayload(registrar, registration);
        }
    }

    public static void registerClientHandlers(RegisterClientPayloadHandlersEvent event)
    {
        for (PayloadRegistration<?> registration : PAYLOADS.values())
        {
            if (isClientbound(registration.direction()))
            {
                registerClientHandler(event, registration);
            }
        }
    }

    private static boolean isClientbound(int direction)
    {
        return direction != IPluginClientPlayHandler.TO_SERVER &&
               direction != IPluginClientPlayHandler.FROM_CLIENT;
    }

    private static <T extends CustomPacketPayload> void registerPayload(
            PayloadRegistrar registrar,
            PayloadRegistration<T> registration)
    {
        switch (registration.direction())
        {
            case IPluginClientPlayHandler.TO_SERVER, IPluginClientPlayHandler.FROM_CLIENT ->
                    registrar.playToServer(registration.id(), registration.codec(), FoxifiedPlayPayloadRegistry::ignoreServerboundPayload);
            case IPluginClientPlayHandler.FROM_SERVER, IPluginClientPlayHandler.TO_CLIENT ->
                    registrar.playToClient(registration.id(), registration.codec());
            default ->
                    registrar.playBidirectional(registration.id(), registration.codec(), FoxifiedPlayPayloadRegistry::ignoreServerboundPayload);
        }
    }

    private static <T extends CustomPacketPayload> void registerClientHandler(
            RegisterClientPayloadHandlersEvent event,
            PayloadRegistration<T> registration)
    {
        event.register(registration.id(), FoxifiedPlayPayloadRegistry::dispatchClientPayload);
    }

    @SuppressWarnings("unchecked")
    private static <T extends CustomPacketPayload> void dispatchClientPayload(T payload, IPayloadContext context)
    {
        IPayloadHandler<T> handler = (IPayloadHandler<T>) CLIENT_HANDLERS.get(payload.type().id());

        if (handler != null)
        {
            handler.handle(payload, context);
        }
    }

    private static <T extends CustomPacketPayload> void ignoreServerboundPayload(T payload, IPayloadContext context)
    {
        // This API represents client handlers. A dedicated server-side handler,
        // when present, is registered by the server-side mod implementation.
    }

    private record PayloadRegistration<T extends CustomPacketPayload>(
            CustomPacketPayload.Type<T> id,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            int direction)
    {
    }
}
