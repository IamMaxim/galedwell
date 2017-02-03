package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 29.01.17.
 */
public class CommandReloadFonts extends CommandBase {
    @Override
    public String getCommandName() {
        return "reloadFonts";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        TESItems.getMinecraft().addScheduledTask(TESItems.ClientThings::loadFonts);
    }
}
