package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ru.iammaxim.tesitems.Networking.MessageFactionList;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 01.01.2017.
 */
public class CommandManageFactions extends CommandBase {
    @Override
    public String getCommandName() {
        return "openFactionsList";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Used to edit factions";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender;
        TESItems.networkWrapper.sendTo(new MessageFactionList(), (EntityPlayerMP) player);
        player.openGui(TESItems.instance, TESItems.guiFactionList, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    }
}
