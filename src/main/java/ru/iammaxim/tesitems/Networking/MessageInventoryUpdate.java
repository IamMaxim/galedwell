package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.GUI.GuiInventory;
import ru.iammaxim.tesitems.GUI.Screen;
import ru.iammaxim.tesitems.GUI.ScreenStack;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.TESItems;
import ru.iammaxim.tesitems.Utils.Utils;

/**
 * Created by maxim on 7/26/16 at 6:20 PM.
 */
public class MessageInventoryUpdate implements IMessage {
    public static final byte ACTION_ADD = 0, ACTION_SET = 1, ACTION_REMOVE_INDEX = 2, ACTION_REMOVE_ITEM = 3, ACTION_CLEAR = 4, ACTION_ADD_WITHOUT_NOTIFY = 5;
    public byte action;
    public ItemStack stack;
    public int index;

    public MessageInventoryUpdate() {}

    public MessageInventoryUpdate(byte action, Object... data) {
        this.action = action;
        switch (action) {
            case ACTION_ADD:
            case ACTION_ADD_WITHOUT_NOTIFY:
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
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        action = buf.readByte();
        switch (action) {
            case ACTION_ADD:
            case ACTION_ADD_WITHOUT_NOTIFY:
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
            case ACTION_ADD_WITHOUT_NOTIFY:
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
    public static class ClientHandler implements IMessageHandler<MessageInventoryUpdate, IMessage> {
        @Override
        public IMessage onMessage(MessageInventoryUpdate message, MessageContext ctx) {
            EntityPlayer player = TESItems.getClientPlayer();
            Inventory inv = TESItems.getCapability(player).getInventory();
            switch(message.action) {
                case ACTION_ADD:
                    inv.addItem(message.stack);
                    break;
                case ACTION_ADD_WITHOUT_NOTIFY:
                    inv.addItemWithoutNotify(message.stack);
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

            Screen lastScreen = ScreenStack.lastScreen();
            if (lastScreen instanceof GuiInventory) {
                ((GuiInventory) lastScreen).update();
                ((GuiInventory) lastScreen).updateTable();
            }
            return null;
        }
    }

    /**
     * Created by maxim on 7/27/16 at 11:46 AM.
     */
    public static class ServerHandler implements IMessageHandler<MessageInventoryUpdate, IMessage> {
        @Override
        public IMessage onMessage(MessageInventoryUpdate message, MessageContext ctx) {
            Inventory inv = TESItems.getCapability(ctx.getServerHandler().playerEntity).getInventory();
            switch(message.action) {
                case ACTION_ADD:
                case ACTION_ADD_WITHOUT_NOTIFY: //we don't need to disable notifications on server
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
//                    inv.clear();
                    Utils.showNotification(ctx.getServerHandler().playerEntity, "Cannot clear from client side");
                    break;
            }
            return null;
        }
    }
}
