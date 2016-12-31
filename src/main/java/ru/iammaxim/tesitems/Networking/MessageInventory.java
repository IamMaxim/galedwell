package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/27/16 at 12:18 PM.
 */
public class MessageInventory implements IMessage {
    public NBTTagCompound tag;

    public MessageInventory() {}

    public MessageInventory(NBTTagCompound tag) {
        this.tag = tag;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, tag);
    }

    /**
     * Created by maxim on 7/27/16 at 12:19 PM.
     */
    public static class Handler implements IMessageHandler<MessageInventory, IMessage> {
        @Override
        public IMessage onMessage(MessageInventory message, MessageContext ctx) {
            TESItems.getMinecraft().addScheduledTask(() -> {
                Inventory inv = TESItems.getCapability(TESItems.getClientPlayer()).getInventory();
                inv.loadFromNBT(message.tag);
                inv.calculateCarryweight();
            });
            return null;
        }
    }
}
