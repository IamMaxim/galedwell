package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Craft.CraftRecipes;

/**
 * Created by maxim on 3/5/17 at 10:14 AM.
 */
public class MessageRecipes implements IMessage {
    private NBTTagCompound tag;

    public MessageRecipes() {}

    public MessageRecipes(NBTTagCompound tag) {
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

    public static class ClientHandler implements IMessageHandler<MessageRecipes, IMessage> {
        @Override
        public IMessage onMessage(MessageRecipes message, MessageContext ctx) {
            CraftRecipes.clientLoadFromNBT(message.tag);
            return null;
        }
    }
}
