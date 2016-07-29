package ru.iammaxim.tesitems.Networking;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 12.07.2016.
 */
public class SpellbookMessageHandler implements IMessageHandler<SpellbookMessage, IMessage> {
    @Override
    public IMessage onMessage(SpellbookMessage message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            IPlayerAttributesCapability cap = Minecraft.getMinecraft().thePlayer.getCapability(TESItems.attributesCapability, null);
            cap.loadSpellbook(message.tag);
        });
        return null;
    }
}
