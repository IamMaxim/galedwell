package ru.iammaxim.tesitems.Inventory;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by maxim on 2/27/17 at 4:23 PM.
 */
public class InventoryContainer extends Inventory {
    public EntityPlayer player;

    public void setLatestPlayer(EntityPlayer player) {
        this.player = player;
    }
}
