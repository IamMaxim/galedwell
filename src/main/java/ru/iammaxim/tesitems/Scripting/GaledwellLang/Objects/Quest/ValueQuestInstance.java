package ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Quest;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;

/**
 * Created by maxim on 2/20/17 at 6:34 PM.
 */
public class ValueQuestInstance extends ValueObject {
    public QuestInstance questInstance;

    @Override
    public String toString() {
        if (questInstance == null)
            return "QuestInstance: null";
        return "QuestInstance: " + questInstance.quest.name;
    }

    public ValueQuestInstance(QuestInstance questInstance) {
        this.questInstance = questInstance;
    }

    @Override
    public String valueToString() {
        return toString();
    }

    @Override
    public NBTTagCompound writeToNBT() {
        return null;
    }
}
