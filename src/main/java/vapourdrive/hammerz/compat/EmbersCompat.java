package vapourdrive.hammerz.compat;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import vapourdrive.hammerz.items.HZ_Items;
import vapourdrive.hammerz.items.hammer.ItemHammer;
import vapourdrive.hammerz.utils.RandomUtils;

public class EmbersCompat {

	public static void init() {
		ItemStack clockworkHammer = new ItemStack(HZ_Items.ItemHammer);
		NBTTagCompound tagCompound = RandomUtils.getNBT(clockworkHammer);
		tagCompound.setString(ItemHammer.HammerKey, "clockwork");
	}
}
