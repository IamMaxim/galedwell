package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Questing.QuestManager;

/**
 * Created by Maxim on 12.07.2016.
 */
public class MessageQuestList implements IMessage {
    NBTTagList tag;

    public MessageQuestList() {}

    public MessageQuestList(NBTTagList nbttag) {
        tag = nbttag;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tag = (NBTTagList) ByteBufUtils.readTag(buf).getTag("quests");
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("quests", tag);
        ByteBufUtils.writeTag(buf, nbt);
    }

    /**
     * Created by Maxim on 12.07.2016.
     */
    public static class Handler implements IMessageHandler<MessageQuestList, IMessage> {
        @Override
        public IMessage onMessage(MessageQuestList message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> QuestManager.readFromNBT(message.tag));
            return null;
        }
    }
}
