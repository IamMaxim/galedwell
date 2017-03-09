package ru.iammaxim.tesitems.Questing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;
import ru.iammaxim.tesitems.Utils.IDGen;
import ru.iammaxim.tesitems.Utils.NBTFileStorage;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestManager {
    public static HashMap<Integer, Quest> questList = new HashMap<>();
    public static IDGen idGen = new IDGen();
    public static NBTFileStorage nbtFileStorage = new NBTFileStorage("questSystem", "questSystem/quests.nbt");
    private static boolean dirty = false;

    public static Quest getByID(int ID) {
        return questList.get(ID);
    }

    public static NBTTagList writeToNBT() {
        NBTTagList quests = new NBTTagList();
        for (Quest quest : questList.values()) {
            quests.appendTag(quest.writeToNBT());
        }
        return quests;
    }

    public static void readFromNBT(NBTTagList tag) {
        try {
            for (int i = 0; i < tag.tagCount(); i++) {
                NBTTagCompound questTag = tag.getCompoundTagAt(i);
                Quest q = Quest.readFromNBT(questTag);
                idGen.update(q.id);
                questList.put(q.id, q);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();

        }
    }

    public static void loadFromFile() {
        try {
            NBTTagList list = (NBTTagList) nbtFileStorage.loadFromFile().getTag("questList");
            if (list != null)
                readFromNBT(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void saveToFile() {
        if (!dirty)
            return;

        try {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("questList", writeToNBT());
            nbtFileStorage.saveToFile(tag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startQuest(EntityPlayer player, Quest quest) {
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        QuestInstance inst = new QuestInstance(quest, 0);
        cap.addQuest(inst);
        cap.setQuestStage(quest.id, 0);
    }

    public void addQuest(Quest quest) {
        dirty = true;
        questList.put(quest.id, quest);
    }

    public Quest getQuest(int id) {
        return questList.get(id);
    }
}
