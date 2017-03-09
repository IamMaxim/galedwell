package ru.iammaxim.tesitems.Craft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryServer;
import ru.iammaxim.tesitems.Utils.IDGen;
import ru.iammaxim.tesitems.Utils.NBTFileStorage;
import ru.iammaxim.tesitems.Utils.Utils;

import java.util.HashMap;

/**
 * Created by Maxim on 17.06.2016.
 */
public class CraftRecipes {
    public static HashMap<Integer, CraftRecipe> recipes = new HashMap<>();
    private static IDGen idGen = new IDGen();
    private static NBTFileStorage nbtFileStorage = new NBTFileStorage("recipes", "workbench_recipes.nbt");
    private static boolean dirty = false;

    public static void addRecipe(CraftRecipe recipe) {
        recipes.put(idGen.genID(), recipe);
        dirty = true;
    }

    public static void addRecipe(int id, CraftRecipe recipe) {
        if (id == -1) {
            id = idGen.genID();
        }
        recipes.put(id, recipe);
        dirty = true;
    }

    private static void removeItemStack(InventoryServer inventory, ItemStack toRemove) {
        int index = inventory.getItemIndex(toRemove);
        ItemStack is = inventory.get(index);
        if (toRemove.stackSize >= is.stackSize)
            inventory.removeItem(index);
        else {
            is.stackSize -= toRemove.stackSize;
            inventory.calculateCarryweight();
        }
    }

    public static void craft(int recipeIndex, EntityPlayer player) {
        Inventory inventory = Inventory.getInventory(player);
        CraftRecipe recipe = recipes.get(recipeIndex);
        if (recipe == null) {
            Utils.showNotification(player, "No such recipe");
            return;
        }

        //check if player has enough items
        int okInput = 0;
        for (ItemStack is : recipe.input) {
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack invIS = inventory.get(i);
                if (is.getItem() == invIS.getItem() && invIS.stackSize >= is.stackSize) {
                    okInput++;
                    break;
                }
            }
        }
        if (okInput < recipe.input.length) {
            Utils.showNotification(player, "Not enough ingredients");
        }

        //remove input items
        for (ItemStack is : recipe.input) {
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack invIS = inventory.get(i);
                if (is.getItem() == invIS.getItem()) {
                    if (invIS.stackSize > is.stackSize) {
                        inventory.get(i).stackSize -= is.stackSize;
                        break;
                    } else if (invIS.stackSize == is.stackSize) {
                        inventory.removeItem(i);
                        break;
                    }
                }
            }
        }

        for (ItemStack itemStack : recipe.output) {
            inventory.addItem(itemStack.copy());
        }

        inventory.calculateCarryweight();
    }

    public static void loadFromNBT(NBTTagCompound tag) {
        recipes.clear();
        NBTTagList list = (NBTTagList) tag.getTag("recipes");
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound recipeTag = list.getCompoundTagAt(i);
            int id = recipeTag.getInteger("id");
            idGen.update(id);
            CraftRecipe recipe = CraftRecipe.loadFromNBT(recipeTag.getCompoundTag("recipe"));
            recipes.put(id, recipe);
        }
    }

    public static NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();

        NBTTagList list = new NBTTagList();
        for (Integer id : recipes.keySet()) {
            NBTTagCompound recipeTag = new NBTTagCompound();
            recipeTag.setInteger("id", id);
            recipeTag.setTag("recipe", recipes.get(id).writeToNBT());
            list.appendTag(recipeTag);
        }

        tag.setTag("recipes", list);
        return tag;
    }

    public static boolean remove(int id) {
        dirty = true;
        return recipes.remove(id) != null;
    }

    public void loadFromFile() {

    }

    public void saveToFile() {
        if (!dirty)
            return;


    }
}
