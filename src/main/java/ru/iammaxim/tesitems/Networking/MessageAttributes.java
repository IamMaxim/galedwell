package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.util.HashMap;

/**
 * Created by Maxim on 11.06.2016.
 */
public class MessageAttributes implements IMessage {
    private HashMap<String, Float> attributes;

    public MessageAttributes() {
        attributes = new HashMap<>();
    }

    public MessageAttributes(HashMap<String, Float> attrs) {
        attributes = attrs;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        for (String s : TESItems.ATTRIBUTES) {
            attributes.put(s, buf.readFloat());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        for (String s : TESItems.ATTRIBUTES) {
            buf.writeFloat(getAttribute(s));
        }
    }

    public float getAttribute(String s) {
        Float value = attributes.get(s);
        if (value == null) return 0;
        return value;
    }

    /**
     * Created by Maxim on 11.06.2016.
     */
    public static class Handler implements IMessageHandler<MessageAttributes, IMessage> {
        @Override
        public IMessage onMessage(MessageAttributes message, MessageContext ctx) {
            if (ctx.side == Side.CLIENT)
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayer player = TESItems.getClientPlayer();
                IPlayerAttributesCapability cap = player.getCapability(TESItems.playerAttributesCapability, null);

                for (String s : TESItems.ATTRIBUTES) {
                    cap.setAttribute(s, message.getAttribute(s));
                }
            });
            return null;
        }
    }
}
