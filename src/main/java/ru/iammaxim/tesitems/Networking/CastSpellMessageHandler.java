package ru.iammaxim.tesitems.Networking;

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
public class CastSpellMessageHandler implements IMessageHandler<CastSpellMessage, IMessage> {
    @Override
    public IMessage onMessage(CastSpellMessage message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        List<SpellBase> spellbook = TESItems.getCapatibility(player).getSpellbook();
        if (message.spellIndex < 0 || message.spellIndex >= spellbook.size()) return null;
        SpellBase spell = spellbook.get(message.spellIndex);
        spell.cast(player);
        return null;
    }
}
