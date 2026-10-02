package team.cagayakegirls.mafglib;

import fi.dy.masa.malilib.MaLiLib;
import fi.dy.masa.malilib.compat.modmenu.ModMenuImpl;
import team.cagayakegirls.mafglib.network.NeoForgeClientNetworking;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = MaFgLib.MOD_ID, dist = Dist.CLIENT)
public class MaFgLib {
    public static final String MOD_ID = "mafglib";

    public MaFgLib(IEventBus modEventBus, ModContainer modContainer) {
        NeoForgeClientNetworking.initialize(modEventBus);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, new ModMenuImpl().getModConfigScreenFactory());
        new MaLiLib().onInitialize();
    }
}
