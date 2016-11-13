package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by maxim on 8/5/16 at 9:18 PM.
 */
public class DialogMessage implements IMessage {
    public NBTTagCompound tag;

    public DialogMessage() {}

    public DialogMessage(NBTTagCompound tag) {
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
     * Created by maxim on 8/5/16 at 9:18 PM.
     */
    public static class Handler implements IMessageHandler<DialogMessage, IMessage> {
        @Override
        public IMessage onMessage(DialogMessage message, MessageContext ctx) {
            return null;
        }
    }
}