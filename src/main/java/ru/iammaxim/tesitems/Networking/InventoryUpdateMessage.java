package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.TESItems;
import scala.actors.threadpool.Arrays;

import java.util.List;

/**
 * Created by maxim on 7/26/16 at 6:20 PM.
 */
public class InventoryUpdateMessage implements IMessage {
    public static final byte ACTION_ADD = 0, ACTION_SET = 1, ACTION_REMOVE_INDEX = 2, ACTION_REMOVE_ITEM = 3, ACTION_CLEAR = 4;
    public byte action;
    public ItemStack stack;
    public int index;

    public InventoryUpdateMessage() {}

    public InventoryUpdateMessage(byte action, Object... data) {
        this.action = action;
        switch (action) {
            case ACTION_ADD:
                stack = (ItemStack) data[0];
                break;
            case ACTION_SET:
                index = (int) data[0];
                stack = (ItemStack) data[1];
                break;
            case ACTION_REMOVE_INDEX:
                index = (int) data[0];
                break;
            case ACTION_REMOVE_ITEM:
                stack = new ItemStack((Item) data[0]);
                break;
        }
        System.out.println("created inventory update message");
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        System.out.println("message size: " + buf.maxCapacity());
        action = buf.readByte();
        switch (action) {
            case ACTION_ADD:
                stack = ByteBufUtils.readItemStack(buf);
                break;
            case ACTION_SET:
                index = buf.readInt();
                stack = ByteBufUtils.readItemStack(buf);
                break;
            case ACTION_REMOVE_INDEX:
                index = buf.readInt();
                break;
            case ACTION_REMOVE_ITEM:
                stack = ByteBufUtils.readItemStack(buf);
                break;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(action);
        switch (action) {
            case ACTION_ADD:
                ByteBufUtils.writeItemStack(buf, stack);
                break;
            case ACTION_SET:
                buf.writeInt(index);
                ByteBufUtils.writeItemStack(buf, stack);
                break;
            case ACTION_REMOVE_INDEX:
                buf.writeInt(index);
                break;
            case ACTION_REMOVE_ITEM:
                ByteBufUtils.writeItemStack(buf, stack);
                break;
        }
    }

    /**
     * Created by maxim on 7/27/16 at 11:46 AM.
     */
    public static class ClientHandler implements IMessageHandler<InventoryUpdateMessage, IMessage> {
        @Override
        public IMessage onMessage(InventoryUpdateMessage message, MessageContext ctx) {
            EntityPlayer player = TESItems.getClientPlayer();
            Inventory inv = TESItems.getCapability(player).getInventory();
            switch(message.action) {
                case ACTION_ADD:
                    inv.addItem(message.stack);
                    break;
                case ACTION_SET:
                    inv.setItem(message.index, message.stack);
                    break;
                case ACTION_REMOVE_INDEX:
                    inv.removeItem(message.index);
                    break;
                case ACTION_REMOVE_ITEM:
                    inv.removeItem(message.stack.getItem());
                    break;
                case ACTION_CLEAR:
                    inv.clear();
                    break;
            }
            return null;
        }
    }

    /**
     * Created by maxim on 7/27/16 at 11:46 AM.
     */
    public static class ServerHandler implements IMessageHandler<InventoryUpdateMessage, IMessage> {
        @Override
        public IMessage onMessage(InventoryUpdateMessage message, MessageContext ctx) {
            Inventory inv = TESItems.getCapability(ctx.getServerHandler().playerEntity).getInventory();
            switch(message.action) {
                case ACTION_ADD:
                    inv.addItem(message.stack);
                    break;
                case ACTION_SET:
                    inv.setItem(message.index, message.stack);
                    break;
                case ACTION_REMOVE_INDEX:
                    inv.removeItem(message.index);
                    break;
                case ACTION_REMOVE_ITEM:
                    inv.removeItem(message.stack.getItem());
                    break;
                case ACTION_CLEAR:
                    inv.clear();
                    break;
            }
            return null;
        }
    }
}
