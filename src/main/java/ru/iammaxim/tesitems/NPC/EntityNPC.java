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
import ru.iammaxim.tesitems.Dialogs.Dialog;
import ru.iammaxim.tesitems.Fractions.Faction;
import ru.iammaxim.tesitems.Fractions.FactionManager;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryNPC;
import ru.iammaxim.tesitems.Items.mItems;
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
    private Dialog dialog;

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

    public void removeFaction(Faction faction) {
        factions.remove(faction);
    }

    public void setInvulnerable(boolean invulnerable) {
        isInvulnerable = invulnerable;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setBoolean("isInvulnerable", isInvulnerable);
        tag.setString("name", name);
        tag.setTag("inventory", inventory.writeToNBT());
        tag.setTag("factions", saveFactions());
        if (dialog != null) {
            tag.setTag("dialog", dialog.saveToNBT());
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        name = tag.getString("name");
        isInvulnerable = tag.getBoolean("isInvulnerable");
        inventory.loadFromNBT(tag.getCompoundTag("inventory"));
        loadFactions((NBTTagList) tag.getTag("factions"));
        if (tag.hasKey("dialog")) {
            dialog = Dialog.loadFromNBT(tag.getCompoundTag("dialog"));
        }
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

        if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() == mItems.itemNPCEditorTool)
            player.openGui(TESItems.instance, TESItems.guiNPCEditor, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        else
            player.openGui(TESItems.instance, TESItems.guiNpcDialog, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ITextComponent getDisplayName() {
        return super.getDisplayName();
    }

    public NBTTagList saveFactions() {
        NBTTagList list = new NBTTagList();
        factions.forEach(f -> {
            NBTTagCompound idTag = new NBTTagCompound();
            idTag.setInteger("id", f.id);
            list.appendTag(idTag);
        });
        return list;
    }

    public void loadFactions(NBTTagList list) {
        for (int i = 0; i < list.tagCount(); i++) {
            factions.add(FactionManager.getFaction(list.getCompoundTagAt(i).getInteger("id")));
        }
    }
}
