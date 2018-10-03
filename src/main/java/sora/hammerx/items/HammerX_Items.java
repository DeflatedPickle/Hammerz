package sora.hammerx.items;

import java.util.ArrayList;
import java.util.Iterator;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import org.apache.logging.log4j.Level;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import sora.hammerx.HammerX;
import thaumcraft.api.ThaumcraftMaterials;
import sora.hammerx.config.ConfigOptions;
import sora.hammerx.config.HammerzConfig;
import sora.hammerx.items.hammer.HammerModel;
import sora.hammerx.items.hammer.HammerType;
import sora.hammerx.items.hammer.ItemHammer;
import sora.hammerx.utils.RandomUtils;
import vazkii.botania.api.BotaniaAPI;

@Mod.EventBusSubscriber
public class HammerX_Items {
	public static ArrayList<HammerType> potentialHammerTypes = new ArrayList<HammerType>();
	public static ArrayList<HammerType> hammerTypes = new ArrayList<HammerType>();

	public static Item ItemHammer;
	public static IForgeRegistryModifiable<IRecipe> recipes;

	public static void preInit() {
		setupHammerTypes();
		setupModHammerTypes();
	}

	public static void init() {
		ItemHammer = new ItemHammer();
	}

	public static void clientInit(FMLPreInitializationEvent event) {
		HammerModel.init();
	}

	public static void postInit() {
		registerHammerTypes();
		String ores[] = OreDictionary.getOreNames();
		sora.hammerx.HammerX.log.log(Level.INFO, "Currently registered ores: ");

		for (String ore : ores) {
			sora.hammerx.HammerX.log.log(Level.INFO, ore);
		}
	}

	@SubscribeEvent
	public static void storeRecipes(RegistryEvent.Register<IRecipe> event) {
		//I'm not sure if storing the registry like this is safe. Should be moved to an IConditional within the recipe json itself at a later date.
		recipes = (IForgeRegistryModifiable) event.getRegistry();
	}

	public static void setupHammerTypes() {
		addHammerType(0, 0, "logWood", ToolMaterial.WOOD, EnumRarity.COMMON);
		addHammerType(0, 0, "stone", ToolMaterial.STONE, EnumRarity.COMMON);
		addHammerType(0, 0, "blockIron", ToolMaterial.IRON, EnumRarity.COMMON);
		addHammerType(0, 0, "blockGold", ToolMaterial.GOLD, EnumRarity.COMMON);
		addHammerType(0, 0, "blockDiamond", ToolMaterial.DIAMOND, EnumRarity.COMMON);
		addHammerType(0, 0, "blockCopper", "Copper", 1, 175, 4.0F, 0.5F, 6, EnumRarity.COMMON);
		addHammerType(0, 0, "blockTin", "Tin", 1, 200, 4.5F, 1.0F, 7, EnumRarity.COMMON);
		addHammerType(0, 0, "blockSilver", "Silver", 2, 200, 6.0F, 1.5F, 20, EnumRarity.COMMON);
		addHammerType(0, 0, "blockLead", "Lead", 1, 150, 5F, 1.0F, 9, EnumRarity.COMMON);
		addHammerType(0, 0, "blockNickel", "Nickel", 2, 300, 6.5F, 2.5F, 18, EnumRarity.COMMON);
		addHammerType(0, 0, "blockElectrum", "Electrum", 0, 100, 14.0F, 0.5F, 30, EnumRarity.COMMON);
		addHammerType(0, 0, "blockInvar", "Invar", 2, 450, 7.0F, 3.0F, 16, EnumRarity.COMMON);
		addHammerType(0, 0, "blockBronze", "Bronze", 2, 500, 6.0F, 2.0F, 15, EnumRarity.COMMON);
		addHammerType(0, 0, "blockPlatinum", "Platinum", 4, 1700, 9.0F, 4.0F, 9, EnumRarity.COMMON);
		addHammerType(0, 0, "blockSteel", "Steel", 2, 500, 7.0F, 2.0F, 9, EnumRarity.COMMON);
	}

	private static void setupModHammerTypes() {
		if (Loader.isModLoaded("thaumcraft")) {
			sora.hammerx.HammerX.log.log(Level.INFO, "ThaumcraftCompat loading");
			addHammerType(3, 0, "blockThaumium", ThaumcraftMaterials.TOOLMAT_THAUMIUM, EnumRarity.UNCOMMON);
			addHammerType(3, 0, "blockVoidMetal", ThaumcraftMaterials.TOOLMAT_VOID, EnumRarity.RARE);
			addHammerType(3, 0, "blockThaumium", ThaumcraftMaterials.TOOLMAT_ELEMENTAL, EnumRarity.RARE);
			sora.hammerx.HammerX.hasStorageBlock = true;
		}
		if (Loader.isModLoaded("botania")) {
			sora.hammerx.HammerX.log.log(Level.INFO, "BotaniaCompat loading");
			addHammerType(1, 0, "blockManasteel", BotaniaAPI.manasteelToolMaterial, EnumRarity.COMMON);
			addHammerType(1, 0, "blockElvenElementium", BotaniaAPI.elementiumToolMaterial, EnumRarity.COMMON);
		}
		if (Loader.isModLoaded("enderio")) {
			sora.hammerx.HammerX.log.log(Level.INFO, "EnderIOCompat loading");
			addHammerType(2, 0, "blockDarkSteel", "DarkSteel", 5, 1561, 7.0F, 2.0F, 25, EnumRarity.COMMON);

		}
		if (Loader.isModLoaded("rotarycraft")) {
			sora.hammerx.HammerX.log.log(Level.INFO, "RotaryCraftCompat loading");
			addHammerType(-1, 0, "blockBedRock", "Bedrock", 3, 0, 8.0F, 3.0F, 10, EnumRarity.COMMON);
			addHammerType(-1, 0, "blockHSLA", "HSLA", 2, 600, 6.0F, 3.0F, 14, EnumRarity.COMMON);
		}
		if (Loader.isModLoaded("betterwithmods")) {
			sora.hammerx.HammerX.log.log(Level.INFO, "Better With Mods Compat loading");
			addHammerType(0, 0, "steel_block", "SoulforgedSteel", 3, 1561, 8.0F, 3.0F, 22, EnumRarity.COMMON);
		}
		if (Loader.isModLoaded("roots")) {
			sora.hammerx.HammerX.log.log(Level.INFO, "Roots Compat loading");
			addHammerType(0, 0, "logWood", "Living", 2, 192, 6.0F, 2.0F, 18, EnumRarity.COMMON);
		}
		if (Loader.isModLoaded("embers")) {
			sora.hammerx.HammerX.log.log(Level.INFO, "Embers Compat loading");
			addHammerType(4, 0, "blockDawnstone", "clockwork", 2, 644, 7.5F, 2.5F, 18, EnumRarity.COMMON);
		}

		if(Loader.isModLoaded("bloodmagic")){
			sora.hammerx.HammerX.log.log(Level.INFO, "Blood Magic Compat loading");
			addHammerType(5,0,"blockDiamond", "bound", 4, 1561, 10.0F, 8.0F, 50,EnumRarity.COMMON);
			addHammerType(6,0,"blockDiamond",RegistrarBloodMagicItems.SOUL_TOOL_MATERIAL,EnumRarity.COMMON);
		}

	}

	public static void registerHammerTypes() {

		for (int i = 0; i < potentialHammerTypes.size(); i++) {
			HammerType type = potentialHammerTypes.get(i);
			if (ConfigOptions.OreDictHammerEnabling[i] && (RandomUtils.doesOreNameExist(type.getBlockName()) || RandomUtils.doesBlockExist(type.getBlockName()))) {
				hammerTypes.add(potentialHammerTypes.get(i));
			}
			else {
				recipes.remove(new ResourceLocation("hammerx:" + type.getName() + "hammer"));
			}
		}

		Iterator<HammerType> iterator = potentialHammerTypes.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			HammerType type = iterator.next();
			if (ConfigOptions.OreDictHammerEnabling[i] && (RandomUtils.doesOreNameExist(type.getBlockName()) || RandomUtils.doesBlockExist(type.getBlockName()))) {
				HammerX.log.log(Level.INFO, "Hammer material " + type.getName() + " confirmed, adding to list");
				hammerTypes.add(potentialHammerTypes.get(i));
			}
			else {
				//Removes extra recipes. Again, I should probably move this to an IConditional
				recipes.remove(new ResourceLocation("hammerz:" + type.getName().toLowerCase() + "hammer"));
			}
			i++;
		}
		//not sure if this is necessary, but the registry is no longer needed, so no reason to keep it around
		recipes = null;

	}

	public static void addHammerType(int damageType, int maxEnergy, String blockName, ToolMaterial material, EnumRarity rarity) {
		String name = material.name();

		if (material == ToolMaterial.DIAMOND) {
			name = "DIAMOND";
		}
		int harvestLevel = material.getHarvestLevel();
		int durability = material.getMaxUses();
		float efficiency = material.getEfficiency();
		float damage = material.getAttackDamage();
		int enchantability = material.getEnchantability();

		HammerType hammertype = new HammerType(damageType, maxEnergy, blockName, name, harvestLevel, durability, efficiency, damage, 0, enchantability, rarity);

		potentialHammerTypes.add(hammertype);
		HammerzConfig.enabledOreDictHammersComment = HammerzConfig.enabledOreDictHammersComment + name + ", ";
		HammerzConfig.enabledOreDictHammersArrayList.add(true);
	}

	public static void addHammerType(int damageType, int maxEnergy, String blockName, String name, int harvestLevel, int durability, float efficiency, float damage, int enchantability, EnumRarity rarity) {
		HammerType hammertype = new HammerType(damageType, maxEnergy, blockName, name.toUpperCase(), harvestLevel, durability, efficiency, damage, 0, enchantability, rarity);
		potentialHammerTypes.add(hammertype);
		HammerzConfig.enabledOreDictHammersComment = HammerzConfig.enabledOreDictHammersComment + name + ", ";
		HammerzConfig.enabledOreDictHammersArrayList.add(true);
	}
}
