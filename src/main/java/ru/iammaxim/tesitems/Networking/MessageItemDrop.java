package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Inventory.Inventory;

/**
 * Created by maxim on 8/5/16 at 9:02 AM.
 */
public class MessageItemDrop implements IMessage {
    public int index, count;

    public MessageItemDrop() {}

    public MessageItemDrop(int index, int count) {
        this.index = index;
        this.count = count;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        index = buf.readInt();
        count = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(index);
        buf.writeInt(count);
    }

    /**
     * Created by maxim on 8/5/16 at 9:06 AM.
     */
    public static class ServerHandler implements IMessageHandler<MessageItemDrop, IMessage> {
        @Override
        public IMessage onMessage(MessageItemDrop message, MessageContext ctx) {
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
            ctx.getServerHandler().playerEntity.worldObj.getMinecraftServer().addScheduledTask(() ->
                    inv.drop(player, message.index, message.count));
            is.stackSize -= message.count;
            if (is.stackSize != 0)
                return new MessageInventoryUpdate(MessageInventoryUpdate.ACTION_SET, message.index, is);
            return null;
        }
    }
}
