package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by maxim on 11/5/16 at 4:23 PM.
 */
public class JournalAppendMessage implements IMessage {
    public String s;

    public JournalAppendMessage() {}

    public JournalAppendMessage(String s) {
        this.s = s;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        s = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, s);
    }
}
