package fi.dy.masa.malilib.compat.modmenu;

import fi.dy.masa.malilib.MaLiLibConfigGui;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ModMenuImpl
{
    public IConfigScreenFactory getModConfigScreenFactory()
    {
        return (_, screen) -> {
            MaLiLibConfigGui gui = new MaLiLibConfigGui();
            gui.setParent(screen);
            return gui;
        };
    }
}
