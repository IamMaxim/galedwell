package ru.iammaxim.tesitems.Questing;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.VariableStorage;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestInstance extends Value {
    public Quest quest;
    public int stage, quest_id;
    public VariableStorage variableStorage;

    public QuestInstance() {
    }

    @Override
    public String valueToString() {
        return "questInstance: name=" + quest.name + ", stage=" + stage + ", variableStorage=" + variableStorage;
    }

    @Override
    public Value operatorPlus(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorSubtract(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorMultiply(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorDivide(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorLess(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorLessEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorMoreEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorMore(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    public QuestInstance(Quest quest, int stage) {
        this.quest = quest;
        this.stage = stage;
        this.quest_id = quest.id;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("index", quest.id);
        tag.setInteger("stage", stage);
        tag.setTag("vars", variableStorage.writeToNBT());
        return tag;
    }

    public void loadFromNBT(NBTTagCompound tag) {
        quest_id = tag.getInteger("index");
        stage = tag.getInteger("stage");
        quest = QuestManager.getByID(quest_id);
        variableStorage = (VariableStorage) Value.loadValueFromNBT(tag.getCompoundTag("vars"));
    }

    @Override
    public String toString() {
        return " quest: " + quest +
                ", stage: " + stage +
                ", variables: " + variableStorage.toString();

    }

    public QuestStage getCurrentStage() {
        return quest.stages.get(stage);
    }
}
