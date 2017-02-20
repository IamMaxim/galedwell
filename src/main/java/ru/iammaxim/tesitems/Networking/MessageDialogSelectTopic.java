package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Dialogs.Dialog;
import ru.iammaxim.tesitems.Dialogs.DialogTopic;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.Scripting.ScriptEngine;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 12/31/16 at 11:38 AM.
 */
public class MessageDialogSelectTopic implements IMessage {
    String topicName;

    public MessageDialogSelectTopic() {}

    public MessageDialogSelectTopic(DialogTopic topic) {
        topicName = topic.name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        topicName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, topicName);
    }

    public static class ServerHandler implements IMessageHandler<MessageDialogSelectTopic, IMessage> {
        @Override
        public IMessage onMessage(MessageDialogSelectTopic message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            IPlayerAttributesCapability cap = TESItems.getCapability(player);
            Dialog d = cap.getLatestDialog();
            d.topics.forEach((name, topic) -> {
                if (name.equals(message.topicName)) {
                    Quest attachedTo = topic.attachedTo;
                    QuestInstance inst = null;
                    if (attachedTo != null)
                        inst = cap.getQuest(attachedTo.id);
                    ScriptEngine.processScript(cap.getLatestNPC(), player, topic.object, cap.getVariableStorage(), inst);
                }
            });
            return new MessageDialog(Dialog.createDialogForPlayer(cap.getLatestNPC(), player).saveToNBT());
        }
    }
}
