package ru.iammaxim.tesitems.Dialogs;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.Questing.QuestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maxim on 8/5/16 at 8:02 PM.
 */
public class Dialog {
    public HashMap<Integer, DialogNode> nodes = new HashMap<>();

    public NBTTagCompound saveToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        nodes.forEach((index,node) -> list.appendTag(node.saveToNBT()));
        tag.setTag("nodes", list);
        return tag;
    }

    public static Dialog loadFromNBT(NBTTagCompound tag) {
        Dialog dialog = new Dialog();
        NBTTagList list = (NBTTagList) tag.getTag("nodes");
        for (int i = 0; i < list.tagCount(); i++) {
            DialogNode node = DialogNode.loadFromNBT(list.getCompoundTagAt(i));
            dialog.nodes.put(node.index, node);
        }
        return dialog;
    }
}
