package ru.iammaxim.tesitems.Inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
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
    public void addItem(ItemStack stack) {
        super.addItem(stack);
        player.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2f, player.getRNG().nextFloat() * 3f);
    }

    @Override
    public void drop(Entity entity, int index, int count) {
        TESItems.networkWrapper.sendToServer(new ItemDropMessage(index, count));
    }

    @Override
    public void equip(EntityEquipmentSlot slot, int index) {
        TESItems.networkWrapper.sendToServer(new EquipMessage(slot.getName(), index));
    }
}
