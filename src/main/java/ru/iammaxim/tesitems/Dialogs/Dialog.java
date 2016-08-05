package ru.iammaxim.tesitems.Dialogs;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.Questing.QuestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 8/5/16 at 8:02 PM.
 */
public class Dialog {
    public String NPCname;
    public String text;
    public List<DialogEntry> entries = new ArrayList<>();

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("NPCname", NPCname);
        tag.setString("text", text);
        NBTTagList entriesTag = new NBTTagList();
        entries.forEach((entry) -> entriesTag.appendTag(entry.writeToNBT()));
        tag.setTag("entries", entriesTag);
        return tag;
    }

    public void loadFromNBT(NBTTagCompound tag) {
        NPCname = tag.getString("NPCname");
        text = tag.getString("text");
        NBTTagList entriesTag = (NBTTagList) tag.getTag("entries");
        for (int i = 0; i < entriesTag.tagCount(); i++) {
            NBTTagCompound entryTag = (NBTTagCompound) entriesTag.get(0);
            entries.add(new DialogEntry(entryTag.getString("name"), QuestManager.getByID(entryTag.getInteger("questID"))));
        }
    }
}
