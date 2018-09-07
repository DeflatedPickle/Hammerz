package vapourdrive.hammerz.items.hammer;

import java.util.Iterator;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import vapourdrive.hammerz.Reference;
import vapourdrive.hammerz.items.HZ_Items;

public class HammerModel {
	public static void init() {
		ResourceLocation[] resourceVariants = new ResourceLocation[HZ_Items.potentialHammerTypes.size()];
		Iterator<HammerType> iterator = HZ_Items.potentialHammerTypes.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			HammerType hammerType = iterator.next();
			if (hammerType == null) {
				ResourceLocation location = new ModelResourceLocation(Reference.ResourcePath + "stone_hammer");
				resourceVariants[i] = location;
			}
			else {
				ResourceLocation location = new ModelResourceLocation(Reference.ResourcePath + hammerType.getName().toLowerCase() + "_hammer");
				resourceVariants[i] = location;
			}
			i++;
		}
		ModelBakery.registerItemVariants(HZ_Items.ItemHammer, resourceVariants);
		ModelLoader.setCustomMeshDefinition(HZ_Items.ItemHammer, stack -> {
			if (HammerInfoHandler.getHammerType(stack) == null) {
				return new ModelResourceLocation(Reference.ResourcePath + "stone_hammer");
			}
			return new ModelResourceLocation(Reference.ResourcePath + HammerInfoHandler.getHammerType(stack).getName().toLowerCase() + "_hammer");
		});
	}
}
