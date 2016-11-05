package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestManager;
import ru.iammaxim.tesitems.Questing.QuestStage;
import ru.iammaxim.tesitems.Questing.QuestTargets.QuestTargetGather;
import ru.iammaxim.tesitems.TESItems;

import java.io.IOException;

/**
 * Created by maxim on 11/4/16 at 10:15 PM.
 */
public class CommandManageQuests extends CommandBase {
    @Override
    public String getCommandName() {
        return "manquests";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args[0].equals("show")) {
            StringBuilder sb = new StringBuilder();
            sb.append("All quests:\n");
            QuestManager.questList.forEach((k, v) -> sb.append("id: ").append(k).append(": {").append(v.toString()).append("}").append('\n'));
            sb.append("Your instances:\n");
            IPlayerAttributesCapability cap = TESItems.getCapability((EntityPlayer) sender);
            cap.getQuests().forEach(q -> sb.append(q.toString()).append('\n'));
            ((EntityPlayer) sender).addChatComponentMessage(new TextComponentString(sb.toString()));
        } else if (args[0].equals("add")) {
            Quest quest = new Quest("testQuest");
            quest.itemsReward.add(new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE)));
            QuestStage stage1 = new QuestStage();
            stage1.questLine = "questLine1";
            stage1.dialogPhrase = "dialogsPhrase1";
            stage1.targets.add(new QuestTargetGather(new ItemStack(Item.getItemFromBlock(Blocks.DIRT))));
            quest.stages.add(stage1);
            QuestStage stage2 = new QuestStage();
            stage2.questLine = "questLine2";
            stage2.dialogPhrase = "dialogsPhrase2";
            stage2.targets.add(new QuestTargetGather(new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE))));
            quest.stages.add(stage2);
            QuestManager.questList.put(quest.id, quest);
            sender.addChatMessage(new TextComponentString("Quest added"));
        } else if (args[0].equals("remove")) {
            QuestManager.questList.remove(Integer.valueOf(args[1]));
        } else if (args[0].equals("save")) {
            try {
                QuestManager.saveToFile();
                sender.addChatMessage(new TextComponentString("Quests saved"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (args[0].equals("start")) {
            QuestManager.startQuest((EntityPlayer) sender, QuestManager.getByID(Integer.parseInt(args[1])));
        } else {

        }
    }
}
