package fi.dy.masa.malilib.mixin.render;

import java.util.Map;

import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.SpecialGuiElementRenderer;
import net.minecraft.client.gui.render.state.special.SpecialGuiElementRenderState;
import net.neoforged.neoforge.client.gui.PictureInPictureRendererPool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiRenderer.class)
public interface IMixinGuiRenderer
{
    // NeoForge patched, see: https://github.com/neoforged/NeoForge/blob/a5a86111e609a737ce560bf924e75e1062608684/patches/net/minecraft/client/gui/render/GuiRenderer.java.patch#L7
//    @Accessor("specialElementRenderers")
//    Map<Class<? extends SpecialGuiElementRenderState>, SpecialGuiElementRenderer<?>> malilib_getSpecialGuiRenderers();
    @Accessor("pictureInPictureRendererPools")
    Map<Class<? extends SpecialGuiElementRenderState>, PictureInPictureRendererPool<?>> malilib_getSpecialGuiRenderers();
}
