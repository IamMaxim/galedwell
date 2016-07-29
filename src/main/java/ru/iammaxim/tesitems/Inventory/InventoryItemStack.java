package ru.iammaxim.tesitems.Inventory;

import net.minecraft.item.ItemStack;

/**
 * Created by maxim on 7/28/16 at 11:37 PM.
 */
public class InventoryItemStack implements Comparable<InventoryItemStack> {
    public ItemStack stack;

    public InventoryItemStack(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public int compareTo(InventoryItemStack o) {
        return stack.getDisplayName().compareTo(o.stack.getDisplayName());
    }
}
