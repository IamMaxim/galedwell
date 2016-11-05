package ru.iammaxim.tesitems.NPC;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import ru.iammaxim.tesitems.Fractions.Faction;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryNPC;
import ru.iammaxim.tesitems.TESItems;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 19.07.2016.
 */
public class EntityNPC extends EntityLivingBase {
    private boolean isInvulnerable = false;
    private Inventory inventory = new InventoryNPC(this);
    private EnumHandSide mainHand = EnumHandSide.RIGHT;
    private List<Faction> factions = new ArrayList<>();

    private String name = "NPC";

    public EntityNPC(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (isInvulnerable) return false;
        return super.attackEntityFrom(source, amount);
    }

    public void addFaction(Faction faction) {
        factions.add(faction);
    }

    public void setInvulnerable(boolean invulnerable) {
        isInvulnerable = invulnerable;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setBoolean("isInvulnerable", isInvulnerable);
        tag.setString("name", name);
        tag.setTag("inventory", inventory.writeToNBT());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return Arrays.asList(inventory.armorInventory);
    }

    @Nullable
    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return slotIn == EntityEquipmentSlot.MAINHAND ? this.inventory.getMainhandItem() : (slotIn == EntityEquipmentSlot.OFFHAND ? this.inventory.getOffhandItem() : (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR ? this.inventory.armorInventory[slotIn.getIndex()] : null));
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, @Nullable ItemStack stack) {

    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return mainHand;
    }


    public boolean isWearing(EnumPlayerModelParts part) {
        return false;
    }

    @Override
    public String getCustomNameTag() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, @Nullable ItemStack stack, EnumHand hand) {
        if (player.worldObj.isRemote) {
            TESItems.getCapability(player).setLatestNPC(this);
            return EnumActionResult.SUCCESS;
        }

        player.openGui(TESItems.instance, TESItems.guiNpcDialog, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ITextComponent getDisplayName() {
        return super.getDisplayName();
    }

    public NBTTagCompound saveFactions() {
        NBTTagList list = new NBTTagList();
        factions.forEach(f -> list.appendTag(f.id));
    }
}
