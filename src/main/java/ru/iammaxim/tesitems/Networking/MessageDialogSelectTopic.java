package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Dialogs.DialogTopic;
import ru.iammaxim.tesitems.GUI.GuiNPCDialog;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 12/31/16 at 11:38 AM.
 */
public class MessageDialogSelectTopic implements IMessage {


    public MessageDialogSelectTopic() {}

    public MessageDialogSelectTopic(DialogTopic topic) {
//        topic.
    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class ServerHandler implements IMessageHandler<MessageDialogSelectTopic, IMessage> {
        @Override
        public IMessage onMessage(MessageDialogSelectTopic message, MessageContext ctx) {
            GuiScreen currentScreen = TESItems.getMinecraft().currentScreen;
            if (currentScreen instanceof GuiNPCDialog) {

            }

                return null;
        }
    }
}
