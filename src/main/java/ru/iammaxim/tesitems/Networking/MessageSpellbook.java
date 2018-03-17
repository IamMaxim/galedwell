package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.GUI.ScreenStack;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 12.07.2016.
 */
public class MessageSpellbook implements IMessage {
    public NBTTagCompound tag;
    public boolean needScripts = false;

    public MessageSpellbook() {
    }

    public MessageSpellbook(NBTTagCompound nbttag) {
        tag = nbttag;
    }

    public MessageSpellbook setNeedScripts() {
        needScripts = true;
        return this;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        if (buf.readBoolean())
            tag = ByteBufUtils.readTag(buf);
        else
            needScripts = buf.readBoolean();
    }

    /**
     * Created by Maxim on 12.07.2016.
     */
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(tag != null);
        if (tag != null)
            ByteBufUtils.writeTag(buf, tag);
        else
            buf.writeBoolean(needScripts);
    }

    // used to request the spellbook
    public static class ServerHandler implements IMessageHandler<MessageSpellbook, IMessage> {

        @Override
        public IMessage onMessage(MessageSpellbook message, MessageContext ctx) {
            return new MessageSpellbook(TESItems.getCapability(ctx.getServerHandler().playerEntity).saveSpellbook(message.needScripts));
        }
    }

    public static class ClientHandler implements IMessageHandler<MessageSpellbook, IMessage> {
        @Override
        public IMessage onMessage(MessageSpellbook message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                IPlayerAttributesCapability cap = Minecraft.getMinecraft().thePlayer.getCapability(TESItems.playerAttributesCapability, null);

                System.out.println("loading spellbook: " + message.tag);

                cap.loadSpellbook(message.tag);
                ScreenStack.processCallback("spellbookUpdated", null);
            });
            return null;
        }

    }
}
