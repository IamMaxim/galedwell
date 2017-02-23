package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;

/**
 * Created by maxim on 2/20/17 at 4:24 PM.
 */
public class ValueNull extends Value {
    @Override
    public String valueToString() {
        return "null";
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public Value operatorEquals(Value right) throws InvalidOperationException {
        return new ValueBoolean(right instanceof ValueNull);
    }

    @Override
    public NBTTagCompound writeToNBT() {
        return null;
    }

    public static boolean isValid(String token) {
        return token.equals("null");
    }
}
