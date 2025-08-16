package org.thinkingstudio.mafglib.loader.entrypoints;

import net.neoforged.fml.IExtensionPoint;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Deprecated(forRemoval = true, since = "1.21.7")
public interface ConfigScreenEntrypoint extends IExtensionPoint {
    IConfigScreenFactory getModConfigScreenFactory();
}
