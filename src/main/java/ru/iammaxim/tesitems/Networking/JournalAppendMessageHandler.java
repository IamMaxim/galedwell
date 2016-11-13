package ru.iammaxim.tesitems.Networking;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 11/5/16 at 4:24 PM.
 */
public class JournalAppendMessageHandler implements IMessageHandler<JournalAppendMessage, IMessage> {
    @Override
    public IMessage onMessage(JournalAppendMessage message, MessageContext ctx) {
        TESItems.getMinecraft().addScheduledTask(() -> {
            IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());
            cap.journalAppend(message.s);
        });
        return null;
    }
}
