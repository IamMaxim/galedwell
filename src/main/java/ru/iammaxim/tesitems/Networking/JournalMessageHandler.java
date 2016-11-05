package ru.iammaxim.tesitems.Networking;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 11/5/16 at 4:14 PM.
 */
public class JournalMessageHandler implements IMessageHandler<JournalMessage, IMessage> {
    @Override
    public IMessage onMessage(JournalMessage message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            IPlayerAttributesCapability cap = TESItems.getCapability(Minecraft.getMinecraft().thePlayer);
            cap.setJournal(message.s);
        });
        return null;
    }
}
