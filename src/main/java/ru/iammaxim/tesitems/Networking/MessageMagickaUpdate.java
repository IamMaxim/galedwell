package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 6/6/17 at 3:16 PM.
 */
public class MessageMagickaUpdate implements IMessage {
    public float magicka;

    public MessageMagickaUpdate() {}

    public MessageMagickaUpdate(float magicka) {
        this.magicka = magicka;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        magicka = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(magicka);
    }

    public static class ClientHandler implements IMessageHandler<MessageMagickaUpdate, IMessage> {

        @Override
        public IMessage onMessage(MessageMagickaUpdate message, MessageContext ctx) {
            IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());
            cap.setMagicka(message.magicka);
            return null;
        }
    }
}
