package ru.iammaxim.tesitems.NPC;

import java.util.HashMap;

/**
 * Created by maxim on 1/17/17 at 3:46 PM.
 */
public class NPCManager {
    private HashMap<Integer, NPC> npcs = new HashMap<>();

    public void add(NPC npc) {
        npcs.put(npc.id, npc);
    }

    public NPC get(int id) {
        return npcs.get(id);
    }
}
