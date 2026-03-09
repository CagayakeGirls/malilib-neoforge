package team.cagayakegirls.mafglib.render.pip;

import fi.dy.masa.malilib.render.special.MaLiLibBlockStateGuiElement;
import net.minecraft.client.renderer.MultiBufferSource;

import net.neoforged.neoforge.client.gui.PictureInPictureRendererPool;
import net.neoforged.neoforge.client.gui.PictureInPictureRendererRegistration;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public class MaLiLibBlockStateGuiElementRendererPool extends PictureInPictureRendererPool<@NotNull MaLiLibBlockStateGuiElement>
{
    private final PictureInPictureRendererRegistration<@NotNull MaLiLibBlockStateGuiElement> factory;

    public MaLiLibBlockStateGuiElementRendererPool(PictureInPictureRendererRegistration<@NotNull MaLiLibBlockStateGuiElement> factory, MultiBufferSource.BufferSource immediate)
    {
        super(factory, immediate);
        this.factory = factory;
    }

    public PictureInPictureRendererRegistration<@NotNull MaLiLibBlockStateGuiElement> getFactory() {
        return factory;
    }
}
