package ru.iammaxim.tesitems.Fractions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maxim on 8/5/16 at 7:56 PM.
 */
public class FactionManager {
    public static HashMap<Integer, Faction> factions = new HashMap<>();

    public static void addFaction(Faction faction) {
        factions.put(faction.id, faction);
    }

    public static Faction getFaction(int id) {
        return factions.get(id);
    }
}
