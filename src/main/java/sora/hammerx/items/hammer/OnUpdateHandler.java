package sora.hammerx.items.hammer;

import java.util.Random;

import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.PlayerDemonWillHandler;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.TextHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import sun.nio.ch.Net;
import teamroots.embers.util.EmberInventoryUtil;
import vazkii.botania.api.mana.ManaItemHandler;

public class OnUpdateHandler {

    public static Random random = new Random();

    public static void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        if (HammerInfoHandler.getUsesMana(stack)) {
            handleManaRepair(stack, world, player);
        }
        if (HammerInfoHandler.isStackVoidHammer(stack)) {
            handleVoidRepair(stack, world, player);
        }
        if (HammerInfoHandler.isStackLivingHammer(stack)) {
            handleLivingRepair(stack, world, player);
        }
        if (HammerInfoHandler.getUsesEmber(stack)) {
            handleEmberUse(stack, world, player);
        }

        if (HammerInfoHandler.getUsesLP(stack)) {
            handleLPUse(stack, world, player);
        }

        if (HammerInfoHandler.getUsesDW(stack)) {
            handleDWUse(stack, world, player);
        }
    }

    public static void handleVoidRepair(ItemStack stack, World world, Entity player) {
        if (stack.isItemDamaged() && player != null && player.ticksExisted % 20 == 0 && player instanceof EntityLivingBase) {
            stack.damageItem(-1, (EntityLivingBase) player);
        }
    }

    public static void handleManaRepair(ItemStack stack, World world, Entity player) {
        if (!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExactForTool(stack, (EntityPlayer) player, DamageHandler.MANA_PER_DAMAGE * 2, true)) {
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
    }

    public static void handleLivingRepair(ItemStack stack, World world, Entity player) {
        if (!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && random.nextInt(80) == 0) {
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
    }

    public static void handleEmberUse(ItemStack stack, World world, Entity player) {
        if (!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && (EmberInventoryUtil.getEmberTotal((EntityPlayer) player)) > 0.0D) {
            EmberInventoryUtil.removeEmber((EntityPlayer) player, 5.0D);
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
    }

    public static void handleLPUse(ItemStack stack, World world, Entity player) {

        int cost = stack.getItemDamage();
        if (!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && (NetworkHelper.getSoulNetwork((EntityPlayer) player).getCurrentEssence()) >= 0) {
            int essence = NetworkHelper.getSoulNetwork((EntityPlayer) player).getCurrentEssence();
           /* if (essence < cost) {
                ((EntityPlayer) player).sendStatusMessage(new TextComponentString(I18n.format("bound.network.warning")), true);
            } */
            NetworkHelper.getSoulNetwork((EntityPlayer) player).syphonAndDamage((EntityPlayer) player, SoulTicket.item(stack, world, player, cost));
            stack.setItemDamage(stack.getItemDamage() - 1);
        }
    }

    public static void handleDWUse(ItemStack stack, World world, Entity player){
        int cost = stack.getItemDamage();
        if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && (PlayerDemonWillHandler.getTotalDemonWill(EnumDemonWillType.DEFAULT, (EntityPlayer) player)) > 0){
            PlayerDemonWillHandler.consumeDemonWill(EnumDemonWillType.DEFAULT, (EntityPlayer) player,cost);
            stack.setItemDamage(stack.getItemDamage()-1);
        }
    }
}







