package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;

/**
 * Created by maxim on 2/12/17 at 10:39 AM.
 */
public class ValueString extends Value {
    public String value;

    public ValueString(String value) {
        this.value = value;
    }

    @Override
    public String valueToString() {
        return value;
    }

    @Override
    public String toString() {
        return "string: \"" + value + "\"";
    }

    @Override
    public Value operatorEquals(Value right) throws InvalidOperationException {
        return new ValueBoolean(value.equals(((ValueString)right).value));
    }

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "string");
        tag.setString("value", value);
        return tag;
    }

    public static ValueString loadValueFromNBT(NBTTagCompound tag) {
        return new ValueString(tag.getString("value"));
    }

    public static boolean isValid(String value) {
        return value.startsWith("\"") && value.endsWith("\"");
    }

/*    @Override
    public String toString(Runtime runtime, int indent) {
        return value;
    }*/
}
