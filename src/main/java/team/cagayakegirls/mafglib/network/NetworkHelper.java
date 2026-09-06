package team.cagayakegirls.mafglib.network;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * Bridges initialization-time malilib registrations to NeoForge's payload
 * registration event. Payload handling itself uses NeoForge's native types.
 */
public final class NetworkHelper
{
    private static final String NETWORK_VERSION = "1";
    private static final Map<Identifier, PendingPayload<?>> PAYLOADS = new LinkedHashMap<>();
    private static final Map<Identifier, IPayloadHandler<?>> CLIENT_HANDLERS = new LinkedHashMap<>();
    private static final Map<Identifier, IPayloadHandler<?>> SERVER_HANDLERS = new LinkedHashMap<>();
    private static boolean frozen;

    private NetworkHelper() {}

    public static <T extends CustomPacketPayload> void playToServer(
            CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec
    ) {
        register(type, codec, false, true);
    }

    public static <T extends CustomPacketPayload> void playToClient(
            CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec
    ) {
        register(type, codec, true, false);
    }

    public static <T extends CustomPacketPayload> void playBidirectional(
            CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec
    ) {
        register(type, codec, true, true);
    }

    public static <T extends CustomPacketPayload> boolean registerClientHandler(
            CustomPacketPayload.Type<T> type, IPayloadHandler<T> handler
    ) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(handler, "handler");

        PendingPayload<?> pending = PAYLOADS.get(type.id());
        if (pending == null || !pending.clientbound)
        {
            throw new IllegalArgumentException("Payload type " + type.id() + " has not been registered as clientbound");
        }

        return CLIENT_HANDLERS.putIfAbsent(type.id(), handler) == null;
    }

    public static void unregisterClientHandler(Identifier id) {
        CLIENT_HANDLERS.remove(id);
    }

    public static <T extends CustomPacketPayload> boolean registerServerHandler(
            CustomPacketPayload.Type<T> type, IPayloadHandler<T> handler
    ) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(handler, "handler");

        PendingPayload<?> pending = PAYLOADS.get(type.id());
        if (pending == null || !pending.serverbound) {
            throw new IllegalArgumentException("Payload type " + type.id() + " has not been registered as serverbound");
        }

        return SERVER_HANDLERS.putIfAbsent(type.id(), handler) == null;
    }

    public static void unregisterServerHandler(Identifier id) {
        SERVER_HANDLERS.remove(id);
    }

    public static boolean isServerboundRegistered(Identifier id) {
        PendingPayload<?> pending = PAYLOADS.get(id);
        return pending != null && pending.serverbound;
    }

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(NETWORK_VERSION).optional();

        for (PendingPayload<?> pending : PAYLOADS.values()) {
            registerWithNeoForge(registrar, pending);
        }

        frozen = true;
    }

    private static <T extends CustomPacketPayload> void register(
            CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            boolean clientbound, boolean serverbound
    ) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(codec, "codec");

        if (frozen)
        {
            throw new IllegalStateException("Cannot register payload " + type.id() + " after NeoForge's registration event");
        }
        if (PAYLOADS.containsKey(type.id()))
        {
            throw new IllegalArgumentException("Payload type " + type.id() + " is already registered");
        }

        PAYLOADS.put(type.id(), new PendingPayload<>(type, codec, clientbound, serverbound));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void registerWithNeoForge(PayloadRegistrar registrar, PendingPayload<?> pending) {
        CustomPacketPayload.Type type = pending.type;
        StreamCodec codec = pending.codec;
        IPayloadHandler serverHandler = NetworkHelper::dispatchServerbound;
        IPayloadHandler clientHandler = NetworkHelper::dispatchClientbound;

        if (pending.clientbound && pending.serverbound)
        {
            registrar.playBidirectional(type, codec, serverHandler, clientHandler);
        }
        else if (pending.clientbound)
        {
            registrar.playToClient(type, codec, clientHandler);
        }
        else
        {
            registrar.playToServer(type, codec, serverHandler);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends CustomPacketPayload> void dispatchClientbound(T payload, IPayloadContext context) {
        IPayloadHandler<T> handler;

        synchronized (NetworkHelper.class)
        {
            handler = (IPayloadHandler<T>) CLIENT_HANDLERS.get(payload.type().id());
        }

        if (handler != null)
        {
            handler.handle(payload, context);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends CustomPacketPayload> void dispatchServerbound(T payload, IPayloadContext context) {
        IPayloadHandler<T> handler;

        synchronized (NetworkHelper.class)
        {
            handler = (IPayloadHandler<T>) SERVER_HANDLERS.get(payload.type().id());
        }

        if (handler != null)
        {
            handler.handle(payload, context);
        }
    }

    private record PendingPayload<T extends CustomPacketPayload>(
            CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            boolean clientbound, boolean serverbound
    ) {
    }
}
