package ru.iammaxim.tesitems.Networking;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/27/16 at 12:19 PM.
 */
public class InventoryMessageHandler implements IMessageHandler<InventoryMessage, IMessage> {
    @Override
    public IMessage onMessage(InventoryMessage message, MessageContext ctx) {
        TESItems.getMinecraft().addScheduledTask(() -> {
            Inventory inv = TESItems.getCapability(TESItems.getClientPlayer()).getInventory();
            inv.loadFromNBT(message.tag);
            inv.calculateCarryweight();
        });
        return null;
    }
}
