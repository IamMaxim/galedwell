package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by Maxim on 12.07.2016.
 */
public class QuestUpdateMessage implements IMessage {
    public NBTTagCompound tag;

    public QuestUpdateMessage() {}

    public QuestUpdateMessage(int index, NBTTagCompound nbttag) {
        tag = nbttag;
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
