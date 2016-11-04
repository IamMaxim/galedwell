package ru.iammaxim.tesitems.Networking;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Inventory.Inventory;

/**
 * Created by maxim on 8/5/16 at 9:06 AM.
 */
public class ItemDropMessageHandlerServer implements IMessageHandler<ItemDropMessage, IMessage> {
    @Override
    public IMessage onMessage(ItemDropMessage message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        Inventory inv = Inventory.getInventory(player);
        if (inv.size() <= message.index) {
            System.out.println("trying to drop item that doesn't exist");
            return null;
        }
        ItemStack is = inv.get(message.index).copy();
        if (is.stackSize - message.count < 0) {
            System.out.println("something went wrong. Trying to drop more items than stack has");
            return null;
        }
//        ItemStack is = inv.get(message.index).copy();
        ctx.getServerHandler().playerEntity.worldObj.getMinecraftServer().addScheduledTask(() ->
                inv.drop(player, message.index, message.count));
        System.out.println("handling drop: " + message.index + " " + message.count);
//        is.stackSize -= message.count;
        is.stackSize -= message.count;
        if (is.stackSize == 0)
            return new InventoryUpdateMessage(InventoryUpdateMessage.ACTION_REMOVE_INDEX, message.index);
        else
            return new InventoryUpdateMessage(InventoryUpdateMessage.ACTION_SET, message.index, is);
    }
}
