package sora.hammerx.proxies;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sora.hammerx.items.HammerX_Items;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		HammerX_Items.clientInit(event);


	}
	
	@Override
	public void Init(FMLInitializationEvent event)
	{
		super.Init(event);

		if (Loader.isModLoaded("EnderIO"))
		{
			//PoweredItemRenderer renderer = new PoweredItemRenderer();
			//MinecraftForgeClient.registerItemRenderer(HammerX_Items.DarkSteelHammer, renderer);
		}

	}
}
