package fi.dy.masa.malilib.mixin.gui;

import java.util.Map;

import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import net.neoforged.neoforge.client.gui.PictureInPictureRendererPool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiRenderer.class)
public interface IMixinGuiRenderer
{
    @Accessor("pictureInPictureRendererPools")
    Map<Class<? extends PictureInPictureRenderState>, PictureInPictureRendererPool<?>> malilib_getSpecialGuiRenderers();
}
