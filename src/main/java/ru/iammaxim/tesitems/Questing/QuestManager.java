package ru.iammaxim.tesitems.Questing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestManager {
    private static final String FILEDIRECTORY = "questSystem";
    private static final String FILEPATH = FILEDIRECTORY + "/quests.nbt";
    public static HashMap<Integer, Quest> questList = new HashMap<>();
    private static int idGen = Integer.MIN_VALUE;

    public static Quest getByID(int ID) {
        return questList.get(ID);
    }

    public static int genID() {
        return ++idGen;
    }

    public static void saveToNBT(NBTTagCompound tagCompound) {
        NBTTagList quests = new NBTTagList();
        for (Quest quest : questList.values()) {
            NBTTagCompound questTag = new NBTTagCompound();
            questTag.setInteger("id", quest.id);
            questTag.setString("name", quest.name);
            NBTTagList stagesListNbt = new NBTTagList();
            for (int j = 0; j < quest.stages.size(); j++) {
                NBTTagCompound questStageNbt = new NBTTagCompound();
                QuestStage stage = quest.stages.get(j);
                questStageNbt.setString("questLine", stage.questLine);
                questStageNbt.setString("dialogPhrase", stage.dialogPhrase);
                NBTTagList targetsNbt = new NBTTagList();
                for (int k = 0; k < stage.targets.size(); k++) {
                    QuestTarget target = stage.targets.get(k);
                    NBTTagCompound tag = target.getNBT();
                    tag.setString("type", target.getType());
                    targetsNbt.appendTag(tag);
                }
                questStageNbt.setTag("targets", targetsNbt);
                stagesListNbt.appendTag(questStageNbt);
            }
            questTag.setTag("stages", stagesListNbt);
            NBTTagList itemsReward = new NBTTagList();
            for (int j = 0; j < quest.itemsReward.size(); j++) {
                ItemStack is = quest.itemsReward.get(j);
                itemsReward.appendTag(is.serializeNBT());
            }
            questTag.setTag("itemsReward", itemsReward);
            questTag.setInteger("goldReward", quest.goldReward);
            quests.appendTag(questTag);
        }
        tagCompound.setTag("quests", quests);
        System.out.println("Saved quests tag: " + tagCompound.toString());
    }

    public static void loadFromNBT(NBTTagCompound tag) {
        try {
            System.out.println("Loading quest tag: " + tag.toString());
            NBTTagList list = (NBTTagList) tag.getTag("quests");
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound questTag = list.getCompoundTagAt(i);
                Quest q = getQuestFromNBT(questTag);
                questList.put(q.id, q);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();

        }
    }

    public static Quest getQuestFromNBT(NBTTagCompound questTag) {
        Quest quest = new Quest();
        quest.id = questTag.getInteger("id");
        if (quest.id > idGen)
            idGen = quest.id;
        quest.name = questTag.getString("name");
        NBTTagList stagesListNbt = (NBTTagList) questTag.getTag("stages");
        for (int j = 0; j < stagesListNbt.tagCount(); j++) {
            NBTTagCompound questStageNbt = stagesListNbt.getCompoundTagAt(j);
            QuestStage stage = new QuestStage();
            stage.questLine = questStageNbt.getString("questLine");
            stage.dialogPhrase = questStageNbt.getString("dialogPhrase");
            NBTTagList targetsNbt = (NBTTagList) questStageNbt.getTag("targets");
            for (int k = 0; k < targetsNbt.tagCount(); k++) {
                stage.targets.add(QuestTarget.getTargetFromNBT(targetsNbt.getCompoundTagAt(k)));
            }
            quest.stages.add(stage);
        }
        NBTTagList itemsReward = (NBTTagList) questTag.getTag("itemsReward");
        for (int j = 0; j < itemsReward.tagCount(); j++) {
            ItemStack is = ItemStack.loadItemStackFromNBT(itemsReward.getCompoundTagAt(j));
            quest.itemsReward.add(is);
        }
        quest.goldReward = questTag.getInteger("goldReward");
        return quest;
    }

    public static void loadFromFile() {
        try {
            File f = new File(FILEPATH);
            if (!f.exists()) return;
            Scanner scanner = new Scanner(f).useDelimiter("\\A");
            String s = scanner.next();
            NBTTagCompound tag = JsonToNBT.getTagFromJson(s);
            loadFromNBT(tag);
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveToFile() throws IOException {
        File f = new File(FILEDIRECTORY);
        f.mkdirs();
        File f2 = new File(FILEPATH);
        FileOutputStream fos = new FileOutputStream(f2);
        NBTTagCompound tag = new NBTTagCompound();
        saveToNBT(tag);
        fos.write(tag.toString().getBytes());
        fos.close();
    }

    public static void startQuest(EntityPlayer player, Quest quest) {
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        QuestInstance inst = new QuestInstance(quest, QuestStatus.INPROGRESS, 0);
        cap.addQuest(inst);
    }
}
