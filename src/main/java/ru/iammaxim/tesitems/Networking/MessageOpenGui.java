package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 13.07.2016.
 */
public class MessageOpenGui implements IMessage {
    public int GuiID;

    public MessageOpenGui() {}

    public MessageOpenGui(int GuiID) {
        this.GuiID = GuiID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            GuiID = buf.readInt();
        } catch (Exception e) {}
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(GuiID);
    }

    /**
     * Created by Maxim on 13.07.2016.
     */
    public static class ServerHandler implements IMessageHandler<MessageOpenGui, IMessage> {
        @Override
        public IMessage onMessage(MessageOpenGui message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            player.openGui(TESItems.instance, message.GuiID, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
            return null;
        }
    }
}
