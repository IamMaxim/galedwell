package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import org.lwjgl.input.Keyboard;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Items.Weapon;

import java.util.HashMap;

/**
 * Created by maxim on 2/27/17 at 4:38 PM.
 */
public class InventoryPlayerLayout extends InventoryLayout {
    private EntityPlayer player;

    @Override
    public boolean needBackground(int index) {
        return inv.isItemEquipped(index);
    }

    public InventoryPlayerLayout(EntityPlayer player, Inventory inventory) {
        super(inventory);
        this.player = player;

        setOnEntryLeftClick(i -> {
            if (!updated())
                return;

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                //drop item
                inv.drop(player, i, 1);
            } else {
                if (!inv.isItemEquipped(i))
                    inv.equip(i);
                else
                    inv.unequip(i);

                updateTable();
                unupdate();
            }
        });
    }

    @Override
    public String getName() {
        return "InventoryPlayerLayout";
    }
}
