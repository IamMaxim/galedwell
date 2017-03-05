package ru.iammaxim.tesitems.Craft;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Created by Maxim on 17.06.2016.
 */
public class CraftRecipe {
    public String name;
    public ItemStack[] input;
    public ItemStack[] output;

    public CraftRecipe(String name, ItemStack[] input, ItemStack[] output) {
        this.input = input;
        this.output = output;
        this.name = name;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("name", name);

        NBTTagList inputList = new NBTTagList();
        for (ItemStack is : input) {
            inputList.appendTag(is.serializeNBT());
        }
        tag.setTag("input", inputList);

        NBTTagList outputList = new NBTTagList();
        for (ItemStack is : output) {
            outputList.appendTag(is.serializeNBT());
        }
        tag.setTag("output", outputList);
        return tag;
    }

    public static CraftRecipe loadFromNBT(NBTTagCompound tag) {
        String name = tag.getString("name");

        NBTTagList inputList = (NBTTagList) tag.getTag("input");
        ItemStack[] input = new ItemStack[inputList.tagCount()];
        for (int i = 0; i < inputList.tagCount(); i++) {
            input[i] = ItemStack.loadItemStackFromNBT(inputList.getCompoundTagAt(i));
        }

        NBTTagList outputList = (NBTTagList) tag.getTag("output");
        ItemStack[] output = new ItemStack[outputList.tagCount()];
        for (int i = 0; i < outputList.tagCount(); i++) {
            output[i] = ItemStack.loadItemStackFromNBT(outputList.getCompoundTagAt(i));
        }

        return new CraftRecipe(name, input, output);
    }
}
