package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Dialogs.Dialog;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.GUI.GuiNPCDialog;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 8/5/16 at 9:18 PM.
 */
public class MessageDialog implements IMessage {
    public NBTTagCompound tag;

    public MessageDialog() {}

    public MessageDialog(NBTTagCompound tag) {
        this.tag = tag;
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
     * Created by maxim on 8/5/16 at 9:18 PM.
     */
    public static class Handler implements IMessageHandler<MessageDialog, IMessage> {
        @Override
        public IMessage onMessage(MessageDialog message, MessageContext ctx) {
            Dialog dialog = Dialog.loadFromNBT(message.tag);
            IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());
            if (TESItems.getMinecraft().currentScreen instanceof GuiNPCDialog) {
                GuiNPCDialog gui = (GuiNPCDialog) TESItems.getMinecraft().currentScreen;
                gui.topics.clear();
                dialog.topics.forEach((name, topic) -> gui.topics.add(new Text(name)));
                gui.setUpdated();
            }
            cap.setLatestDialog(dialog);
            return null;
        }
    }
}