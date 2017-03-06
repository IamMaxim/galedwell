package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.GUI.GuiContainer;
import ru.iammaxim.tesitems.GUI.Screen;
import ru.iammaxim.tesitems.GUI.ScreenStack;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 3/4/17 at 2:22 PM.
 */
public class MessageUpdateLatestContainer implements IMessage {
    public static final int CLICK_CONTAINER = 0, //used when player wants to transfer item from container to inventory
            CLICK_INVENTORY = 1; //used when player wants to transfer item from inventory to container
    public int action;
    public int slotIndex;

    public MessageUpdateLatestContainer() {}

    public MessageUpdateLatestContainer(int action, int slotIndex) {
        this.action = action;
        this.slotIndex = slotIndex;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        action = buf.readInt();
        slotIndex = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(action);
        buf.writeInt(slotIndex);
    }

    public static class ServerHandler implements IMessageHandler<MessageUpdateLatestContainer, IMessage> {

        @Override
        public IMessage onMessage(MessageUpdateLatestContainer message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            IPlayerAttributesCapability cap = TESItems.getCapability(player);

            cap.getLatestContainer().setLatestPlayer(player);
            switch (message.action) {
                case CLICK_CONTAINER:
                    if (message.slotIndex >= cap.getLatestContainer().size()) {
                        return new MessageShowNotification("Error occurred while transferring item");
                    }
                    cap.getInventory().addItemWithoutNotify(cap.getLatestContainer().get(message.slotIndex));
                    cap.getLatestContainer().removeItem(message.slotIndex);
                    return new MessageUpdateLatestContainer(message.action, message.slotIndex);
                case CLICK_INVENTORY:
                    if (message.slotIndex >= cap.getInventory().size()) {
                        return new MessageShowNotification("Error occurred while transferring item");
                    }
                    if (cap.getInventory().isItemEquipped(message.slotIndex))
                        cap.getInventory().unequip(message.slotIndex);
                    cap.getLatestContainer().addItem(cap.getInventory().get(message.slotIndex));
                    cap.getInventory().removeItem(message.slotIndex);
                    return new MessageUpdateLatestContainer(message.action, message.slotIndex);
            }

            return new MessageShowNotification("Error occurred while transferring item");
        }
    }

    public static class ClientHandler implements IMessageHandler<MessageUpdateLatestContainer, IMessage> {

        @Override
        public IMessage onMessage(MessageUpdateLatestContainer message, MessageContext ctx) {
            Screen lastScreen = ScreenStack.lastScreen();
            if (lastScreen instanceof GuiContainer) {
                ((GuiContainer) lastScreen).updateTable();
                ((GuiContainer) lastScreen).update();
            }

            return null;
        }
    }
}
