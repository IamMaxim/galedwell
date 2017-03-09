package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;
import ru.iammaxim.tesitems.Utils.Utils;

import java.util.List;

/**
 * Created by maxim on 09.03.2017.
 */
public class CommandLuck extends CommandBase {
    @Override
    public String getCommandName() {
        return "luck";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        List<EntityPlayerMP> players = server.getPlayerList().getPlayerList();
        EntityPlayerMP player = (EntityPlayerMP) sender;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        float attr = cap.getAttribute("luck");
        int value = (int) Math.round((attr + Math.random() * (200 - attr)) / 10);
        String message = TextFormatting.LIGHT_PURPLE + "*" + player.getName() + " " + String.join(" ", args) + ": " + TextFormatting.YELLOW + value + " (luck)" + TextFormatting.LIGHT_PURPLE + "*";
        Utils.sendChatMessage(players, player, message, TESItems.actionDistance);
    }
}
