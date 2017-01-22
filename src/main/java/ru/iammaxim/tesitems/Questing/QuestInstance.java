package ru.iammaxim.tesitems.Questing;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.VariableStorage;

import java.util.HashMap;
import java.util.StringJoiner;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestInstance {
    public Quest quest;
    public int stage, quest_id;
    public VariableStorage variableStorage;

    public QuestInstance() {
    }

    public QuestInstance(Quest quest, int stage) {
        this.quest = quest;
        this.stage = stage;
        this.quest_id = quest.id;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("id", quest.id);
        tag.setInteger("stage", stage);
        tag.setTag("vars", variableStorage.writeToNBT());
        return tag;
    }

    public void loadFromNBT(NBTTagCompound tag) {
        quest_id = tag.getInteger("id");
        stage = tag.getInteger("stage");
        quest = QuestManager.getByID(quest_id);
        variableStorage.loadFromNBT(tag.getCompoundTag("vars"));
    }

    @Override
    public String toString() {
        return " quest: " + quest +
                " stage: " + stage +
                "variables: [" + variableStorage.toString() + "]";

    }

    public QuestStage getCurrentStage() {
        return quest.stages.get(stage);
    }
}
