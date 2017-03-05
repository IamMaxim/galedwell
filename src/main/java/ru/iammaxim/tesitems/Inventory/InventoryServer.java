package ru.iammaxim.tesitems.Inventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import ru.iammaxim.tesitems.Networking.MessageEquip;
import ru.iammaxim.tesitems.Networking.MessageInventoryUpdate;
import ru.iammaxim.tesitems.Networking.MessageItemDrop;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/27/16 at 12:37 PM.
 */
public class InventoryServer extends Inventory {
    @Override
    public void addItemWithoutNotify(ItemStack stack) {
        super.addItemWithoutNotify(stack);
        sendMessage(new MessageInventoryUpdate(MessageInventoryUpdate.ACTION_ADD_WITHOUT_NOTIFY, stack), player);
    }

    private EntityPlayer player;

    public InventoryServer(EntityPlayer player) {
        this.player = player;
    }

    private void sendMessage(IMessage msg, EntityPlayer player) {
        TESItems.networkWrapper.sendTo(msg, (EntityPlayerMP) player);
    }

    @Override
    public void clear() {
        super.clear();
        sendMessage(new MessageInventoryUpdate(MessageInventoryUpdate.ACTION_CLEAR), player);
    }

    @Override
    public void addItem(ItemStack stack) {
        super.addItem(stack);
        sendMessage(new MessageInventoryUpdate(MessageInventoryUpdate.ACTION_ADD, stack), player);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        super.setItem(index, stack);
        sendMessage(new MessageInventoryUpdate(MessageInventoryUpdate.ACTION_SET, index, stack), player);
    }

    @Override
    public boolean removeItem(Item item) {
        sendMessage(new MessageInventoryUpdate(MessageInventoryUpdate.ACTION_REMOVE_ITEM, item), player);
        return super.removeItem(item);
    }

    @Override
    public boolean removeItem(int index) {
        sendMessage(new MessageInventoryUpdate(MessageInventoryUpdate.ACTION_REMOVE_INDEX, index), player);
        return super.removeItem(index);
    }

    @Override
    public void drop(Entity entity, int index, int count) {
        ItemStack is = get(index);
        super.drop(entity, index, count);
        calculateCarryweight();
        if (is.stackSize > 0)
            sendMessage(new MessageItemDrop(index, is.stackSize - count), player);
        else sendMessage(new MessageInventoryUpdate(MessageInventoryUpdate.ACTION_REMOVE_INDEX, index), player);
    }

    @Override
    public void equip(EntityEquipmentSlot slot, int index) {
//        System.out.println("Equipping " + index + " (" + get(index) + ") to slot " + slot);
        if (index == -1)
            player.setItemStackToSlot(slot, null);
        else
            player.setItemStackToSlot(slot, get(index));
        TESItems.networkWrapper.sendTo(new MessageEquip(slot.toString(), index), (EntityPlayerMP) player);
    }
}
