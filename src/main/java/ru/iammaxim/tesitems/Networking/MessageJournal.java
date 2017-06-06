package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 11/5/16 at 4:12 PM.
 */
public class MessageJournal implements IMessage {
    public String s;

    public MessageJournal() {};

    public MessageJournal(String s) {
        this.s = s;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        s = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, s);
    }

    /**
     * Created by maxim on 11/5/16 at 4:14 PM.
     */
    public static class ClientHandler implements IMessageHandler<MessageJournal, IMessage> {
        @Override
        public IMessage onMessage(MessageJournal message, MessageContext ctx) {
            TESItems.getMinecraft().addScheduledTask(() -> {
                IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());
                cap.setJournal(message.s);
            });
            return null;
        }
    }
}
