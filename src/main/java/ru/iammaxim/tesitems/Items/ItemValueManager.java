package ru.iammaxim.tesitems.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * Created by maxim on 11/5/16 at 1:31 PM.
 */
public class ItemValueManager {
    private static HashMap<Item, Float> values = new HashMap<>();

    public static void init() {

    }

    public static float getValue(ItemStack is) {
        Float w;
        if ((w = values.get(is.getItem())) != null)
            return w * is.stackSize;
        else return 0;
    }
}
