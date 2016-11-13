package ru.iammaxim.tesitems.Networking;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Items.mItems;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 11/9/16 at 6:57 PM.
 */
public class NPCUpdateMessageClientHandler implements IMessageHandler<NPCUpdateMessage, IMessage> {
    @Override
    public IMessage onMessage(NPCUpdateMessage message, MessageContext ctx) {
        EntityPlayer player = TESItems.getClientPlayer();
        if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() != mItems.itemNPCEditorTool)
            return null;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        cap.getLatestNPC().deserializeNBT(message.tag);
        return null;
    }
}
