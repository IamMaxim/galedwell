package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestManager;

import java.util.HashMap;

/**
 * Created by Maxim on 12.07.2016.
 */
public class MessageQuestList implements IMessage {
    public HashMap<Integer, String> questList;

    public MessageQuestList() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        questList = new HashMap<>();
        int count = buf.readInt();
        for (int i = count; i > 0; i--) {
            questList.put(buf.readInt(), ByteBufUtils.readUTF8String(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(QuestManager.questList.size());
        QuestManager.questList.forEach((id, quest) -> {
            buf.writeInt(id);
            ByteBufUtils.writeUTF8String(buf, quest.name);
        });
    }

    /**
     * Created by Maxim on 12.07.2016.
     */
    public static class Handler implements IMessageHandler<MessageQuestList, IMessage> {
        @Override
        public IMessage onMessage(MessageQuestList message, MessageContext ctx) {
            message.questList.forEach((id, name) -> {
                Quest quest = new Quest();
                quest.id = id;
                quest.name = name;
                QuestManager.questList.put(id, quest);
            });
            return null;
        }
    }
}
