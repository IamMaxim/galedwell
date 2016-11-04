package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import ru.iammaxim.tesitems.Inventory.Inventory;

/**
 * Created by maxim on 11/4/16 at 1:13 PM.
 */
public class CommandManageInventory extends CommandBase {
    @Override
    public String getCommandName() {
        return "maninv";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args[0].equals("clear")) {
            Inventory.getInventory((EntityPlayer) sender).clear();
            ((EntityPlayer) sender).addChatComponentMessage(new TextComponentString("Your inventory was cleared"));
        } else if (args[0].equals("show")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Your inventory:\n");
            Inventory.getInventory((EntityPlayer) sender).getItemList().forEach(i -> sb.append(i.getDisplayName()).append(" ").append(i.stackSize).append('\n'));
            ((EntityPlayer) sender).addChatComponentMessage(new TextComponentString(sb.toString()));
        }
    }
}
