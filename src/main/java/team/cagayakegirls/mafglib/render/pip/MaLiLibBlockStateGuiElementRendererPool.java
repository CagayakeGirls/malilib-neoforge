package team.cagayakegirls.mafglib.render.pip;

import fi.dy.masa.malilib.render.special.MaLiLibBlockStateGuiElement;
import net.minecraft.client.renderer.MultiBufferSource;

import net.minecraft.client.renderer.block.BlockModelResolver;
import net.neoforged.neoforge.client.gui.PictureInPictureRendererPool;
import net.neoforged.neoforge.client.gui.PictureInPictureRendererRegistration;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public class MaLiLibBlockStateGuiElementRendererPool extends PictureInPictureRendererPool<@NotNull MaLiLibBlockStateGuiElement>
{
    private final PictureInPictureRendererRegistration<@NotNull MaLiLibBlockStateGuiElement> factory;

    public MaLiLibBlockStateGuiElementRendererPool(MultiBufferSource.BufferSource immediate, BlockModelResolver resolver) {
        super(MaLiLibGuiElementRendererRegistries.blockStateGuiElement(resolver), immediate);
        this.factory = MaLiLibGuiElementRendererRegistries.blockStateGuiElement(resolver);
    }

    public PictureInPictureRendererRegistration<@NotNull MaLiLibBlockStateGuiElement> getFactory() {
        return factory;
    }
}
