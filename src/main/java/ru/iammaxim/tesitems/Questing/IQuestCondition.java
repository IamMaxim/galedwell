package ru.iammaxim.tesitems.Questing;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Maxim on 20.07.2016.
 */
public interface IQuestCondition {
    public boolean isTrue(EntityPlayer player);
}
