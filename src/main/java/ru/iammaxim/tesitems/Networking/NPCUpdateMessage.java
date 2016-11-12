package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by maxim on 11/9/16 at 6:56 PM.
 */
public class NPCUpdateMessage implements IMessage {
    public NBTTagCompound tag;

    public NPCUpdateMessage() {}

    public NPCUpdateMessage(NBTTagCompound tag) {
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
}
