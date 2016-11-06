package ru.iammaxim.tesitems.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * Created by maxim on 11/4/16 at 6:55 PM.
 */
public class ItemWeightManager {
    private static HashMap<Item, Float> weights = new HashMap<>();

    public static void init() {

    }

    public static float getWeight(ItemStack is) {
        Float w;
        if ((w = weights.get(is.getItem())) != null)
            return w * is.stackSize;
        else return 1 * is.stackSize;
    }

    public static String getWeightString(ItemStack is) {
        float w = getWeight(is);
        if (w == (int)w)
            return String.valueOf((int)w);
        else return String.valueOf(w);
    }
}
