package ru.iammaxim.tesitems.Commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
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
            IPlayerAttributesCapability cap = ((EntityPlayer) sender).getCapability(TESItems.attributesCapability, null);
            StringBuilder sb = new StringBuilder();
            for (String attr : TESItems.ATTRIBUTES) {
                sb.append(ChatFormatting.BLUE).append(attr).append(": ").append(ChatFormatting.AQUA).append(cap.getAttribute(attr)).append("\n");
            }
            sender.addChatMessage(new TextComponentString(sb.toString()));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
