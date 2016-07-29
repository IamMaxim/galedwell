package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by Maxim on 17.07.2016.
 */
public class CastSpellMessage implements IMessage {
    public int spellIndex = -1;

    public CastSpellMessage() {}

    public CastSpellMessage(int index) {
        spellIndex = index;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        spellIndex = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(spellIndex);
    }
}
