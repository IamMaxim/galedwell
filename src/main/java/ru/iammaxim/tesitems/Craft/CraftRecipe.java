package ru.iammaxim.tesitems.Craft;

import net.minecraft.item.ItemStack;

/**
 * Created by Maxim on 17.06.2016.
 */
public class CraftRecipe {
    public String name;
    public ItemStack[] input;
    public ItemStack output;

    public CraftRecipe(String name, ItemStack[] input, ItemStack output) {
        this.input = input;
        this.output = output;
        this.name = name;
    }

    public ItemStack getCraftedItem() {
        return output;
    }
}
