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
    public abstract NBTTagCompound getNBT();
    public abstract void loadFromNBT();
    public static QuestTarget getTargetFromNBT(NBTTagCompound tag) {
        String type = tag.getString("type");
        if (type.equals("gather")) {
            return new QuestTargetGather(tag);
        } else if (type.equals("kill")) {
            return new QuestTargetKill(tag);
        } else if (type.equals("talk")) {
            return new QuestTargetTalk(tag);
        } else return null;
    }
}
