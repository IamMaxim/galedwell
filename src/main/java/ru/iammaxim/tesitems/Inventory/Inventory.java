package ru.iammaxim.tesitems.Inventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.Items.EntityItemNew;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 20.07.2016.
 */
public class Inventory {
    private List<ItemStack> inventory = new ArrayList<>();
    public ItemStack[] armorInventory = new ItemStack[4];

    public ItemStack getMainhandItem() {
        return null;
    }
    public ItemStack getOffhandItem() {
        return null;
    }

    public void setMainHandItem() {
    }

    public void setOffHandItem() {
    }

    public List<ItemStack> getItemList() {
        return inventory;
    }

    public void setItemList(List<ItemStack> list) {
        inventory = list;
    }

    public int size() {
        return inventory.size();
    }

    public ItemStack get(int index) {
        return inventory.get(index);
    }

    public void clear() {
        inventory.clear();
    }

    public void addItem(ItemStack stack) {
        System.out.println("add item()");
        if (stack.isItemStackDamageable())
            inventory.add(stack);
        else {
            int index = getItemStackIndex(stack);
            if (index == -1)
                inventory.add(stack);
            else {
                ItemStack is = inventory.get(index);
                is.stackSize += stack.stackSize;
                while (is.stackSize > 64) {
                    is.stackSize -= 64;
                    inventory.add(new ItemStack(stack.getItem(), 64));
                }
            }
        }
    }

    public void setItem(int index, ItemStack stack) {
        inventory.set(index, stack);
    }

    public void checkInventory() {
        for (int i = inventory.size(); i >= 0; i--) {
            checkSlot(i);
        }
    }

    public void checkSlot(int index) {
        System.out.println("checking slot " + index);
        ItemStack is = inventory.get(index);
        if (is.stackSize <= 0) inventory.remove(index);
    }

    public boolean removeItem(Item item) {
        int index = getItemIndex(item);
        if (index == -1) return false;
        return removeItem(index);
    }

    public boolean removeItem(int index) {
        inventory.remove(index);
        return true;
    }

    public int getItemIndex(Item item) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack is = inventory.get(i);
            if (is.getItem() == item) return i;
        }
        return -1;
    }

    public int getItemStackIndex(ItemStack stack) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack is = inventory.get(i);
            if (is.getItem() == stack.getItem() && stack.getMetadata() == is.getMetadata()) return i;
        }
        return -1;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList tagList = new NBTTagList();
        for (ItemStack is : inventory) {
            NBTTagCompound itemTag = is.serializeNBT();
            tagList.appendTag(itemTag);
        }
        tag.setTag("items", tagList);
        return tag;
    }

    public void loadFromNBT(NBTTagCompound tag) {
        NBTTagList tagList = (NBTTagList) tag.getTag("items");
        for (int i = 0; i < tagList.tagCount(); i++) {
            ItemStack stack = ItemStack.loadItemStackFromNBT((NBTTagCompound) tagList.get(i));
            inventory.add(stack);
        }
    }

    public List<int[]> getItemIndices(Item item) {
        List<int[]> slots = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack is = inventory.get(i);
            if (is.getItem() == item) {
                slots.add(new int[]{i, is.stackSize});
            }
        }
        return slots;
    }

    public ItemStack takeItem(Item item, int count) {
        if (item.isDamageable()) {
            List<int[]> slots = getItemIndices(item);
            final int[] total = {0};
            slots.forEach((slot) -> total[0]+=slot[1]);
            if (total[0] < count) return null;
            for (int i = 0; i < slots.size(); i++) {
                int[] slot = slots.get(i);
                ItemStack is = inventory.get(slot[0]);
                total[0] -= slot[1];
                if (total[0] <= 0) {
                    is.stackSize -= total[0] + slot[1];
                    return new ItemStack(item, count);
                } else {
                    inventory.remove(slot[0]);
                }
            }
            return null;
        } else {
            int itemIndex = getItemIndex(item);
            if (itemIndex == -1) return null;
            inventory.remove(itemIndex);
            return inventory.get(itemIndex);
        }
    }

    public static Inventory getInventory(EntityPlayer player) {
        return TESItems.getCapatibility(player).getInventory();
    }

    public void equip(EntityEquipmentSlot slot, int index) {
        System.out.println("equip");
    }

    public void drop(Entity entity, int index, int count) {
        if (index < 0 || index > size() - 1) {
            return;
        }
        ItemStack is = inventory.get(index);
        System.out.println("index: " + index + " count: " + count + " stackSize: " + is.stackSize);
        if (count > is.stackSize) {
            System.out.println("count > is.stackSize. Something goes wrong");
            return;
        }
        ItemStack stack = is.copy();
        stack.stackSize = count;
        is.stackSize -= count;
        checkSlot(index);
        EntityItemNew e = new EntityItemNew(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);
        e.setDefaultPickupDelay();
        entity.worldObj.spawnEntityInWorld(e);
    }
}