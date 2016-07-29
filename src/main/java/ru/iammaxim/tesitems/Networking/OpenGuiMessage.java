package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by Maxim on 13.07.2016.
 */
public class OpenGuiMessage implements IMessage {
    public int GuiID;

    public OpenGuiMessage() {}

    public OpenGuiMessage(int GuiID) {
        this.GuiID = GuiID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        GuiID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(GuiID);
    }
}
