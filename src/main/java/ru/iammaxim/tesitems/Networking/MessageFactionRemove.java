package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.Factions.FactionManager;

/**
 * Created by maxim on 02.01.2017.
 */
public class MessageFactionRemove implements IMessage {
    public int factionID;

    public MessageFactionRemove() {}

    public MessageFactionRemove(int factionID) {
        this.factionID = factionID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        factionID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(factionID);
    }

    public static class ServerHandler implements IMessageHandler<MessageFactionRemove, IMessage> {

        @Override
        public IMessage onMessage(MessageFactionRemove message, MessageContext ctx) {
            //todo: add permission check

            Faction f = FactionManager.factions.remove(message.factionID);
            //todo: check if needed to notify all online clients that their factions was changed and add this feature if needed
            /*if (f != null) {

            }*/
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<MessageFactionRemove, IMessage> {

        @Override
        public IMessage onMessage(MessageFactionRemove message, MessageContext ctx) {
            FactionManager.factions.remove(message.factionID);
            return null;
        }
    }
}
