package ru.iammaxim.tesitems.Inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import ru.iammaxim.tesitems.Networking.EquipMessage;
import ru.iammaxim.tesitems.TESItems;

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

    @Override
    public void equip(EntityEquipmentSlot slot, int index) {
        if (index == -1)
            player.setItemStackToSlot(slot, null);
        else
            player.setItemStackToSlot(slot, get(index));
        TESItems.networkWrapper.sendToServer(new EquipMessage(slot.getName(), index));
    }
}
