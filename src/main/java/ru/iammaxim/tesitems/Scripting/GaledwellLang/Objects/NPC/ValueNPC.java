package ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.NPC;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;

/**
 * Created by maxim on 2/20/17 at 6:27 PM.
 */
public class ValueNPC extends ValueObject {
    public NPC npc;

    public ValueNPC(NPC npc) {
        this.npc = npc;
    }

    @Override
    public String valueToString() {
        return toString();
    }

    @Override
    public NBTTagCompound writeToNBT() {
        return null;
    }

    @Override
    public String toString() {
        return "NPC: " + npc.name;
    }
}
