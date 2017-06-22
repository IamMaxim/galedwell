package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Craft.CraftRecipe;
import ru.iammaxim.tesitems.Craft.CraftRecipes;
import ru.iammaxim.tesitems.GUI.ScreenStack;
import ru.iammaxim.tesitems.Utils.Utils;

/**
 * Created by maxim on 08.03.2017.
 */
public class MessageRecipe implements IMessage {
    public int id;
    public CraftRecipe.Type type;
    public CraftRecipe recipe;

    public MessageRecipe() {
    }

    public MessageRecipe(CraftRecipe.Type type, int id, CraftRecipe recipe) {
        this.type = type;
        this.id = id;
        this.recipe = recipe;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
        type = CraftRecipe.Type.valueOf(ByteBufUtils.readUTF8String(buf));
        boolean delete = buf.readBoolean();
        if (delete)
            recipe = null;
        else
            recipe = CraftRecipe.loadFromNBT(ByteBufUtils.readTag(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
        ByteBufUtils.writeUTF8String(buf, type.toString());
        if (recipe == null)
            buf.writeBoolean(true); //delete recipe
        else
            buf.writeBoolean(false);
        ByteBufUtils.writeTag(buf, recipe.writeToNBT());
    }

    public static class ServerHandler implements IMessageHandler<MessageRecipe, IMessage> {

        @Override
        public IMessage onMessage(MessageRecipe message, MessageContext ctx) {
            //TODO: add permission check
            if (message.recipe == null) {
                if (!CraftRecipes.remove(message.type, message.id))
                    Utils.showNotification(ctx.getServerHandler().playerEntity, "Couldn't delete recipe");
            } else {
                CraftRecipes.addRecipe(message.type, message.id, message.recipe);
                Utils.showNotification(ctx.getServerHandler().playerEntity, "Recipe saved successfully");
            }
            return new MessageRecipe(message.type, message.id, message.recipe);
        }
    }

    public static class ClientHandler implements IMessageHandler<MessageRecipe, IMessage> {
        @Override
        public IMessage onMessage(MessageRecipe message, MessageContext ctx) {
            CraftRecipes.addRecipe(message.type, message.id, message.recipe);
            ScreenStack.processCallback("recipeUpdated");
            return null;
        }
    }
}
