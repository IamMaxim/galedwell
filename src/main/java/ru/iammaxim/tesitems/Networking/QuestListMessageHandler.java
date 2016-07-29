package ru.iammaxim.tesitems.Networking;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Questing.QuestManager;

/**
 * Created by Maxim on 12.07.2016.
 */
public class QuestListMessageHandler implements IMessageHandler<QuestListMessage, IMessage> {
    @Override
    public IMessage onMessage(QuestListMessage message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            QuestManager.loadFromNBT(message.tag);
        });
        return null;
    }
}
