package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 12.07.2016.
 */
public class MessageSpellbook implements IMessage {
    NBTTagCompound tag;

    public MessageSpellbook() {}

    public MessageSpellbook(NBTTagCompound nbttag) {
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
    public static class Handler implements IMessageHandler<MessageSpellbook, IMessage> {
        @Override
        public IMessage onMessage(MessageSpellbook message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                IPlayerAttributesCapability cap = Minecraft.getMinecraft().thePlayer.getCapability(TESItems.playerAttributesCapability, null);

                System.out.println("loading spellbook: " + message.tag);

                cap.loadSpellbook(message.tag);
            });
            return null;
        }
    }
}
