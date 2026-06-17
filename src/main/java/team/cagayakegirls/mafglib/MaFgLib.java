package team.cagayakegirls.mafglib;

import fi.dy.masa.malilib.MaLiLib;
import fi.dy.masa.malilib.compat.modmenu.ModMenuImpl;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = MaFgLib.MOD_ID, dist = Dist.CLIENT)
public class MaFgLib {
    public static final String MOD_ID = "mafglib";

    public MaFgLib(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, new ModMenuImpl().getModConfigScreenFactory());
        new MaLiLib().onInitialize();
    }
}
