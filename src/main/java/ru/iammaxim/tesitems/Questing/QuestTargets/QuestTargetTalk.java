package ru.iammaxim.tesitems.Questing.QuestTargets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Questing.QuestTarget;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestTargetTalk extends QuestTarget {
    public QuestTargetTalk(NBTTagCompound tag) {

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public boolean check(EntityPlayer player) {
        return false;
    }

    @Override
    public NBTTagCompound getNBT() {
        return null;
    }

    @Override
    public void loadFromNBT(NBTTagCompound tag) {

    }
}
