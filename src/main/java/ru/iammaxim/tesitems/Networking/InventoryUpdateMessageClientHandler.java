package ru.iammaxim.tesitems.Networking;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.TESItems;

import static ru.iammaxim.tesitems.Networking.InventoryUpdateMessage.*;

/**
 * Created by maxim on 7/27/16 at 11:46 AM.
 */
public class InventoryUpdateMessageClientHandler implements IMessageHandler<InventoryUpdateMessage, IMessage> {
    @Override
    public IMessage onMessage(InventoryUpdateMessage message, MessageContext ctx) {
        EntityPlayer player = TESItems.getClientPlayer();
        Inventory inv = TESItems.getCapability(player).getInventory();
        switch(message.action) {
            case ACTION_ADD:
                inv.addItem(message.stack);
                break;
            case ACTION_SET:
                inv.setItem(message.index, message.stack);
                break;
            case ACTION_REMOVE_INDEX:
                inv.removeItem(message.index);
                break;
            case ACTION_REMOVE_ITEM:
                inv.removeItem(message.stack.getItem());
                break;
            case ACTION_CLEAR:
                inv.clear();
                break;
        }
        return null;
    }
}
