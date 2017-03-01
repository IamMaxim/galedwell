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
    private HashMap<EntityEquipmentSlot, Integer> equippedIndices = new HashMap<>();
    private EntityPlayer player;

    @Override
    public boolean needBackground(int index) {
        return equippedIndices.values().contains(index);
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
                ItemStack is = inv.get(i);
                if (is.getItem() instanceof ItemArmor) {
                    ItemArmor armor = (ItemArmor) is.getItem();

                    //if this armor is equipped, unequip it
                    //TODO: check this
                    if (equippedIndices.get(armor.getEquipmentSlot()) != null && equippedIndices.get(armor.getEquipmentSlot()) == i) {
                        inv.equip(armor.getEquipmentSlot(), -1);
                        equippedIndices.put(armor.getEquipmentSlot(), null);
                        updateTable();
                        unupdate();
                    } else {
                        //if not equipped, equip
                        inv.equip(armor.getEquipmentSlot(), i);
                        equippedIndices.put(armor.getEquipmentSlot(), i);
                        updateTable();
                        unupdate();
                    }
                } else if (is.getItem() instanceof Weapon || is.getItem() instanceof ItemTool) {
                    if (equippedIndices.get(EntityEquipmentSlot.MAINHAND) != null && equippedIndices.get(EntityEquipmentSlot.MAINHAND) == inv.getItemStackIndex(is)) {
                        //unequip from this hand
                        inv.equip(EntityEquipmentSlot.MAINHAND, -1);
                        equippedIndices.put(EntityEquipmentSlot.MAINHAND, null);
                        updateTable();
                        unupdate();
                    } else {
                        //unequip from other hand
                        if (equippedIndices.get(EntityEquipmentSlot.OFFHAND) != null && equippedIndices.get(EntityEquipmentSlot.OFFHAND) == inv.getItemStackIndex(is)) {
                            inv.equip(EntityEquipmentSlot.OFFHAND, -1);
                        }

                        //equip in this hand
                        inv.equip(EntityEquipmentSlot.MAINHAND, i);
                        equippedIndices.put(EntityEquipmentSlot.MAINHAND, inv.getItemStackIndex(is));
                        updateTable();
                        unupdate();
                    }
                }
            }
        });

        setOnEntryRightClick(i -> {
            if (!updated())
                return;

            ItemStack is = inv.get(i);
            if (equippedIndices.get(EntityEquipmentSlot.OFFHAND) != null && equippedIndices.get(EntityEquipmentSlot.OFFHAND) == inv.getItemStackIndex(is)) {
                //unequip from this hand
                inv.equip(EntityEquipmentSlot.OFFHAND, -1);
                equippedIndices.put(EntityEquipmentSlot.OFFHAND, null);
                updateTable();
                unupdate();
            } else {
                //unequip from other hand
                if (equippedIndices.get(EntityEquipmentSlot.MAINHAND) != null && equippedIndices.get(EntityEquipmentSlot.MAINHAND) == inv.getItemStackIndex(is)) {
                    inv.equip(EntityEquipmentSlot.MAINHAND, -1);
                }

                //equip in this hand
                inv.equip(EntityEquipmentSlot.OFFHAND, i);
                equippedIndices.put(EntityEquipmentSlot.OFFHAND, inv.getItemStackIndex(is));
                updateTable();
                unupdate();
            }
        });
    }

    public void checkEquipped() {
        equippedIndices.clear();
        ItemStack[] armorInv = player.inventory.armorInventory;
        for (int i = 0; i < armorInv.length; i++) {
            ItemStack is = armorInv[i];
            if (is != null)
                equippedIndices.put(getSlotFromIndex(i), inv.getItemStackIndex(is));
        }
        if (player.getHeldItemMainhand() != null)
            equippedIndices.put(EntityEquipmentSlot.MAINHAND, inv.getItemStackIndex(player.getHeldItemMainhand()));
        if (player.getHeldItemOffhand() != null)
            equippedIndices.put(EntityEquipmentSlot.OFFHAND, inv.getItemStackIndex(player.getHeldItemOffhand()));
    }

    @Override
    public String getName() {
        return "InventoryPlayerLayout";
    }
}
