package fi.dy.masa.malilib;

import java.util.HashMap;

import fi.dy.masa.malilib.compat.iris.IrisCompat;
import fi.dy.masa.malilib.compat.sodium.SodiumCompat;
import team.cagayakegirls.mafglib.utils.ModPlatform;

public class MaLiLibFabricData
{
	public static HashMap<String, String> ALL_MOD_VERSIONS = collectAllModIds();

	protected static void onInitialize()
	{
		collectAllModIds();
		SodiumCompat.register();
		IrisCompat.register();
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
