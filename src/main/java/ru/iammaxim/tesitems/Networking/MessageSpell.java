package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.GUI.Screen;
import ru.iammaxim.tesitems.GUI.ScreenStack;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

// used to transfer spell from client to server during spell creation
// if index == -1, new spell is added
public class MessageSpell implements IMessage {
    public int index = -1;
    public SpellBase spell;

    public MessageSpell() {
    }

    public MessageSpell(SpellBase spell) {
        this.spell = spell;
    }

    public MessageSpell(SpellBase spell, int index) {
        this.spell = spell;
        this.index = index;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        index = buf.readInt();
        spell = SpellBase.loadFromNBT(ByteBufUtils.readTag(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(index);
        ByteBufUtils.writeTag(buf, spell.writeToNBT(true));
    }

    public static class ServerHandler implements IMessageHandler<MessageSpell, IMessage> {

        @Override
        public IMessage onMessage(MessageSpell message, MessageContext ctx) {
            IPlayerAttributesCapability cap = TESItems.getCapability(ctx.getServerHandler().playerEntity);

            if (message.index == -1) {
                cap.getSpellbook().add(message.spell);
                message.index = cap.getSpellbook().indexOf(message.spell);
            } else
                cap.getSpellbook().set(message.index, message.spell);

            return message;
        }
    }

    public static class ClientHandler implements IMessageHandler<MessageSpell, IMessage> {

        @Override
        public IMessage onMessage(MessageSpell message, MessageContext ctx) {

            if (message.index != -1)
                ScreenStack.processCallback("spellIndexChanged", message.index);

            ScreenStack.processCallback("update", null);

            return null;
        }
    }
}
