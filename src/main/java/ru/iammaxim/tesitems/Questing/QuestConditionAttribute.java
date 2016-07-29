package ru.iammaxim.tesitems.Questing;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestConditionAttribute implements IQuestCondition {
    private float value;
    private String attributeName;
    private Condition condition;

    public QuestConditionAttribute(String attributeName, Condition condition, float value) {
        this.attributeName = attributeName;
        this.condition = condition;
        this.value = value;
    }

    @Override
    public boolean isTrue(EntityPlayer player) {
        if (condition == Condition.LESS) {
            if (TESItems.getCapatibility(player).getAttribute(attributeName) < value) return true;
        } else if (condition == Condition.LESSEQUAL) {
            if (TESItems.getCapatibility(player).getAttribute(attributeName) <= value) return true;
        } else if (condition == Condition.EQUAL) {
            if (TESItems.getCapatibility(player).getAttribute(attributeName) == value) return true;
        } else if (condition == Condition.GREATEREQUAL) {
            if (TESItems.getCapatibility(player).getAttribute(attributeName) >= value) return true;
        } else if (condition == Condition.GREATER) {
            if (TESItems.getCapatibility(player).getAttribute(attributeName) > value) return true;
        }
        return false;
    }

    public enum Condition {
        LESS, LESSEQUAL, EQUAL, GREATEREQUAL, GREATER
    }
}
