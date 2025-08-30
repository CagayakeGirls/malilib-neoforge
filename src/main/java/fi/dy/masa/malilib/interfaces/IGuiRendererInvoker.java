package fi.dy.masa.malilib.interfaces;

import java.util.Map;

import net.minecraft.client.gui.render.SpecialGuiElementRenderer;
import net.minecraft.client.gui.render.state.special.SpecialGuiElementRenderState;
import net.neoforged.neoforge.client.gui.PictureInPictureRendererPool;

public interface IGuiRendererInvoker
{
    //void malilib$replaceSpecialGuiRenderers(Map<Class<? extends SpecialGuiElementRenderState>, SpecialGuiElementRenderer<?>> map);
    void malilib$replaceSpecialGuiRenderers(Map<Class<? extends SpecialGuiElementRenderState>, PictureInPictureRendererPool<?>> map);
}
