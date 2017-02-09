package ru.iammaxim.tesitems.Questing;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maxim on 20.07.2016.
 */
public class Quest {
    private static int idGen = -1;
    public int id;
    public List<QuestStage> stages = new ArrayList<>();
    public String name;

    public Quest() {
        id = -1;
        name = "";
    }

    public Quest(String name) {
        id = genID();
        this.name = name;
    }

    public static int genID() {
        return ++idGen;
    }

    @Override
    public String toString() {
        return "name: " + name + ", stages: [" + stages.stream().map(QuestStage::toString).collect(Collectors.joining(", ")) + "]";
    }

    public Quest copy() {
        Quest dest = new Quest();
        dest.id = id;
        dest.name = name;
        stages.forEach(stage -> {
            dest.stages.add(stage.copy());
        });
        return dest;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound questTag = new NBTTagCompound();
        questTag.setInteger("id", id);
        questTag.setString("name", name);
        NBTTagList stagesTag = new NBTTagList();
        for (QuestStage stage : stages) {
            stagesTag.appendTag(stage.saveToNBT());
        }
        questTag.setTag("stages", stagesTag);
        return questTag;
    }

    public static Quest readFromNBT(NBTTagCompound tag) {
        Quest quest = new Quest();
        quest.id = tag.getInteger("id");
        if (quest.id > idGen)
            idGen = quest.id;
        quest.name = tag.getString("name");
        NBTTagList stagesListNbt = (NBTTagList) tag.getTag("stages");
        for (int j = 0; j < stagesListNbt.tagCount(); j++) {
            NBTTagCompound questStageNbt = stagesListNbt.getCompoundTagAt(j);
            QuestStage stage = new QuestStage();
            stage.journalLine = questStageNbt.getString("journalLine");
            NBTTagList targetsNbt = (NBTTagList) questStageNbt.getTag("targets");
            for (int k = 0; k < targetsNbt.tagCount(); k++) {
                stage.targets.add(QuestTarget.getTargetFromNBT(targetsNbt.getCompoundTagAt(k)));
            }
            quest.stages.add(stage);
        }
        return quest;
    }
}