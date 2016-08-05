package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by maxim on 8/5/16 at 9:02 AM.
 */
public class ItemDropMessage implements IMessage {
    public int index, count;

    public ItemDropMessage() {}

    public ItemDropMessage(int index, int count) {
        this.index = index;
        this.count = count;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        index = buf.readInt();
        count = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(index);
        buf.writeInt(count);
    }
}
