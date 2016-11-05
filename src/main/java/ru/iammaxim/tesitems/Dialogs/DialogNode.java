package ru.iammaxim.tesitems.Dialogs;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

/**
 * Created by maxim on 11/5/16 at 6:51 PM.
 */
public class DialogNode {
    public int index;
    public String dialogText;
    public boolean hasEntries;
    public ArrayList<DialogEntry> entries = new ArrayList<>();

    public NBTTagCompound saveToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("index", index);
        tag.setString("dialogText", dialogText);
        NBTTagList list = new NBTTagList();
        entries.forEach(e -> list.appendTag(e.saveToNBT()));
        tag.setTag("entries", list);
        return tag;
    }

    public static DialogNode loadFromNBT(NBTTagCompound tag) {
        DialogNode node = new DialogNode();
        node.index = tag.getInteger("index");
        node.dialogText = tag.getString("dialogText");
        NBTTagList list = (NBTTagList) tag.getTag("entries");
        for (int i = 0; i < list.tagCount(); i++) {
            node.entries.add(DialogEntry.loadFromNBT(list.getCompoundTagAt(i)));
        }
        return node;
    }
}
