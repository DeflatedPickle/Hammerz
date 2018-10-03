package sora.hammerx.items.hammer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sora.hammerx.utils.RandomUtils;

public class ItemUseHandler
{

	public static EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float floatx,
			float floaty, float floatz)
	{
		/*if(HammerInfoHandler.getUsesEmber(player.getHeldItemMainhand())){
			return handleEmberUse(player, world, pos, hand, side, floatx, floaty, floatz);
		}*/
		return handleRegularUse(player, world, pos, hand, side, floatx, floaty, floatz);
	}

	public static EnumActionResult onItemShiftUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float floatx,
			float floaty, float floatz)
	{
		if (HammerInfoHandler.isStackElementalHammer(player.getHeldItemMainhand()))
		{
			return handleElementalUse(player, world, pos, hand, side, floatx, floaty, floatz);
		}
		return EnumActionResult.PASS;
	}

	private static EnumActionResult handleElementalUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side,
			float floatx, float floaty, float floatz)
	{
		ItemStack ThaumPick = RandomUtils.getItemStackFromString("Thaumcraft", "elemental_pick", 1);
		if (ThaumPick != null && ThaumPick.getItem() != null)
		{
			if (DamageHandler.requestDamage(false, null, player.getHeldItemMainhand(), player, 10))
			{
				ThaumPick.getItem().onItemUse(player, world, pos, hand, side, floatx, floaty, floatz);
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.PASS;
	}

	/*private static EnumActionResult handleEmberUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float floatx, float floaty, float floatz)
	{
		if(HammerInfoHandler.getUsesEmber(player.getHeldItemMainhand()) && EmberInventoryUtil.getEmberTotal(player) > 0)
		{
			EmberInventoryUtil.removeEmber(player, 5.0D);
			return EnumActionResult.SUCCESS;

		}
		return EnumActionResult.FAIL;
	}*/

	private static EnumActionResult handleRegularUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float floatx,
			float floaty, float floatz)
	{
		if (player.getHeldItemMainhand().getItem() instanceof ItemHammer)
		{
			if (!player.isSneaking())
			{
				ItemStack torch = new ItemStack(Blocks.TORCH);
				if (player.inventory.hasItemStack(torch))
				{
					ItemStack torchStack = RandomUtils.findStackForItem(torch.getItem(), player);
					if (RandomUtils.onItemBlockUse(torchStack, player, world, pos, hand, side, floatx, floaty, floatz) == EnumActionResult.SUCCESS)
					{
						RandomUtils.consumeInventoryItem(player.inventory, torchStack, 1);
						return EnumActionResult.SUCCESS;
					}
				}
			}
			else
			{
				return onItemShiftUse(player, world, pos, hand, side, floatx, floaty, floatz);
			}
		}
		return EnumActionResult.PASS;
	}

}
