package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Inventory.InventoryContainer;
import ru.iammaxim.tesitems.Inventory.InventoryContainerServer;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 6/6/17 at 6:39 PM.
 */
public class MessageOpenNPCInventory implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class ServerHandler implements IMessageHandler<MessageOpenNPCInventory, IMessage> {

        @Override
        public IMessage onMessage(MessageOpenNPCInventory message, MessageContext ctx) {
            // TODO: add permission check

            EntityPlayer player = ctx.getServerHandler().playerEntity;
            IPlayerAttributesCapability cap = TESItems.getCapability(player);
            InventoryContainer container = new InventoryContainerServer(cap.getLatestNPC().inventory);
            cap.setLatestContainer(container);
            TESItems.networkWrapper.sendTo(new MessageLatestContainer(container), (EntityPlayerMP) player);
            player.openGui(TESItems.instance, TESItems.guiContainer, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
            return null;
        }
    }
}
