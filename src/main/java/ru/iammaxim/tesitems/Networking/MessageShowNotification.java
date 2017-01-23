package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.GUI.NotificationManager;

/**
 * Created by maxim on 1/23/17 at 9:37 PM.
 */
public class MessageShowNotification implements IMessage {
    public String text;

    public MessageShowNotification() {}

    public MessageShowNotification(String text) {
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        text = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, text);
    }

    public static class Handler implements IMessageHandler<MessageShowNotification, IMessage> {

        @Override
        public IMessage onMessage(MessageShowNotification message, MessageContext ctx) {
            NotificationManager.addNotification(message.text);

            return null;
        }
    }
}
