package sora.hammerx.compat;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sora.hammerx.items.HammerX_Items;
import sora.hammerx.items.hammer.ItemHammer;
import sora.hammerx.utils.RandomUtils;
import vazkii.botania.api.BotaniaAPI;

public class BotaniaCompat
{
	public static void init()
	{
		ItemStack manasteelHammer = new ItemStack(HammerX_Items.ItemHammer);
		NBTTagCompound tagCompound = RandomUtils.getNBT(manasteelHammer);
		tagCompound.setString(ItemHammer.HammerKey, "manasteel");
		new HammerEntry("manasteel", false, manasteelHammer, "ManasteelHammer", BotaniaAPI.categoryTools);
		
		ItemStack elementiumHammer = new ItemStack(HammerX_Items.ItemHammer);
		NBTTagCompound tagCompound2 = RandomUtils.getNBT(elementiumHammer);
		tagCompound2.setString(ItemHammer.HammerKey, "b_elementium");
		new HammerEntry("b_elementium", true, elementiumHammer, "ElementiumHammer", BotaniaAPI.categoryAlfhomancy);
	}

}
