package ru.iammaxim.tesitems.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.NPC.EntityNPC;

/**
 * Created by maxim on 7/28/16 at 10:28 PM.
 */
public class InventoryNPC extends Inventory {
    public InventoryNPC() {
    }

    @Override
    public boolean isItemEquipped(int index) {
        return super.isItemEquipped(index);
    }

    @Override
    public void setMainHandItem() {
        super.setMainHandItem();
    }

    @Override
    public void setOffHandItem() {
        super.setOffHandItem();
    }
}
