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
public class SpellbookMessage implements IMessage {
    NBTTagCompound tag;

    public SpellbookMessage() {}

    public SpellbookMessage(NBTTagCompound nbttag) {
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
    public static class Handler implements IMessageHandler<SpellbookMessage, IMessage> {
        @Override
        public IMessage onMessage(SpellbookMessage message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                IPlayerAttributesCapability cap = Minecraft.getMinecraft().thePlayer.getCapability(TESItems.attributesCapability, null);
                cap.loadSpellbook(message.tag);
            });
            return null;
        }
    }
}
