package ru.iammaxim.tesitems.Networking;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Inventory.Inventory;

/**
 * Created by maxim on 7/29/16 at 12:07 PM.
 */
public class EquipMessageServerHandler implements IMessageHandler<EquipMessage, IMessage> {
    @Override
    public IMessage onMessage(EquipMessage message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        if (message.index == -1) {
            player.setItemStackToSlot(EntityEquipmentSlot.fromString(message.slot), null);
        } else {
            player.setItemStackToSlot(EntityEquipmentSlot.fromString(message.slot), Inventory.getInventory(player).get(message.index));
        }
        return null;
    }
}