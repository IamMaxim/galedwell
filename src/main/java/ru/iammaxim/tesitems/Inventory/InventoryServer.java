package ru.iammaxim.tesitems.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import ru.iammaxim.tesitems.Networking.InventoryUpdateMessage;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/27/16 at 12:37 PM.
 */
public class InventoryServer extends Inventory {
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
        sendMessage(new InventoryUpdateMessage(InventoryUpdateMessage.ACTION_CLEAR), player);
    }

    @Override
    public void addItem(ItemStack stack) {
        super.addItem(stack);
        sendMessage(new InventoryUpdateMessage(InventoryUpdateMessage.ACTION_ADD, stack), player);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        super.setItem(index, stack);
        sendMessage(new InventoryUpdateMessage(InventoryUpdateMessage.ACTION_SET, index, stack), player);
    }

    @Override
    public boolean removeItem(Item item) {
        sendMessage(new InventoryUpdateMessage(InventoryUpdateMessage.ACTION_REMOVE_ITEM, item), player);
        return super.removeItem(item);
    }

    @Override
    public boolean removeItem(int index) {
        sendMessage(new InventoryUpdateMessage(InventoryUpdateMessage.ACTION_REMOVE_INDEX, index), player);
        return super.removeItem(index);
    }
}
