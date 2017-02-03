package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import ru.iammaxim.tesitems.Networking.MessageFactionList;
import ru.iammaxim.tesitems.Networking.MessageQuestList;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestManager;
import ru.iammaxim.tesitems.Questing.QuestStage;
import ru.iammaxim.tesitems.Questing.QuestTargets.QuestTargetGather;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 11/4/16 at 10:15 PM.
 */
public class CommandManageQuests extends CommandBase {
    @Override
    public String getCommandName() {
        return "openQuestsList";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Used to edit quests";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender;
        TESItems.networkWrapper.sendTo(new MessageQuestList(), (EntityPlayerMP) player);
        player.openGui(TESItems.instance, TESItems.guiQuestList, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    }
}
