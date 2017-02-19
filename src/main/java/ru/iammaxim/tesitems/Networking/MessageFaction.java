package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
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

    public MessageFaction() {
    }

    public MessageFaction(Faction faction) {
        this.faction = faction;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound tag = ByteBufUtils.readTag(buf);
        //faction == null
        if (tag.getKeySet().size() == 0) {
            System.out.println("faction is null");
            faction = null;
        } else
            faction = Faction.loadFromNBT(tag);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (faction == null)
            ByteBufUtils.writeTag(buf, new NBTTagCompound());
        else
            ByteBufUtils.writeTag(buf, faction.writeToNBT());
    }

    public static class ServerHandler implements IMessageHandler<MessageFaction, IMessage> {
        @Override
        public IMessage onMessage(MessageFaction message, MessageContext ctx) {
            System.out.println("Handling MessageFaction");

            if (message.faction == null) {
                System.out.println("Error handling faction");
                return new MessageShowNotification("Error handling faction");
            }

            EntityPlayerMP player = ctx.getServerHandler().playerEntity;

            //todo: add permission check
//            if (player.per)
            if (FactionManager.factions.get(message.faction.id) == null || message.faction.id == -1)
                message.faction.id = FactionManager.idGen.genID();
            System.out.println("setting faction " + message.faction.writeToNBT().toString());
            FactionManager.addFaction(message.faction);

            return new MessageFaction(message.faction);
        }
    }

    public static class ClientHandler implements IMessageHandler<MessageFaction, IMessage> {
        @Override
        public IMessage onMessage(MessageFaction message, MessageContext ctx) {
            if (message.faction == null) {
                System.out.println("Error handling faction");
                return null;
            }

            System.out.println("Handling MessageFaction " + message.faction.writeToNBT().toString());

            FactionManager.addFaction(message.faction);
            AdminTemporaryStorage.lastEditedFaction = message.faction;
            return null;
        }
    }
}
