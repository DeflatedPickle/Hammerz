package sora.hammerx.items.hammer;

import java.util.Iterator;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import sora.hammerx.Reference;
import sora.hammerx.items.HammerX_Items;

public class HammerModel {
	public static void init() {
		ResourceLocation[] resourceVariants = new ResourceLocation[HammerX_Items.potentialHammerTypes.size()];
		Iterator<HammerType> iterator = HammerX_Items.potentialHammerTypes.iterator();
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
		ModelBakery.registerItemVariants(HammerX_Items.ItemHammer, resourceVariants);
		ModelLoader.setCustomMeshDefinition(HammerX_Items.ItemHammer, stack -> {
			if (HammerInfoHandler.getHammerType(stack) == null) {
				return new ModelResourceLocation(Reference.ResourcePath + "stone_hammer");
			}
			return new ModelResourceLocation(Reference.ResourcePath + HammerInfoHandler.getHammerType(stack).getName().toLowerCase() + "_hammer");
		});
	}
}
