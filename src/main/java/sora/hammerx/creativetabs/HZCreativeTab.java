package sora.hammerx.creativetabs;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sora.hammerx.items.HammerX_Items;
import sora.hammerx.items.hammer.ItemHammer;
import sora.hammerx.utils.RandomUtils;

public class HZCreativeTab extends CreativeTabs
{
	public static int timeSinceChance = 250;
	public static ItemStack stack;

	public HZCreativeTab(int id, String name)
	{
		super(id, name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTabLabel()
	{
		return "HammerX";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return "HammerX";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack()
	{
		if (timeSinceChance >= 250)
		{
			timeSinceChance = 0;
			ItemStack stack = new ItemStack(HammerX_Items.ItemHammer);
			NBTTagCompound tagCompound = RandomUtils.getNBT(stack);
			Random rand = new Random();
			String name = HammerX_Items.hammerTypes.get(rand.nextInt(HammerX_Items.hammerTypes.size())).getName();
			tagCompound.setString(ItemHammer.HammerKey, name);
			HZCreativeTab.stack = stack;
		}
		timeSinceChance++;
		return HZCreativeTab.stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem()
	{
		return new ItemStack(HammerX_Items.ItemHammer);
	}

}
