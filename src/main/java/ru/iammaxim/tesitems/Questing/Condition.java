package ru.iammaxim.tesitems.Questing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Maxim on 20.07.2016.
 */
public abstract class Condition {
    public abstract boolean isTrue(EntityPlayer player);
    public abstract NBTTagCompound saveToNBT();
    public static Condition loadFromNBT(NBTTagCompound tag) {
        String type = tag.getString("type");
        switch (type) {
            case "attr":
                return new ConditionAttribute(tag);
        }
        return null;
    }
}
