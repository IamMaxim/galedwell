package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

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
    }

    @Override
    public void fromBytes(ByteBuf buf) {
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
}
