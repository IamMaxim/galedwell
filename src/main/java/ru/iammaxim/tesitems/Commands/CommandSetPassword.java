package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 23.02.2017.
 */
public class CommandSetPassword extends CommandBase {
    @Override
    public String getCommandName() {
        return "setPassword";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Usage: /setPassword <password> <repeatPassword>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        IPlayerAttributesCapability cap = TESItems.getCapability((EntityPlayer) sender);
        if (cap.isAuthorized()) {
            if (args.length == 2 && args[0].equals(args[1])) {
                cap.setPassword(args[0]);
                ((EntityPlayer) sender).addChatComponentMessage(new TextComponentString("Password changed successfully"));
            } else {
                ((EntityPlayer) sender).addChatComponentMessage(new TextComponentString(TextFormatting.RED + getCommandUsage(sender)));
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
