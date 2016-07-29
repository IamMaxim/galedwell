package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by maxim on 7/29/16 at 12:03 PM.
 */
public class EquipMessage implements IMessage {
    public int index;
    public String slot;

    public EquipMessage() {}

    //pass -1 to unequip
    public EquipMessage(String slot, int index) {
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
}
