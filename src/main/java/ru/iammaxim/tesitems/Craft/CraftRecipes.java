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

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Maxim on 17.06.2016.
 */
public class CraftRecipes {
    public static HashMap<CraftRecipe.Type, HashMap<Integer, CraftRecipe>> recipes = new HashMap<>();

    // workaround to support singleplayer
    public static HashMap<CraftRecipe.Type, HashMap<Integer, CraftRecipe>> clientRecipes = new HashMap<>();
    private static HashMap<CraftRecipe.Type, IDGen> idGens = new HashMap<>();
    private static boolean dirty = false;

    static {
        // init recipe maps
        for (CraftRecipe.Type type : CraftRecipe.Type.values()) {
            idGens.put(type, new IDGen());
            recipes.put(type, new HashMap<>());
            clientRecipes.put(type, new HashMap<>());
        }
    }

    public static void addRecipe(CraftRecipe.Type type, CraftRecipe recipe) {
        recipes.get(type).put(idGens.get(type).genID(), recipe);
        dirty = true;
    }

    public static void addRecipe(CraftRecipe.Type type, int id, CraftRecipe recipe) {
        if (id == -1) {
            id = idGens.get(type).genID();
        }
        recipe.id = id;
        recipes.get(type).put(id, recipe);
        dirty = true;
    }

    public static void addClientRecipe(CraftRecipe.Type type, int id, CraftRecipe recipe) {
        if (id == -1) {
            id = idGens.get(type).genID();
        }
        recipe.id = id;
        clientRecipes.get(type).put(id, recipe);
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

    // intended to work on server side
    public static void craft(CraftRecipe.Type type, int recipeIndex, EntityPlayer player) {
        Inventory inventory = Inventory.getInventory(player);
        CraftRecipe recipe = recipes.get(type).get(recipeIndex);
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
            return;
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
    }

    public static void loadFromNBT(NBTTagCompound tag) {
        _loadFromNBT(recipes, tag);
    }

    private static void _loadFromNBT(HashMap<CraftRecipe.Type, HashMap<Integer, CraftRecipe>> recipes, NBTTagCompound tag) {
        if (tag == null)
            return;

        recipes.forEach((t, m) -> m.clear());
        NBTTagList list = (NBTTagList) tag.getTag("types");
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag1 = list.getCompoundTagAt(i);
            CraftRecipe.Type type = CraftRecipe.Type.valueOf(tag1.getString("type"));
            NBTTagList recipes1 = (NBTTagList) tag1.getTag("recipes");
            HashMap<Integer, CraftRecipe> map = recipes.get(type);
            for (int j = 0; j < recipes1.tagCount(); j++) {
                NBTTagCompound recipeTag = recipes1.getCompoundTagAt(j);
                int id = recipeTag.getInteger("id");
                idGens.get(type).update(id);
                CraftRecipe recipe = CraftRecipe.loadFromNBT(recipeTag.getCompoundTag("recipe"));
                map.put(id, recipe);
            }
        }

    }

    public static NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();

        NBTTagList list = new NBTTagList();
        recipes.forEach((type, map) -> {
            NBTTagCompound tag1 = new NBTTagCompound();
            NBTTagList list1 = new NBTTagList();
            map.forEach((id, recipe) -> {
                NBTTagCompound recipeTag = new NBTTagCompound();
                recipeTag.setInteger("id", id);
                recipeTag.setTag("recipe", recipe.writeToNBT());
                list1.appendTag(recipeTag);
            });
            tag1.setString("type", type.toString());
            tag1.setTag("recipes", list1);
            list.appendTag(tag1);
        });
        tag.setTag("types", list);

//        System.out.println("saved recipes " + tag);

        return tag;
    }

    public static boolean remove(CraftRecipe.Type type, int id) {
        dirty = true;
        return recipes.get(type).remove(id) != null;
    }

    public static void loadFromFile() {
        try {
            NBTTagCompound list = (NBTTagCompound) NBTFileStorage.loadFromFile("crafting/recipes.nbt").getTag("recipeList");
            loadFromNBT(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToFile() {
        if (!dirty)
            return;

        try {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("recipeList", writeToNBT());
            NBTFileStorage.saveToFile("crafting/recipes.nbt", tag);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clientLoadFromNBT(NBTTagCompound tag) {
        _loadFromNBT(clientRecipes, tag);
    }
}
