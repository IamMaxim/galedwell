package ru.iammaxim.tesitems.Inventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.GUI.NotificationManager;
import ru.iammaxim.tesitems.Networking.MessageEquip;
import ru.iammaxim.tesitems.Networking.MessageItemDrop;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/28/16 at 10:28 PM.
 */
public class InventoryClient extends Inventory {
    public EntityPlayer player;

    public InventoryClient(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void setMainHandItem() {
//        System.out.println("setting mainhand item to " + getMainhandItem());
        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, getMainhandItem());
    }

    @Override
    public void setOffHandItem() {
//        System.out.println("setting offhand item to " + getOffhandItem());
        player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, getOffhandItem());
    }

    @Override
    public ItemStack getMainhandItem() {
//        System.out.println("returning mainhand item: " + player.getHeldItemMainhand());
        return player.getHeldItemMainhand();
    }

    @Override
    public ItemStack getOffhandItem() {
//        System.out.println("returning offhand item: " + player.getHeldItemOffhand());
        return player.getHeldItemOffhand();
    }

    @Override
    public void addItem(ItemStack stack) {
        super.addItem(stack);
        player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2f, player.getRNG().nextFloat() * 3f);
        if (stack.stackSize > 1)
            NotificationManager.addNotification("Added (" + stack.stackSize + ") " + stack.getDisplayName());
        else
            NotificationManager.addNotification("Added " + stack.getDisplayName());
    }

    @Override
    public void addItemWithoutNotify(ItemStack stack) {
        super.addItem(stack);
    }

    @Override
    public void drop(Entity entity, int index, int count) {
//        System.out.println("dropping " + index + " (" + get(index) + ") " + count);
        TESItems.networkWrapper.sendToServer(new MessageItemDrop(index, count));
    }

    @Override
    public void equip(EntityEquipmentSlot slot, int index) {
        TESItems.networkWrapper.sendToServer(new MessageEquip(slot.getName(), index));
    }

    @Override
    public boolean isItemEquipped(int index) {
        if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand() == get(index))
            return true;
        if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand() == get(index))
            return true;
        ItemStack[] armorInv = player.inventory.armorInventory;
        for (ItemStack is : armorInv)
            if (is != null && is == get(index))
                return true;
        return false;
    }
}
