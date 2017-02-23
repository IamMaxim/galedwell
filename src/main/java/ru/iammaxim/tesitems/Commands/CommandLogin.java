package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 23.02.2017.
 */
public class CommandLogin extends CommandBase {
    @Override
    public String getCommandName() {
        return "login";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Usage: /login <password>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        IPlayerAttributesCapability cap = TESItems.getCapability((EntityPlayer) sender);
        if (cap.isAuthorized()) {
            ((EntityPlayer) sender).addChatComponentMessage(new TextComponentString(TextFormatting.RED + "You already logged in"));
            return;
        }

        if (args.length == 1 && args[0].equals(cap.getPassword())) {
            cap.authorize((EntityPlayerMP) sender);
            ((EntityPlayerMP) sender).addChatComponentMessage(new TextComponentString(TextFormatting.GREEN + "Logged in successfully"));
        } else {
            ((EntityPlayer) sender).addChatComponentMessage(new TextComponentString(TextFormatting.RED + getCommandUsage(sender)));
        }
    }
}
