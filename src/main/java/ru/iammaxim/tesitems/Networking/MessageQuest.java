package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestManager;
import ru.iammaxim.tesitems.Questing.QuestStage;

import java.util.Iterator;

public class MessageQuest implements IMessage {
    public Quest quest;

    public MessageQuest() {}

    public MessageQuest(Quest quest) {
        this.quest = quest;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        quest = Quest.readFromNBT(ByteBufUtils.readTag(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, quest.writeToNBT());
    }

    public static class ClientHandler implements IMessageHandler<MessageQuest, IMessage> {
        @Override
        public IMessage onMessage(MessageQuest message, MessageContext ctx) {
            QuestManager.questList.put(message.quest.id, message.quest);
//            if (ScreenStack.)
            return null;
        }
    }

    public static class ServerHandler implements IMessageHandler<MessageQuest, IMessage> {

        @Override
        public IMessage onMessage(MessageQuest message, MessageContext ctx) {
            if (message.quest.id == -1)
                message.quest.id = QuestManager.idGen.genID();

            Iterator<QuestStage> it = message.quest.stages.iterator();
            while (it.hasNext()) {
                QuestStage stage = it.next();

                if (stage.journalLine.isEmpty()) {
                    it.remove();
                    continue;
                }

                if (stage.id == -1)
                    stage.id = message.quest.idGen.genID();

            }

            QuestManager.questList.put(message.quest.id, message.quest);
            return new MessageQuest(message.quest);
        }
    }
}
