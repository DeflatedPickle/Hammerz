package sora.hammerx.compat;

import WayofTime.bloodmagic.alchemyArray.AlchemyArrayEffectBinding;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.core.registry.AlchemyArrayRecipeRegistry;
import WayofTime.bloodmagic.core.registry.TartaricForgeRecipeRegistry;
import WayofTime.bloodmagic.item.types.ComponentTypes;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;
import sora.hammerx.utils.RandomUtils;

public class BloodMagicCompat {

    public static void init(){
        ItemStack ironHammer = RandomUtils.getHammer("iron");
        ItemStack diamondHammer = RandomUtils.getHammer("diamond");
        ItemStack boundHammer= RandomUtils.getHammer("bound");
        ItemStack demonHammer = RandomUtils.getHammer("demonic");

        sora.hammerx.HammerX.log.log(Level.INFO, "Registering Binding Recipes...");
       // AlchemyArrayRecipeRegistry.registerRecipe(ComponentTypes.REAGENT_BINDING.getStack(),diamondHammer, new AlchemyArrayEffectBinding("boundhammer", boundHammer));
        sora.hammerx.HammerX.log.log(Level.INFO, "Registering Tartic Forge Recipes...");
       // TartaricForgeRecipeRegistry.registerRecipe(demonHammer,240,150, ironHammer,new ItemStack(RegistrarBloodMagicItems.SOUL_GEM,1,1));
    }

}
