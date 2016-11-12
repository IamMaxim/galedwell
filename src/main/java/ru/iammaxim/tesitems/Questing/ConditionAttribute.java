package ru.iammaxim.tesitems.Questing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 20.07.2016.
 */
public class ConditionAttribute extends Condition {
    private float value;
    private String attributeName;
    private Condition condition;

    public ConditionAttribute(String attributeName, Condition condition, float value) {
        this.attributeName = attributeName;
        this.condition = condition;
        this.value = value;
    }

    public ConditionAttribute(NBTTagCompound tag) {
        value = tag.getFloat("value");
        attributeName = tag.getString("attribute");
        condition = Condition.valueOf(tag.getString("condition"));
    }

    @Override
    public boolean isTrue(EntityPlayer player) {
        try {
            IPlayerAttributesCapability cap = TESItems.getCapability(player);
            if (condition == Condition.LESS) {
                if (cap.getAttribute(attributeName) < value) return true;
            } else if (condition == Condition.LESSEQUAL) {
                if (cap.getAttribute(attributeName) <= value) return true;
            } else if (condition == Condition.EQUAL) {
                if (cap.getAttribute(attributeName) == value) return true;
            } else if (condition == Condition.GREATEREQUAL) {
                if (cap.getAttribute(attributeName) >= value) return true;
            } else if (condition == Condition.GREATER) {
                if (cap.getAttribute(attributeName) > value) return true;
            }
        } catch (Exception e) {}
        return false;
    }

    @Override
    public NBTTagCompound saveToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "attr");
        tag.setFloat("value", value);
        tag.setString("condition", condition.name());
        tag.setString("attribute", attributeName);
        return tag;
    }

    public enum Condition {
        LESS, LESSEQUAL, EQUAL, GREATEREQUAL, GREATER
    }
}
