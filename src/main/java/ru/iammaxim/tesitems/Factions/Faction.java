package ru.iammaxim.tesitems.Factions;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 8/5/16 at 7:56 PM.
 */
public class Faction {
    public int id;
    public String name;
    public List<String> phrases = new ArrayList<>();

    public Faction(String name) {
        this.name = name;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("name", name);
        NBTTagList list = new NBTTagList();
        phrases.forEach((phrase) -> {
            list.appendTag(new NBTTagCompound());
        });
        return tag;
    }
}
