package ru.iammaxim.tesitems.Dialogs;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Questing.Quest;

/**
 * Created by maxim on 8/5/16 at 8:07 PM.
 */
public class DialogEntry {
    public String name;
    public Quest attachedQuest;

    public DialogEntry(String name, Quest attachedQuest) {
        this.name = name;
        this.attachedQuest = attachedQuest;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("name", name);
        tag.setInteger("questID", attachedQuest.id);
        return tag;
    }
}
