package sora.hammerx.config;

import java.io.File;

import net.minecraftforge.fml.common.Loader;

public class ConfigHandler
{
	public static File EnderIOCFG;
	public static File HammerzCFG;
	
	public static void earlyInit(File configPath)
	{
		HammerzConfig.preInit(new File(configPath + "/hammerz/" + "HammerX.cfg"));
		
	}

	public static void lateInit(File configPath)
	{
		if (Loader.isModLoaded("EnderIO"))
		{
			EnderIOCFG = new File(configPath + "/enderio/" + "EnderIO.cfg");
			EIOConfig.init(EnderIOCFG);
		}
		HammerzConfig.init(new File(configPath + "/hammerz/" + "HammerX.cfg"));
	}
}
