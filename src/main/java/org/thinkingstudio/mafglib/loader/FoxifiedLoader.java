package org.thinkingstudio.mafglib.loader;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import team.cagayakegirls.mafglib.utils.ModPlatform;

import java.nio.file.Path;
import java.util.List;

@Deprecated(since = "mc1.21.7", forRemoval = true)
public final class FoxifiedLoader {
    public static ModContainer getModContainers() {
        return ModLoadingContext.get().getActiveContainer();
    }

    public static boolean isModLoaded(String modId) {
        return ModPlatform.isModLoaded(modId);
    }

    public static Path getConfigDir() {
        return ModPlatform.getConfigDir();
    }

    public static String getModVersion(String modId) {
        for (ModInfo modInfo: FMLLoader.getLoadingModList().getMods()) {
            if (modInfo.getModId().equals(modId)) {
                return modInfo.getVersion().toString();
            }
        }

        return "?";
    }

    public static List<IModInfo> getAllMods() {
        return ModPlatform.getAllMods();
    }
}
