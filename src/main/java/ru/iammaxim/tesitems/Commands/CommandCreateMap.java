package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import ru.iammaxim.tesitems.PNG.ImageHelper;

/**
 * Created by maxim on 2/21/17 at 2:34 PM.
 */
public class CommandCreateMap extends CommandBase {
    @Override
    public String getCommandName() {
        return "createMap";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        new Thread(() -> {
            ImageHelper.createWorldMapImage(sender.getEntityWorld(), (Entity) sender, 5);
        }).start();
    }
}
