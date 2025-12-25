package team.cagayakegirls.mafglib.utils;

import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforgespi.language.IModInfo;

import java.nio.file.Path;
import java.util.List;

public class ModPlatform {
    public static boolean isModLoaded(String modId) {
        return FMLLoader.getLoadingModList().getModFileById(modId) != null;
    }

    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static List<IModInfo> getAllMods() {
        return FMLLoader.getLoadingModList().getMods().stream()
                        .map(modInfo -> (IModInfo) modInfo)
                        .toList();
    }
}
