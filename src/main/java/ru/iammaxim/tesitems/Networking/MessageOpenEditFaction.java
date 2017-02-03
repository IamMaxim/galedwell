package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 2/3/17 at 7:59 PM.
 */
public class MessageOpenEditFaction implements IMessage {
    public int id;

    public MessageOpenEditFaction() {}

    public MessageOpenEditFaction(int id) {
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
    }

    public static class ServerHandler implements IMessageHandler<MessageOpenEditFaction, IMessage> {

        @Override
        public IMessage onMessage(MessageOpenEditFaction message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            TESItems.networkWrapper.sendTo(new MessageFaction(FactionManager.getFaction(message.id)), (EntityPlayerMP) player);
            player.openGui(TESItems.instance, TESItems.guiFactionEditor, ((EntityPlayerMP) player).worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
            return null;
        }
    }
}
