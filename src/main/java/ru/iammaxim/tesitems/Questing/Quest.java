package ru.iammaxim.tesitems.Questing;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.Utils.IDGen;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Maxim on 20.07.2016.
 */
public class Quest {
    public int id = -1;
    public ArrayList<QuestStage> stages = new ArrayList<>();
    public String name;
    public IDGen idGen = new IDGen();

    public Quest() {
        id = -1;
        name = "";
    }

    public Quest(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name: " + name + ", stages: [" + stages.stream().map(QuestStage::toString).collect(Collectors.joining(", ")) + "]";
    }

    public Quest copy() {
        Quest dest = new Quest();
        dest.id = id;
        dest.name = name;
        stages.forEach(stage ->
                dest.stages.add(stage.copy()));
        return dest;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound questTag = new NBTTagCompound();
        questTag.setInteger("index", id);
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
        quest.id = tag.getInteger("index");
        quest.idGen.update(quest.id);
        quest.name = tag.getString("name");
        NBTTagList stagesListNbt = (NBTTagList) tag.getTag("stages");
        for (int j = 0; j < stagesListNbt.tagCount(); j++) {
            NBTTagCompound questStageNbt = stagesListNbt.getCompoundTagAt(j);
            QuestStage stage = QuestStage.loadFromNBT(questStageNbt);
            quest.stages.add(stage);
        }
        return quest;
    }
}