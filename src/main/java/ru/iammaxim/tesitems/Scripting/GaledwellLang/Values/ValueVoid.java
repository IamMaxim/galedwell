package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;

/**
 * Created by maxim on 2/18/17 at 10:30 PM.
 */
public class ValueVoid extends Value {
/*    @Override
    public String toString(Runtime runtime, int indent) {
        return "void";
    }*/

    @Override
    public String valueToString() {
        return "void";
    }

    @Override
    public NBTTagCompound writeToNBT() {
        return null;
    }
}
