package ru.iammaxim.tesitems.Inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.Networking.EquipMessage;
import ru.iammaxim.tesitems.Networking.ItemDropMessage;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/28/16 at 10:28 PM.
 */
public class InventoryClient extends Inventory {
    public EntityPlayer player;

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
    public ItemStack getMainhandItem() {
        return player.getHeldItemMainhand();
    }

    @Override
    public ItemStack getOffhandItem() {
        return player.getHeldItemOffhand();
    }

    @Override
    public void drop(Entity entity, int index, int count) {
//        System.out.println("sending drop message: " + index + " " + count);
        TESItems.networkWrapper.sendToServer(new ItemDropMessage(index, count));
        checkSlot(index);
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
