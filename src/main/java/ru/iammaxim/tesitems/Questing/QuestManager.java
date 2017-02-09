package ru.iammaxim.tesitems.Questing;

import net.minecraft.entity.player.EntityPlayer;
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
//    private static final String FILEDIRECTORY = "questSystem";
//    private static final String FILEPATH = FILEDIRECTORY + "/quests.nbt";
    public static HashMap<Integer, Quest> questList = new HashMap<>();

    public static Quest getByID(int ID) {
        return questList.get(ID);
    }

    public static NBTTagList writeToNBT() {
        NBTTagList quests = new NBTTagList();
        for (Quest quest : questList.values()) {
            quests.appendTag(quest.writeToNBT());
        }

        //todo: remove out
        System.out.println("Saved quests tag: " + quests.toString());
        return quests;
    }

    public static void readFromNBT(NBTTagList tag) {
        try {
            //todo: remove out
            System.out.println("Loading quest tag: " + tag.toString());
            for (int i = 0; i < tag.tagCount(); i++) {
                NBTTagCompound questTag = tag.getCompoundTagAt(i);
                Quest q = Quest.readFromNBT(questTag);
                questList.put(q.id, q);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();

        }
    }

/*    public static void loadFromFile() {
        try {
            File f = new File(FILEPATH);
            if (!f.exists()) return;
            Scanner scanner = new Scanner(f).useDelimiter("\\A");
            String s = scanner.next();
            NBTTagCompound tag = JsonToNBT.getTagFromJson(s);
            readFromNBT(tag);
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

/*    public static void saveToFile() throws IOException {
        File f = new File(FILEDIRECTORY);
        f.mkdirs();
        File f2 = new File(FILEPATH);
        FileOutputStream fos = new FileOutputStream(f2);
        fos.write(writeToNBT().toString().getBytes());
        fos.close();
    }*/

    public static void startQuest(EntityPlayer player, Quest quest) {
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        QuestInstance inst = new QuestInstance(quest, 0);
        cap.addQuest(inst);
        cap.setQuestStage(quest.id, 0);
    }
}
