package ru.iammaxim.tesitems.NPC;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import ru.iammaxim.tesitems.Dialogs.Dialog;
import ru.iammaxim.tesitems.Items.mItems;
import ru.iammaxim.tesitems.Networking.MessageDialog;
import ru.iammaxim.tesitems.Networking.MessageFactionList;
import ru.iammaxim.tesitems.Networking.MessageNPCUpdate;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;

/**
 * Created by Maxim on 19.07.2016.
 */
public class EntityNPC extends EntityLivingBase {
    public NPC npc;

    public EntityNPC(World worldIn) {
        super(worldIn);
        npc = new NPC();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return !npc.isInvulnerable && super.attackEntityFrom(source, amount);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        npc.writeToNBT(tag);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        npc.readFromNBT(tag);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return Arrays.asList(npc.inventory.armorInventory);
    }

    @Nullable
    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return slotIn == EntityEquipmentSlot.MAINHAND ? npc.inventory.getMainhandItem() :
                (slotIn == EntityEquipmentSlot.OFFHAND ? npc.inventory.getOffhandItem() :
                        (slotIn
                                .getSlotType()
                                == EntityEquipmentSlot
                                .Type
                                .ARMOR ? npc
                                .inventory
                                .armorInventory
                                [slotIn
                                .getIndex()]
                                : null));
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, @Nullable ItemStack stack) {
        if (slotIn == EntityEquipmentSlot.MAINHAND) setHeldItem(EnumHand.MAIN_HAND, stack);
        else if (slotIn == EntityEquipmentSlot.OFFHAND) setHeldItem(EnumHand.OFF_HAND, stack);
        else if (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR) npc.inventory.armorInventory[slotIn.getIndex()] = stack;
    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return npc.primaryHand;
    }

    public boolean isWearing(EnumPlayerModelParts part) {
        return false;
    }

    @Override
    public String getCustomNameTag() {
        return npc.name;
    }

    @Override
    public String getName() {
        return npc.name;
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, @Nullable ItemStack stack, EnumHand hand) {
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (player.worldObj.isRemote) {
            cap.setLatestNPC(npc);
            return EnumActionResult.SUCCESS;
        }

        //if latest NPC player interacted with is not this NPC, send him this NPC data (name etc.)
        if (cap.getLatestNPC() != npc) {
            TESItems.networkWrapper.sendTo(new MessageNPCUpdate(npc.getNBT()), (EntityPlayerMP) player);
            cap.setLatestNPC(npc);
        }
        if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() == mItems.itemNPCEditorTool) {
            //let the player edit this NPC
            TESItems.networkWrapper.sendTo(new MessageFactionList(), (EntityPlayerMP) player);
            player.openGui(TESItems.instance, TESItems.guiNPCEditor, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        } else {
            //open dialog GUI
            TESItems.networkWrapper.sendTo(new MessageDialog(Dialog.createDialogForPlayer(npc, player).saveToNBT()), (EntityPlayerMP) player);
            player.openGui(TESItems.instance, TESItems.guiNpcDialog, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
            return EnumActionResult.SUCCESS;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(npc.name);
    }
}
