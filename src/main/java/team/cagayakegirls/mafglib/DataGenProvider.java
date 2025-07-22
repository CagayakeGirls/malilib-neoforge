package team.cagayakegirls.mafglib;

import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class DataGenProvider {
    public static abstract class BlockTags extends BlockTagsProvider {
        public BlockTags(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookupProvider) {
            super(output, lookupProvider, MaFgLib.MOD_ID);
        }
    }

    public static abstract class ItemTags extends ItemTagsProvider {
        public ItemTags(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookupProvider) {
            super(output, lookupProvider, MaFgLib.MOD_ID);
        }
    }
}
