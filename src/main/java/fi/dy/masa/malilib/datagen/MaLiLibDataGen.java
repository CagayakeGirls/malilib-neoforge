package fi.dy.masa.malilib.datagen;

import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.ServerDynamicRegistryType;
import net.minecraft.resource.ResourceType;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import team.cagayakegirls.mafglib.MaFgLib;
import org.thinkingstudio.mafglib.helper.RuntimePackHelper;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("removal")
public class MaLiLibDataGen
{
    public static void onInitializeDataGenerator(AddPackFindersEvent event)
    {
        var type = event.getPackType();
        var lookupProvider = CompletableFuture.<RegistryWrapper.WrapperLookup>completedFuture(ServerDynamicRegistryType.createCombinedDynamicRegistries().getCombinedRegistryManager());
        if (type == ResourceType.SERVER_DATA) {
            var pack = RuntimePackHelper.simpleRuntimePack(MaFgLib.MOD_ID, type);
            var output = pack.getPackOutput();
            var existingFileHelper = pack.getExistingFileHelper();
            var blockTag = new BlockTagDataGenerator(output, lookupProvider, existingFileHelper);
            pack.addDataProvider(blockTag);
            //pack.addDataProvider(new ItemTagGenerator(output, lookupProvider, blockTag, existingFileHelper))
            event.addRepositorySource(pack);
        }
    }
}
