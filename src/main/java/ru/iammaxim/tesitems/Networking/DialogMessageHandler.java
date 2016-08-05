package ru.iammaxim.tesitems.Networking;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by maxim on 8/5/16 at 9:18 PM.
 */
public class DialogMessageHandler implements IMessageHandler<DialogMessage, IMessage> {
    @Override
    public IMessage onMessage(DialogMessage message, MessageContext ctx) {
        return null;
    }
}