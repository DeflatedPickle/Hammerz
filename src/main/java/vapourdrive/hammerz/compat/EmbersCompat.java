package vapourdrive.hammerz.compat;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import teamroots.embers.research.ResearchBase;
import teamroots.embers.research.ResearchCategory;
import teamroots.embers.research.ResearchManager;
import vapourdrive.hammerz.items.HZ_Items;
import vapourdrive.hammerz.items.hammer.ItemHammer;
import vapourdrive.hammerz.utils.RandomUtils;

public class EmbersCompat {

	public static void init() {
		//ADD RESEARCH REGISTRY FOR CLOCKWORK HAMMER
		  ResearchCategory categoryHammerz = new ResearchCategory("hammerz",16.0D);
	}
}
