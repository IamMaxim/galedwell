package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.GUI.GuiInventory;
import ru.iammaxim.tesitems.GUI.Screen;
import ru.iammaxim.tesitems.GUI.ScreenStack;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/29/16 at 12:03 PM.
 */
public class MessageEquip implements IMessage {
    public int index;
    public String slot;

    public MessageEquip() {}

    //pass -1 to unequip
    public MessageEquip(String slot, int index) {
        this.slot = slot;
        this.index = index;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        slot = ByteBufUtils.readUTF8String(buf);
        index = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, slot);
        buf.writeInt(index);
    }

    /**
     * Created by maxim on 7/29/16 at 12:07 PM.
     */
    public static class ClientHandler implements IMessageHandler<MessageEquip, IMessage> {
        @Override
        public IMessage onMessage(MessageEquip message, MessageContext ctx) {
            EntityPlayer player = TESItems.getClientPlayer();
            if (message.index == -1) player.setItemStackToSlot(EntityEquipmentSlot.fromString(message.slot), null);
            else player.setItemStackToSlot(EntityEquipmentSlot.fromString(message.slot), Inventory.getInventory(player).get(message.index));

            Screen lastScreen = ScreenStack.lastScreen();
            if (lastScreen instanceof GuiInventory) {
                ((GuiInventory) lastScreen).setUpdated();
                ((GuiInventory) lastScreen).checkEquipped();
                ((GuiInventory) lastScreen).updateTable();
            }
            return null;
        }
    }

    /**
     * Created by maxim on 7/29/16 at 12:07 PM.
     */
    public static class ServerHandler implements IMessageHandler<MessageEquip, IMessage> {
        @Override
        public IMessage onMessage(MessageEquip message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            Inventory inv = Inventory.getInventory(player);
            if (message.index == -1) {
                player.setItemStackToSlot(EntityEquipmentSlot.fromString(message.slot), null);
            } else if (message.index >= inv.size()) {
                System.out.println("trying to equip item that doesn't exists");
                return null;
            } else {
                player.setItemStackToSlot(EntityEquipmentSlot.fromString(message.slot), inv.get(message.index));
            }
            return new MessageEquip(message.slot, message.index);
        }
    }
}
