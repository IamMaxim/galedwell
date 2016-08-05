package ru.iammaxim.tesitems.Items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;

/**
 * Created by maxim on 8/5/16 at 8:25 AM.
 */
public class EntityItemNew extends EntityItem {
    public EntityItemNew(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityItemNew(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
    }

    public EntityItemNew(World worldIn) {
        super(worldIn);
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, @Nullable ItemStack stack, EnumHand hand) {
        if (!this.worldObj.isRemote) {
            ItemStack itemstack = this.getEntityItem();
            int i = itemstack.stackSize;

            int hook = ForgeEventFactory.onItemPickup(this, player, itemstack);
            if (hook < 0) return EnumActionResult.PASS;

            if ((lifespan - getAge() <= 200 || getOwner().equals(player.getName())) && (hook == 1 || i <= 0 || player.inventory.addItemStackToInventory(itemstack))) {
                FMLCommonHandler.instance().firePlayerItemPickupEvent(player, this);
                if (!this.isSilent()) {
                    this.worldObj.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                }
                player.onItemPickup(this, i);
                if (itemstack.stackSize <= 0) {
                    this.setDead();
                }
                player.addStat(StatList.getObjectsPickedUpStats(itemstack.getItem()), i);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
    }


}
