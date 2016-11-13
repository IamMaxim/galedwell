package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestManager;

/**
 * Created by Maxim on 12.07.2016.
 */
public class QuestUpdateMessage implements IMessage {
    public NBTTagCompound tag;

    public QuestUpdateMessage() {}

    public QuestUpdateMessage(NBTTagCompound nbttag) {
        tag = nbttag;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tag = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, tag);
    }

    /**
     * Created by Maxim on 12.07.2016.
     */
    public static class Handler implements IMessageHandler<QuestUpdateMessage, IMessage> {
        @Override
        public IMessage onMessage(QuestUpdateMessage message, MessageContext ctx) {
            Quest quest = QuestManager.getQuestFromNBT(message.tag);
            QuestManager.questList.put(quest.id, quest);
            return null;
        }
    }
}
