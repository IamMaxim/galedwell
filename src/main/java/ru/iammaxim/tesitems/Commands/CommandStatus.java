package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 1/22/17 at 4:03 PM.
 */
public class CommandStatus extends CommandBase {
    @Override
    public String getCommandName() {
        return "status";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        IPlayerAttributesCapability cap = TESItems.getCapability((EntityPlayer) sender);
        ((EntityPlayer) sender).addChatComponentMessage(new TextComponentString("Variables: " + cap.getVariableStorage().toString()));
    }
}
