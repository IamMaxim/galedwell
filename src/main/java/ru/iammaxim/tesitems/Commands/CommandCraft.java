package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import ru.iammaxim.tesitems.Craft.CraftRecipes;

/**
 * Created by Maxim on 17.06.2016.
 */
public class CommandCraft extends CommandBase {
    @Override
    public String getCommandName() {
        return "craft";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String name = args[0];
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack is = CraftRecipes.craft(name, player);
            if (is != null) {
                boolean flag = player.inventory.addItemStackToInventory(is);
                if (flag) {
                    EntityItem entityitem1 = player.dropItem(is, false);
                    player.worldObj.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    player.inventoryContainer.detectAndSendChanges();
                }
            }
        }
    }
}
