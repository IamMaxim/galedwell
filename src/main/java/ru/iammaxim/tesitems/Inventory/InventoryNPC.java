package ru.iammaxim.tesitems.Inventory;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.NPC.EntityNPC;

/**
 * Created by maxim on 7/28/16 at 10:28 PM.
 */
public class InventoryNPC extends Inventory {
    private EntityNPC npc;

    public InventoryNPC(EntityNPC npc) {
        this.npc = npc;
    }
}
