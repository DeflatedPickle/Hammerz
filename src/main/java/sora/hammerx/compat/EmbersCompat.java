package sora.hammerx.compat;

import net.minecraft.item.ItemStack;
import teamroots.embers.research.ResearchCategory;
import sora.hammerx.utils.RandomUtils;

public class EmbersCompat {

	public static void init() {
		//ADD RESEARCH REGISTRY FOR CLOCKWORK HAMMER
		ItemStack clockworkHammer = RandomUtils.getHammer("clockwork");
		ResearchCategory categoryHammerz = new ResearchCategory("hammerz",16.0D);
	}
}
