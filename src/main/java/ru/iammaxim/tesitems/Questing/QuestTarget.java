package ru.iammaxim.tesitems.Questing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Questing.QuestTargets.QuestTargetGather;
import ru.iammaxim.tesitems.Questing.QuestTargets.QuestTargetKill;
import ru.iammaxim.tesitems.Questing.QuestTargets.QuestTargetTalk;

/**
 * Created by Maxim on 20.07.2016.
 */
public abstract class QuestTarget {
    public abstract String getType();
    public abstract boolean check(EntityPlayer player);
    public abstract NBTTagCompound saveToNBT();
    public abstract void loadFromNBT(NBTTagCompound tag);
    public static QuestTarget getTargetFromNBT(NBTTagCompound tag) {
        String type = tag.getString("type");
        switch (type) {
            case "gather":
                return new QuestTargetGather(tag);
            case "kill":
                return new QuestTargetKill(tag);
            case "talk":
                return new QuestTargetTalk(tag);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "type: " + getType();
    }
}
