package team.cagayakegirls.mafglib.utils;

import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.tag.ItemTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import team.cagayakegirls.mafglib.MaFgLib;

import java.util.concurrent.CompletableFuture;

public class DataGenProvider {
    public static abstract class BlockTags extends BlockTagsProvider {
        public BlockTags(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookupProvider) {
            super(output, lookupProvider, MaFgLib.MOD_ID);
        }
    }

    public static abstract class ItemTags extends ItemTagProvider {
        public ItemTags(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider) {
            super(output, lookupProvider, blockTagProvider, MaFgLib.MOD_ID);
        }
    }
}
