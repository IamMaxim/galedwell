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
public class OpenGuiMessage implements IMessage {
    public int GuiID;

    public OpenGuiMessage() {}

    public OpenGuiMessage(int GuiID) {
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
    public static class Handler implements IMessageHandler<OpenGuiMessage, IMessage> {
        @Override
        public IMessage onMessage(OpenGuiMessage message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            player.openGui(TESItems.instance, message.GuiID, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
            return null;
        }
    }
}
