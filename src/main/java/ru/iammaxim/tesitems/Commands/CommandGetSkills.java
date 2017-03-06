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
 * Created by Maxim on 08.07.2016.
 */
public class CommandGetSkills extends CommandBase {
    @Override
    public String getCommandName() {
        return "getSkills";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            IPlayerAttributesCapability cap = ((EntityPlayer) sender).getCapability(TESItems.playerAttributesCapability, null);
            StringBuilder sb = new StringBuilder();
            for (String attr : TESItems.ATTRIBUTES) {
                sb.append(TextFormatting.BLUE).append(attr).append(": ").append(TextFormatting.AQUA).append(cap.getAttribute(attr)).append("\n");
            }
            sender.addChatMessage(new TextComponentString(sb.toString()));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
