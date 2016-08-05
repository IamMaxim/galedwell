package ru.iammaxim.tesitems.Questing;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static void loadFromNBT(NBTTagCompound tag) {
        System.out.println("Loading quest tag: " + tag.toString());
        NBTTagList list = (NBTTagList) tag.getTag("quests");
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound questTag = list.getCompoundTagAt(i);
            Quest q = getQuestFromNBT(questTag);
            questList.put(q.id, q);
        }
    }

    public static Quest getQuestFromNBT(NBTTagCompound questTag) {
        Quest quest = new Quest();
        int id = questTag.getInteger("id");
        if (id > idGen)
            idGen = id;
        quest.id = id;
        quest.name = questTag.getString("name");
        NBTTagList stagesListNbt = (NBTTagList) questTag.getTag("stages");
        for (int j = 0; j < stagesListNbt.tagCount(); j++) {
            NBTTagCompound questStageNbt = stagesListNbt.getCompoundTagAt(j);
            QuestStage stage = new QuestStage();
            stage.questLine = questStageNbt.getString("questLine");
            NBTTagList targetsNbt = (NBTTagList) questStageNbt.getTag("targets");
            for (int k = 0; k < targetsNbt.tagCount(); k++) {
                stage.targets.add(QuestTarget.getTargetFromNBT(targetsNbt.getCompoundTagAt(k)));
            }
            quest.stages.add(stage);
        }
        NBTTagList itemsReward = (NBTTagList) questTag.getTag("itemsReward");
        for (int j = 0; j < itemsReward.tagCount(); j++) {
            NBTTagCompound itemTag = itemsReward.getCompoundTagAt(j);
            ItemStack is = new ItemStack(Item.getByNameOrId(itemTag.getString("name")), itemTag.getInteger("count"));
            quest.itemsReward.add(is);
        }
        quest.goldReward = questTag.getInteger("goldReward");
        return quest;
    }

    public static void loadFromFile() throws FileNotFoundException, NBTException {
        File f = new File(FILEPATH);
        if (!f.exists()) return;
        Scanner scanner = new Scanner(f).useDelimiter("\\A");
        String s = scanner.next();
        NBTTagCompound tag = JsonToNBT.getTagFromJson(s);
        loadFromNBT(tag);
        scanner.close();
    }

    public static void saveToNBT(NBTTagCompound tagCompound) {
        NBTTagCompound quests = new NBTTagCompound();
        for (Quest quest : questList.values()) {
            NBTTagCompound questTag = new NBTTagCompound();
            questTag.setInteger("id", quest.id);
            questTag.setString("name", quest.name);
            NBTTagList stagesListNbt = new NBTTagList();
            for (int j = 0; j < quest.stages.size(); j++) {
                NBTTagCompound questStageNbt = new NBTTagCompound();
                QuestStage stage = quest.stages.get(j);
                questStageNbt.setString("questLine", stage.questLine);
                NBTTagList targetsNbt = new NBTTagList();
                for (int k = 0; k < targetsNbt.tagCount(); k++) {
                    targetsNbt.set(k, stage.targets.get(k).getNBT());
                }
                questStageNbt.setTag("targets", targetsNbt);
                stagesListNbt.set(j, questStageNbt);
            }
            questTag.setTag("stages", stagesListNbt);
            NBTTagList itemsReward = new NBTTagList();
            for (int j = 0; j < itemsReward.tagCount(); j++) {
                NBTTagCompound itemTag = new NBTTagCompound();
                ItemStack is = quest.itemsReward.get(j);
                itemTag.setString("name", is.getItem().getRegistryName().toString());
                itemTag.setInteger("count", is.stackSize);
                itemsReward.set(j, itemTag);
            }
            questTag.setTag("itemsReward", itemsReward);
            questTag.setInteger("goldReward", quest.goldReward);
        }

        tagCompound.setTag("quests", quests);
        System.out.println("Saved quests tag: " + tagCompound.toString());
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
}
