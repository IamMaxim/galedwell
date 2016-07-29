package ru.iammaxim.tesitems.Networking;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestManager;

/**
 * Created by Maxim on 12.07.2016.
 */
public class QuestUpdateMessageHandler implements IMessageHandler<QuestUpdateMessage, IMessage> {
    @Override
    public IMessage onMessage(QuestUpdateMessage message, MessageContext ctx) {
        Quest quest = QuestManager.getQuestFromNBT(message.tag);
        QuestManager.questList.put(quest.id, quest);
        return null;
    }
}
