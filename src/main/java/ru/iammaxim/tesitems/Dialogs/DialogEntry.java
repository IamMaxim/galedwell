package ru.iammaxim.tesitems.Dialogs;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by maxim on 11/5/16 at 6:52 PM.
 */
public class DialogEntry {
    public String text;
    public String script;

    public NBTTagCompound saveToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("text", text);
        tag.setString("script", script);
        return tag;
    }

    public static DialogEntry loadFromNBT(NBTTagCompound tag) {
        DialogEntry entry = new DialogEntry();
        entry.text = tag.getString("text");
        entry.script = tag.getString("script");
        return entry;
    }
}
