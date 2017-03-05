package ru.iammaxim.tesitems.Inventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import ru.iammaxim.tesitems.Networking.MessageInventoryUpdate;
import ru.iammaxim.tesitems.Networking.MessageLatestContainerUpdate;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 3/5/17 at 8:40 AM.
 */
public class InventoryContainerServer extends InventoryContainer {

    public InventoryContainerServer() {
    }

    private void sendMessage(IMessage msg, EntityPlayer player) {
        TESItems.networkWrapper.sendTo(msg, (EntityPlayerMP) player);
    }

    @Override
    public void clear() {
        super.clear();
        sendMessage(new MessageLatestContainerUpdate(MessageInventoryUpdate.ACTION_CLEAR), player);
    }

    @Override
    public void addItem(ItemStack stack) {
        super.addItem(stack);
        sendMessage(new MessageLatestContainerUpdate(MessageInventoryUpdate.ACTION_ADD, stack), player);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        super.setItem(index, stack);
        sendMessage(new MessageLatestContainerUpdate(MessageInventoryUpdate.ACTION_SET, index, stack), player);
    }

    @Override
    public boolean removeItem(Item item) {
        sendMessage(new MessageLatestContainerUpdate(MessageInventoryUpdate.ACTION_REMOVE_ITEM, item), player);
        return super.removeItem(item);
    }

    @Override
    public boolean removeItem(int index) {
        sendMessage(new MessageLatestContainerUpdate(MessageInventoryUpdate.ACTION_REMOVE_INDEX, index), player);
        return super.removeItem(index);
    }

    @Override
    public void drop(Entity entity, int index, int count) {
        System.out.println("Dropping from container is not implemented");
    }

    @Override
    public void equip(EntityEquipmentSlot slot, int index) {
        System.out.println("Equipping from container is not implemented");
    }
}
