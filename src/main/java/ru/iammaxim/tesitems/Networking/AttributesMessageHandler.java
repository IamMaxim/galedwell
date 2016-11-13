package ru.iammaxim.tesitems.Networking;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 11.06.2016.
 */
public class AttributesMessageHandler implements IMessageHandler<AttributesMessage, IMessage> {
    @Override
    public IMessage onMessage(AttributesMessage message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT)
        Minecraft.getMinecraft().addScheduledTask(() -> {
            EntityPlayer player = TESItems.getClientPlayer();
            IPlayerAttributesCapability cap = player.getCapability(TESItems.attributesCapability, null);

            for (String s : TESItems.ATTRIBUTES) {
                cap.setAttribute(s, message.getAttribute(s));
            }
        });
        return null;
    }
}
