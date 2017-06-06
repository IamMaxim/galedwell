package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 06.03.2017.
 */
public class MessageGoldUpdate implements IMessage {
    public int gold;

    public MessageGoldUpdate() {}

    public MessageGoldUpdate(int gold) {
        this.gold = gold;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        gold = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(gold);
    }

    public static class ClientHandler implements IMessageHandler<MessageGoldUpdate, IMessage> {
        @Override
        public IMessage onMessage(MessageGoldUpdate message, MessageContext ctx) {
            IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());
            cap.setGold(message.gold);
            return null;
        }
    }
}
