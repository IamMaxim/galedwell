package ru.iammaxim.tesitems.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * Created by maxim on 7/28/16 at 10:28 PM.
 */
public class InventoryClient extends Inventory {
    private EntityPlayer player;

    public InventoryClient(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void setMainHandItem() {
        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, getMainhandItem());
    }

    @Override
    public void setOffHandItem() {
        player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, getOffhandItem());
    }
}
