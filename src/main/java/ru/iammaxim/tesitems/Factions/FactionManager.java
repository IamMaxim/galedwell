package ru.iammaxim.tesitems.Factions;

import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;

/**
 * Created by maxim on 8/5/16 at 7:56 PM.
 */
public class FactionManager {
    public static HashMap<Integer, Faction> factions = new HashMap<>();
    private static int nextID = -1;

    public static void addFaction(Faction faction) {
        factions.put(faction.id, faction);
    }

    public static Faction getFaction(int id) {
        return factions.get(id);
    }

    public static NBTTagList writeToNBT() {
        NBTTagList tagList = new NBTTagList();
        factions.forEach((id, f) -> tagList.appendTag(f.writeToNBT()));
        return tagList;
    }

    public static void readFromNBT(NBTTagList tagList) {
        System.out.println("loading factions: " + tagList.toString());
        for (int i = tagList.tagCount() - 1; i >= 0; i--) {
            Faction faction = Faction.loadFromNBT(tagList.getCompoundTagAt(i));
            factions.put(faction.id, faction);
            if (faction.id > nextID)
                nextID = faction.id;
        }
    }

    public static int nextID() {
        return ++nextID;
    }
}
