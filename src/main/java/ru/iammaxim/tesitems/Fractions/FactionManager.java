package ru.iammaxim.tesitems.Fractions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maxim on 8/5/16 at 7:56 PM.
 */
public class FactionManager {
    public HashMap<String, Faction> factions = new HashMap<>();

    public void addFaction(Faction faction) {
        factions.put(faction.name, faction);
    }

    public Faction getFaction(String name) {
        return factions.get(name);
    }
}
