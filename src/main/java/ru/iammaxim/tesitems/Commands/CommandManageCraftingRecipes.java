package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 07.03.2017.
 */
public class CommandManageCraftingRecipes extends CommandBase {
    @Override
    public String getCommandName() {
        return "openCraftingRecipesList";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ((EntityPlayer) sender).openGui(TESItems.instance, TESItems.guiCraftingRecipesList, ((EntityPlayer) sender).worldObj, (int) ((EntityPlayer) sender).posX, (int) ((EntityPlayer) sender).posY, (int) ((EntityPlayer) sender).posZ);
    }
}
