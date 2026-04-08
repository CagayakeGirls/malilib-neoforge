package fi.dy.masa.malilib;

import java.nio.file.Path;
import java.util.HashMap;

import team.cagayakegirls.mafglib.utils.ModPlatform;

public class MaLiLibFabricData
{
	protected static final Path GAME_DIR = ModPlatform.getGameDir();
	protected static final Path CONFIG_DIR = ModPlatform.getConfigDir();
	public static HashMap<String, String> ALL_MOD_VERSIONS = collectAllModIds();
	protected static boolean RUNNING_IN_IDE = false;

	protected static void onInitialize()
	{
		RUNNING_IN_IDE = ModPlatform.isDevelopmentEnvironment();
		collectAllModIds();
	}

	private static HashMap<String, String> collectAllModIds()
	{
		final HashMap<String, String> map = new HashMap<>();

		ModPlatform.getAllMods()
		            .stream().toList()
		            .forEach(modInfo ->
		                     {
			                     map.put(modInfo.getModId(), modInfo.getVersion().toString());
		                     }
		            );

		return map;
	}
}
