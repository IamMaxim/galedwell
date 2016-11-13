package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import ru.iammaxim.tesitems.Networking.AttributesMessage;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Created by Maxim on 09.06.2016.
 */
public class CommandSetAttribute extends CommandBase {
    @Override
    public String getCommandName() {
        return "setAttribute";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender iCommandSender, String[] args) throws CommandException {
        if (args.length == 3) {
            EntityPlayer player = server.getPlayerList().getPlayerByUsername(args[0]);
            if (player == null) {
                iCommandSender.addChatMessage(new TextComponentString(TextFormatting.RED + "Player " + TextFormatting.BLUE + TextFormatting.BOLD + args[0] + TextFormatting.RED + TextFormatting.RESET + " not found"));
                return;
            }
            IPlayerAttributesCapability cap = player.getCapability(TESItems.attributesCapability, null);
            float f = Float.parseFloat(args[2]);
            cap.setAttribute(args[1], f);
            iCommandSender.addChatMessage(new TextComponentString("Setting " + TextFormatting.YELLOW + args[0] + TextFormatting.RESET + "'s " + TextFormatting.BLUE + args[1] + TextFormatting.RESET + " to " + f));
            TESItems.networkWrapper.sendTo(new AttributesMessage(cap.getAttributes()), (EntityPlayerMP) player);
        } else {
            iCommandSender.addChatMessage(new TextComponentString(TextFormatting.RED + "Invalid argument count"));
        }
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : (args.length == 2 ? getListOfStringsMatchingLastWord(args, TESItems.ATTRIBUTES) : Collections.emptyList());
    }
}