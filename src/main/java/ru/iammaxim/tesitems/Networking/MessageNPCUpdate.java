package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Items.mItems;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 11/9/16 at 6:56 PM.
 */
public class MessageNPCUpdate implements IMessage {
    public NBTTagCompound tag;

    public MessageNPCUpdate() {}

    public MessageNPCUpdate(NBTTagCompound tag) {
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
     * Created by maxim on 11/9/16 at 6:57 PM.
     */
    public static class ClientHandler implements IMessageHandler<MessageNPCUpdate, IMessage> {
        @Override
        public IMessage onMessage(MessageNPCUpdate message, MessageContext ctx) {
            EntityPlayer player = TESItems.getClientPlayer();
            if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() != mItems.itemNPCEditorTool)
                return null;
            IPlayerAttributesCapability cap = TESItems.getCapability(player);
            cap.getLatestNPC().readFromNBT(message.tag);
            return null;
        }
    }

    /**
     * Created by maxim on 11/9/16 at 6:57 PM.
     */
    public static class ServerHandler implements IMessageHandler<MessageNPCUpdate, IMessage> {
        @Override
        public IMessage onMessage(MessageNPCUpdate message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() != mItems.itemNPCEditorTool)
                return null;
            IPlayerAttributesCapability cap = TESItems.getCapability(player);
            cap.getLatestNPC().readFromNBT(message.tag);
            return null;
        }
    }
}
