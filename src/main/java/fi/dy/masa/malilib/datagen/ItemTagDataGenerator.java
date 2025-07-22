package fi.dy.masa.malilib.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import team.cagayakegirls.mafglib.DataGenProvider;

public class ItemTagDataGenerator extends DataGenProvider.ItemTags
{
    public ItemTagDataGenerator(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup)
    {
        // todo
    }
}
