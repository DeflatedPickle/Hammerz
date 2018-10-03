package sora.hammerx.items.hammer;

import WayofTime.bloodmagic.core.data.Binding;
import WayofTime.bloodmagic.iface.IBindable;
import WayofTime.bloodmagic.iface.ISentientTool;
import WayofTime.bloodmagic.util.helper.TextHelper;
import cofh.redstoneflux.api.IEnergyContainerItem;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import exnihilocreatio.items.tools.IHammer;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sora.hammerx.Reference;
import sora.hammerx.config.ConfigOptions;
import sora.hammerx.items.HammerX_Items;
import sora.hammerx.proxies.CommonProxy;
import sora.hammerx.utils.BlockUtils;
import sora.hammerx.utils.RandomUtils;
import teamroots.embers.api.item.IEmberChargedTool;
import vazkii.botania.api.mana.IManaUsingItem;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Optional.InterfaceList(
        {
                @Optional.Interface(modid = "botania", iface = "vazkii.botania.api.mana.IManaUsingItem", striprefs = true),
                @Optional.Interface(modid = "thaumcraft", iface = "thaumcraft.api.items.IRepairableExtended", striprefs = true),
                @Optional.Interface(modid = "thaumcraft", iface = "thaumcraft.api.items.IWarpingGear", striprefs = true),
                @Optional.Interface(modid = "botania", iface = "vazkii.botania.api.mana.ManaItemHandler", striprefs = true),
                @Optional.Interface(modid = "redstoneflux", iface = "cofh.redstoneflux.api.IEnergyContainerItem", striprefs = true),
                @Optional.Interface(modid = "embers", iface = "teamroots.embers.item.IEmberChargedTool", striprefs = true),
                @Optional.Interface(modid = "bloodmagic", iface = "WayofTime.bloodmagic.iface.IBindable", striprefs = true),
                @Optional.Interface(modid = "bloodmagic", iface = "WayofTime.bloodmagic.iface.ISentientTool", striprefs = true),
                @Optional.Interface(modid = "bloodmagic", iface = "exnihilocreatio.items.tools.IHammer", striprefs = true)

        })
public class ItemHammer extends ItemPickaxe implements IEnergyContainerItem, IManaUsingItem, IEmberChargedTool, IBindable, ISentientTool, IHammer {
    public static final String HammerKey = "HammerX.HammerType";
    public static final String Tag_DarkSteelEnergy = "HammerX.hammer.darkhammer.energy";
    public static final String Tag_EnergyStored = "HammerX.hammer.energy";
    public static final String Key_Empower = "HammerX.upgrade.empowerment";


    public ItemHammer() {
        super(ToolMaterial.IRON);
        this.setCreativeTab(CommonProxy.HZTab);
        this.setUnlocalizedName(Reference.ModID + ".hammer");
        this.hasSubtypes = true;
        this.setRegistryName("item.hammer");
        ForgeRegistries.ITEMS.register(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
        super.addInformation(stack, world, list, flagIn);
        AddInformationHelper.addInformation(stack, world, list, flagIn);
        if (HammerInfoHandler.getUsesLP(stack)) {
            Binding binding = getBinding(stack);
            if (binding != null)
                list.add(TextHelper.localizeEffect("tooltip.bloodmagic.currentOwner", getBinding(stack).getOwnerName()));
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

	/*@Override
	public float getDigSpeed(ItemStack stack, IBlockState state)
	{
		if (state.getBlock().isToolEffective("pickaxe", state))
		{
				return HammerInfoHandler.getEfficiency((stack)) * ConfigOptions.EfficiencyMultiplier;
		}
        return (super.getDigSpeed(stack, state) * ConfigOptions.EfficiencyMultiplier);
	}*/

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float floatx, float floaty, float floatz) {
        if (!player.isSneaking()) {
            return ItemUseHandler.onItemUse(player, world, pos, hand, facing, floatx, floaty, floatz);
        } else {
            return ItemUseHandler.onItemShiftUse(player, world, pos, hand, facing, floatx, floaty, floatz);
        }
    }
//Used in DEV to check LP
   /* @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(HammerInfoHandler.getUsesLP(stack)) {
            if (PlayerHelper.isFakePlayer(player))
                return ActionResult.newResult(EnumActionResult.FAIL, stack);

            if (!world.isRemote) {
                super.onItemRightClick(world, player, hand);

                Binding binding = getBinding(stack);
                if (binding != null) {
                    int currentEssence = NetworkHelper.getSoulNetwork(binding).getCurrentEssence();
                        ChatUtil.sendNoSpam(player,"Current LP:" + " " + currentEssence);
                }
            }
        }

        return super.onItemRightClick(world, player, hand);
    }*/


    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        super.onUpdate(stack, world, player, par4, par5);
        OnUpdateHandler.onUpdate(stack, world, player, par4, par5);
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack stack, ItemStack stack2) {
        return RepairHandler.getIsRepairable(stack, stack2);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @javax.annotation.Nullable net.minecraft.entity.player.EntityPlayer player, @javax.annotation.Nullable IBlockState blockState) {
        return HammerInfoHandler.getHarvestLevel(stack, toolClass, player, blockState);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return HammerInfoHandler.getItemEnchantability(stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return HammerInfoHandler.getMaxDamage(stack);
    }

   @Override
    @MethodsReturnNonnullByDefault
    public Set<String> getToolClasses(ItemStack stack)
    {


        Set<String> ToolClass = Sets.newHashSet(new String[]
                {
                        "pickaxe"
                });
        return ToolClass;
    }

    @Override
    public float getDestroySpeed(@Nullable ItemStack stack, IBlockState state) {
        return HammerInfoHandler.getStrengthVsBlock(stack, state);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, @Nullable EntityLivingBase attacker) {
        return DamageHandler.handleDamage(true, stack, target, attacker, 4);
    }

    @Override
    public boolean onBlockDestroyed(@Nullable ItemStack stack, World worldIn, @Nullable IBlockState state, @Nullable BlockPos pos, @Nullable EntityLivingBase entityLiving) {
        DamageHandler.handleDamage(true, state, stack, entityLiving);
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        World world = player.getEntityWorld();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if ((player.isSneaking() && ConfigOptions.CanShiftMine) || block.getBlockHardness(state, world, pos) == 0) {
            return false;
        }

        RayTraceResult object = RandomUtils.raytraceFromEntity(world, player, false, 4.5D);

        if (object == null) {
            return super.onBlockDestroyed(stack, world, state, pos, player);
        }

        EnumFacing side = object.sideHit;
        int xmove = 0;
        int ymove = 0;
        int zmove = 0;

        if (side == EnumFacing.UP || side == EnumFacing.DOWN) {
            xmove = 1;
            zmove = 1;
        } else {
            ymove = 1;
            if (side == EnumFacing.WEST || side == EnumFacing.EAST) {
                zmove = 1;
            } else {
                xmove = 1;
            }
        }

        float strength = ForgeHooks.blockStrength(state, player, world, pos);

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for (int i = -xmove; i <= xmove; i++) {
            for (int j = -ymove; j <= ymove; j++) {
                for (int k = -zmove; k <= zmove; k++) {
                    if ((x + i) != x || (y + j) != y || (z + k) != z) {
                        checkBlockBreak(world, player, new BlockPos(x + i, y + j, z + k), stack, strength, block, side);
                    }
                }
            }
        }
        return false;
    }

    public void checkBlockBreak(World world, EntityPlayer player, BlockPos pos, ItemStack stack, float strength, Block originalBlock,
                                EnumFacing side) {
        IBlockState state = world.getBlockState(pos);
        Block breakBlock = state.getBlock();
        Material material = originalBlock.getMaterial(state);
        if (breakBlock.getMaterial(state) == material && ForgeHooks.canHarvestBlock(breakBlock, player, world, pos)
                && stack.canHarvestBlock(state)) {
            float newStrength = ForgeHooks.blockStrength(state, player, world, pos);
            if (newStrength > 0f && strength / newStrength <= 10f) {
                if ((double) breakBlock.getBlockHardness(state, world, pos) != 0.0D) {
                    if (DamageHandler.handleDamage(false, state, stack, player)) {
                        BlockUtils.tryHarvestBlock(world, state, pos, side, player);
                    }
                } else {
                    BlockUtils.tryHarvestBlock(world, state, pos, side, player);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        Iterator<HammerType> iterator = HammerX_Items.hammerTypes.iterator();
        while (iterator.hasNext()) {
            HammerType hammerType = (HammerType) iterator.next();
            ItemStack stack = new ItemStack(this);
            NBTTagCompound tagCompound = RandomUtils.getNBT(stack);
            tagCompound.setString(HammerKey, hammerType.getName());

            if (this.isInCreativeTab(tab))
                list.add(stack);
        }
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        HammerType type = HammerInfoHandler.getHammerType(stack);
        if (type != null) {
            return (I18n.format("item.hammerx.hammer." + type.getName().toLowerCase() + ".name"));
        }
        return (I18n.format("item.hammerx.hammer.brokenHammer.name"));
    }

    @Override
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        return EnergyHandler.receiveEnergy(stack, maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        return EnergyHandler.extractEnergy(stack, maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ItemStack stack) {
        return EnergyHandler.getEnergyStored(stack);
    }

    @Override
    public int getMaxEnergyStored(ItemStack stack) {
        return EnergyHandler.getMaxEnergyStored(stack);
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return HammerInfoHandler.getUsesMana(stack);
    }

    @Override
    public boolean hasEmber(ItemStack stack) {
        return HammerInfoHandler.getUsesEmber(stack);
    }


	/*@Override
	public boolean doRepair(ItemStack stack, EntityPlayer player, int enchantlevel)
	{
		return HammerInfoHandler.getCanRepair(stack);
	}*/

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double) (2F + HammerInfoHandler.getAttackValue(stack)), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) (-3.5F + HammerInfoHandler.getAttackSpeed(stack)), 0));
        }
        return multimap;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return stack.isItemEnchanted() ? HammerInfoHandler.getEnchantedRarity(stack) : HammerInfoHandler.getRarity(stack);
    }

    @Override
    public boolean spawnSentientEntityOnDrop(ItemStack itemStack, EntityPlayer entityPlayer) {
        return false;
    }

    @Override
    public boolean isHammer(ItemStack itemStack) {
        if (Loader.isModLoaded("exnihilocreatio")) {

            return true;


        }
        return false;
    }

    @Override
    public int getMiningLevel(ItemStack itemStack) {
        return toolMaterial.getHarvestLevel();
    }









	/*@Override
	public int getWarp(ItemStack stack, EntityPlayer player)
	{
		return HammerInfoHandler.getWarp(stack);
	}*/
}
