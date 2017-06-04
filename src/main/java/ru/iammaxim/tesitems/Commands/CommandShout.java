package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 04.06.2017.
 */
public class CommandShout extends CommandBase {
    @Override
    public String getCommandName() {
        return "s";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/s <message>";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender;
        ITextComponent msg = new TextComponentString(TextFormatting.GREEN + player.getName() + TextFormatting.GRAY + " shouts: " + TextFormatting.GOLD + String.join(" ", args));
        server.getPlayerList().getPlayerList().forEach(p -> {
            if (player.getDistanceSqToEntity(p) <= TESItems.shoutDistance * TESItems.shoutDistance)
                p.addChatComponentMessage(msg);
        });
    }
}
