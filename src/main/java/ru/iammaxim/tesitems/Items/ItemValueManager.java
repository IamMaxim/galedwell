package ru.iammaxim.tesitems.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * Created by maxim on 11/5/16 at 1:31 PM.
 */
public class ItemValueManager {
    private static HashMap<Item, Integer> values = new HashMap<>();

    public static void init() {

    }

    public static int getValue(ItemStack is) {
        Integer v;
        if ((v = values.get(is.getItem())) != null)
            return v * is.stackSize;
        else return 0;
    }
}
