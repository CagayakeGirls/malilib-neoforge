package team.cagayakegirls.mafglib.utils;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.jarcontents.JarResourceVisitor;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforgespi.language.IModInfo;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ModPlatform {
    public static boolean isModLoaded(String modId) {
        return FMLLoader.getCurrent().getLoadingModList().getModFileById(modId) != null;
    }

    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    public static List<IModInfo> getAllMods() {
        return FMLLoader.getCurrent().getLoadingModList().getMods().stream()
                        .map(modInfo -> (IModInfo) modInfo)
                        .toList();
    }

    public static Optional<? extends ModContainer> getModContainer(String modId) {
        return ModList.get().getModContainerById(modId);
    }

    public static boolean isDevelopmentEnvironment() {
        return !FMLLoader.getCurrent().isProduction();
    }

    public static InputStream getResourceAsStream(String path) {
        return FMLLoader.getCurrent().getCurrentClassLoader().getResourceAsStream(path);
    }

    public static void visitJarResources(String folder, String requiredFile, Consumer<String> visitor) {
        String markerPath = folder + "/" + requiredFile;
        JarResourceVisitor visitPath = (path, resource) -> visitor.accept(path);
        for (var modFile : FMLLoader.getCurrent().getLoadingModList().getModFiles()) {
            var contents = modFile.getFile().getContents();
            if (contents.containsFile(markerPath)) {
                contents.visitContent(folder, visitPath);
            }
        }
    }
}
