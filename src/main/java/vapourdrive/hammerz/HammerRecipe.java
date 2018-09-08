package vapourdrive.hammerz;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import vapourdrive.hammerz.utils.RandomUtils;

public class HammerRecipe extends ShapedOreRecipe {

	public HammerRecipe(ResourceLocation group, ItemStack result, CraftingHelper.ShapedPrimer primer) {
		super(group, result, primer);
	}

	@Override
	@Nonnull
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		return getRecipeOutput();
	}

	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	public static HammerRecipe factory(JsonContext context, JsonObject json) {
		String group = JsonUtils.getString(json, "group", "");
		Map<Character, Ingredient> ingMap = Maps.newHashMap();
		for (Entry<String, JsonElement> entry : JsonUtils.getJsonObject(json, "key").entrySet()) {
			if (entry.getKey().length() != 1) {
				throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
			}
			if (" ".equals(entry.getKey())) {
				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
			}
			ingMap.put(entry.getKey().toCharArray()[0], CraftingHelper.getIngredient(entry.getValue(), context));
		}
		ingMap.put(' ', Ingredient.EMPTY);
		JsonArray patternJ = JsonUtils.getJsonArray(json, "pattern");
		if (patternJ.size() == 0) {
			throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
		}
		String[] pattern = new String[patternJ.size()];
		for (int x = 0; x < pattern.length; ++x) {
			String line = JsonUtils.getString(patternJ.get(x), "pattern[" + x + "]");
			if (x > 0 && pattern[0].length() != line.length()) {
				throw new JsonSyntaxException("Invalid pattern: each row must  be the same width");
			}
			pattern[x] = line;
		}
		ShapedPrimer primer = new ShapedPrimer();
		primer.width = pattern[0].length();
		primer.height = pattern.length;
		primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
		primer.input = NonNullList.withSize(primer.width * primer.height, Ingredient.EMPTY);
		Set<Character> keys = Sets.newHashSet(ingMap.keySet());
		keys.remove(' ');
		int x = 0;
		for (String line : pattern) {
			for (char chr : line.toCharArray()) {
				Ingredient ing = ingMap.get(chr);
				if (ing == null) {
					throw new JsonSyntaxException("Pattern references symbol '" + chr + "' but it's not defined in the key");
				}
				primer.input.set(x++, ing);
				keys.remove(chr);
			}
		}
		if (!keys.isEmpty()) {
			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + keys);
		}
		JsonObject result = JsonUtils.getJsonObject(json, "result");
		String itemName = JsonUtils.getString(result, "hammertype");
		ItemStack returnStack = RandomUtils.getHammer(itemName);
		return new HammerRecipe(group.isEmpty() ? null : new ResourceLocation(group), returnStack, primer);
	}

	public static class Factory implements IRecipeFactory {

		@Override
		public IRecipe parse(JsonContext context, JsonObject json) {
			JsonObject result = JsonUtils.getJsonObject(json, "result");
			String itemName = JsonUtils.getString(result, "hammertype");
			CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();

			HammerRecipe recipe = HammerRecipe.factory(context, json);
			primer.width = recipe.getRecipeWidth();
			primer.height = recipe.getRecipeHeight();
			primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
			primer.input = recipe.getIngredients();

			return new HammerRecipe(new ResourceLocation(Reference.ModID, "hammer_crafting"), RandomUtils.getHammer(itemName), primer);
		}

	}
}
