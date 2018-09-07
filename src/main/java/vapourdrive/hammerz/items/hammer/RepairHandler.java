package vapourdrive.hammerz.items.hammer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RepairHandler {

	public static boolean getIsRepairable(ItemStack stack, ItemStack stack2) {
		int[] names = OreDictionary.getOreIDs(stack2);
		for (int name : names) {
			HammerType type = HammerInfoHandler.getHammerType(stack);
			if (type != null) {
				if ((type.getBlockName()).contentEquals(OreDictionary.getOreName(name))) {
					return true;
				}
			}
		}
		return false;
	}

}
