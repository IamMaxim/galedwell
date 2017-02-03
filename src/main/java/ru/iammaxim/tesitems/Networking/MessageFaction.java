package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.Player.AdminTemporaryStorage;

/**
 * Created by maxim on 01.01.2017.
 */
public class MessageFaction implements IMessage {
    public Faction faction;

    public MessageFaction() {}

    public MessageFaction(Faction faction) {
        this.faction = faction;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        faction = Faction.loadFromNBT(ByteBufUtils.readTag(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, faction.writeToNBT());
    }

    public static class ServerHandler implements IMessageHandler<MessageFaction, IMessage> {
        @Override
        public IMessage onMessage(MessageFaction message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;

            //todo: add permission check
//            if (player.per)
            if (FactionManager.factions.get(message.faction.id) == null || message.faction.id == -1)
                message.faction.id = FactionManager.nextID();
            FactionManager.addFaction(message.faction);

            return new MessageFaction(message.faction);
        }
    }

    public static class ClientHandler implements IMessageHandler<MessageFaction, IMessage> {
        @Override
        public IMessage onMessage(MessageFaction message, MessageContext ctx) {
            FactionManager.addFaction(message.faction);
            AdminTemporaryStorage.lastEditedFaction = message.faction;
            return null;
        }
    }
}
