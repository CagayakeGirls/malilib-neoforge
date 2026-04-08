package team.cagayakegirls.mafglib.render.pip;

import fi.dy.masa.malilib.render.special.MaLiLibBlockStateGuiElement;
import fi.dy.masa.malilib.render.special.MaLiLibBlockStateGuiElementRenderer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.neoforged.neoforge.client.gui.PictureInPictureRendererRegistration;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public class MaLiLibGuiElementRendererRegistries {
    public static PictureInPictureRendererRegistration<MaLiLibBlockStateGuiElement> blockStateGuiElement(BlockRenderDispatcher dispatcher) {
        return new PictureInPictureRendererRegistration<>(MaLiLibBlockStateGuiElement.class, (bufferSource) -> new MaLiLibBlockStateGuiElementRenderer(bufferSource, dispatcher));
    }
}
