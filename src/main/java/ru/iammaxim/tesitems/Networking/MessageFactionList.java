package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.Factions.FactionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maxim on 01.01.2017.
 */
public class MessageFactionList implements IMessage {
    public HashMap<Integer, String> factions;

    @Override
    public void fromBytes(ByteBuf buf) {
        int size = buf.readInt();
        for (int i = size; i > 0; i--) {
            int id = buf.readInt();
            String name = ByteBufUtils.readUTF8String(buf);
            factions.put(id, name);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(factions.size());
        factions.forEach((id, name) -> {
            buf.writeInt(id);
            ByteBufUtils.writeUTF8String(buf, name);
        });
    }

    public static class Handler implements IMessageHandler<MessageFactionList, IMessage> {

        @Override
        public IMessage onMessage(MessageFactionList message, MessageContext ctx) {
            message.factions.forEach((id, name) -> {
                Faction f = new Faction(name);
                f.id = id;
                FactionManager.addFaction(f);
            });
            return null;
        }
    }
}
