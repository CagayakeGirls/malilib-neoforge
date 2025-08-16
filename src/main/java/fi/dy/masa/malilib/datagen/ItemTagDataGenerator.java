package fi.dy.masa.malilib.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import team.cagayakegirls.mafglib.utils.DataGenProvider;

public class ItemTagDataGenerator extends DataGenProvider.ItemTags
{
    public ItemTagDataGenerator(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper)
    {
        super(output, completableFuture, blockTagProvider, existingFileHelper);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup)
    {
        // todo
    }
}
