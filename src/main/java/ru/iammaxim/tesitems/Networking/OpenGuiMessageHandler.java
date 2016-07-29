package ru.iammaxim.tesitems.Networking;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 13.07.2016.
 */
public class OpenGuiMessageHandler implements IMessageHandler<OpenGuiMessage, IMessage> {
    @Override
    public IMessage onMessage(OpenGuiMessage message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        player.openGui(TESItems.instance, message.GuiID, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        return null;
    }
}
