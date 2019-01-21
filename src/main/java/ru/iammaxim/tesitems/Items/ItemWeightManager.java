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

    /**
     * Returns weight of ItemStack based on initialized item weights.
     * If weight for given item is not initialized
     *
     * @param is ItemStack to get weight for
     * @return weight of ItemStack
     */
    public static float getWeight(ItemStack is) {
        Float weight;
        if (is == null) return 1;
        if ((weight = weights.get(is.getItem())) != null)
            return weight * is.stackSize;
        else return 1 * is.stackSize;
    }
}
