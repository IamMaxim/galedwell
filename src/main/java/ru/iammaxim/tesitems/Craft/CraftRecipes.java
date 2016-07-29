package ru.iammaxim.tesitems.Craft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 17.06.2016.
 */
public class CraftRecipes {
    private static List<CraftRecipe> recipes = new ArrayList<>();

    public static void addRecipe(CraftRecipe recipe) {
        recipes.add(recipe);
    }

    private static void removeItemStack(InventoryServer inventory, ItemStack toRemove) {
        int index = inventory.getItemIndex(toRemove.getItem());
        ItemStack is = inventory.get(index);
        if (toRemove.stackSize >= is.stackSize)
            inventory.removeItem(index);
        else {
            is.stackSize -= toRemove.stackSize;
            inventory.setItem(index, is);
        }
    }

    public static ItemStack craft(String name, EntityPlayer player) {
        InventoryServer inventory = (InventoryServer) Inventory.getInventory(player);
        final ItemStack[] returnStack = {null};
        recipes.forEach((recipe) -> {
            if (recipe.name.equals(name)) {
                int neededItems = 0;
                for (ItemStack input : recipe.input) {
                    for (ItemStack is : inventory.getItemList()) {
                        if (is.getItem() == input.getItem())
                            if (is.stackSize >= input.stackSize) neededItems++;
                    }
                if (neededItems >= recipe.input.length)
                    returnStack[0] = recipe.output.copy();
                    for (ItemStack itemStack : recipe.input) {
                        removeItemStack(inventory, itemStack);
                    }
                }
                inventory.addItem(recipe.output.copy());
                player.addChatMessage(new TextComponentString("Crafted " + recipe.name));
            }
        });
        return returnStack[0];
    }
}
