package sora.hammerx.proxies;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Level;

import sora.hammerx.compat.BloodMagicCompat;
import sora.hammerx.compat.BotaniaCompat;
import sora.hammerx.compat.EmbersCompat;
import sora.hammerx.compat.ThaumcraftCompat;
import sora.hammerx.config.ConfigHandler;
import sora.hammerx.creativetabs.HZCreativeTab;
import sora.hammerx.handlers.EventsHandler;
import sora.hammerx.handlers.OreDictHandler;
import sora.hammerx.handlers.UpgradeManager;
import sora.hammerx.items.HammerX_Items;

public class CommonProxy
{
	public static CreativeTabs HZTab;
	public static File ConfigPath;

	
	public void preInit(FMLPreInitializationEvent event)
	{
		sora.hammerx.HammerX.log.log(Level.INFO, "Beginning preInit");
		ConfigPath = event.getModConfigurationDirectory();
		HZTab = new HZCreativeTab(CreativeTabs.getNextID(), "SS_CreativeTab");
		OreDictHandler.earlyInit();
		ConfigHandler.earlyInit(ConfigPath);

		HammerX_Items.preInit();

		ConfigHandler.lateInit(ConfigPath);
		HammerX_Items.init();

	}
	
	public void Init(FMLInitializationEvent event)
	{
		sora.hammerx.HammerX.log.log(Level.INFO, "Beginning Init");
		HammerX_Items.postInit();

		EventsHandler.init();
		new UpgradeManager();



		sora.hammerx.HammerX.log.log(Level.INFO, "Beginning preInit");
		if (Loader.isModLoaded("rotarycraft"))
		{
			OreDictHandler.registerOre("rotarycraft", "rotarycraft_block_deco", "blockHSLA", 0);
		}
		if (Loader.isModLoaded("thaumcraft"))
		{
			ThaumcraftCompat.init();
		}
		if (Loader.isModLoaded("botania"))
		{
			BotaniaCompat.init();
		}
		if(Loader.isModLoaded("embers")){
			EmbersCompat.init();
		}



	}
	
	public void posInit(FMLPostInitializationEvent event)
	{
		if(Loader.isModLoaded("bloodmagic")){
			BloodMagicCompat.init();
		}

	}
}
