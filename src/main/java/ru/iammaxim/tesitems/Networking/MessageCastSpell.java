package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.TESItems;

import java.util.List;

/**
 * Created by Maxim on 17.07.2016.
 */
public class MessageCastSpell implements IMessage {
    public int spellIndex = -1;

    public MessageCastSpell() {}

    public MessageCastSpell(int index) {
        spellIndex = index;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        spellIndex = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(spellIndex);
    }

    /**
     * Created by Maxim on 17.07.2016.
     */
    public static class ServerHandler implements IMessageHandler<MessageCastSpell, IMessage> {
        @Override
        public IMessage onMessage(MessageCastSpell message, MessageContext ctx) {
            System.out.println("casting spell " + message.spellIndex);
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            List<SpellBase> spellbook = TESItems.getCapability(player).getSpellbook();
            if (message.spellIndex < 0 || message.spellIndex >= spellbook.size()) {
                System.out.println("WARNING: " + message.spellIndex + " is out of range spell");
                return null;
            }
            SpellBase spell = spellbook.get(message.spellIndex);
            System.out.println(spell.getClass().getName());
            spell.cast(player);
            return null;
        }
    }
}
