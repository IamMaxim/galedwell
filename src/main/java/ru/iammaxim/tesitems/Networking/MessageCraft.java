package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Craft.CraftRecipe;
import ru.iammaxim.tesitems.Craft.CraftRecipes;

public class MessageCraft implements IMessage {
    public CraftRecipe.Type type;
    public int id;

    public MessageCraft() {}

    public MessageCraft(CraftRecipe.Type type, int id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = CraftRecipe.Type.values()[buf.readInt()];
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(type.ordinal());
        buf.writeInt(id);
    }

    public static class ServerHandler implements IMessageHandler<MessageCraft, IMessage> {

        @Override
        public IMessage onMessage(MessageCraft message, MessageContext ctx) {
            CraftRecipes.craft(message.type, message.id, ctx.getServerHandler().playerEntity);
            return null;
        }
    }
}
