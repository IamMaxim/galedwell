package ru.iammaxim.tesitems.Inventory;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * Created by maxim on 3/5/17 at 8:40 AM.
 */
public class InventoryContainerClient extends InventoryContainer {
    public InventoryContainerClient() {}

    @Override
    public void drop(Entity entity, int index, int count) {
        System.out.println("Dropping not implemented for containers");
    }

    @Override
    public void equip(EntityEquipmentSlot slot, int index) {
        System.out.println("Equipping is not implemented for containers");
    }
}
