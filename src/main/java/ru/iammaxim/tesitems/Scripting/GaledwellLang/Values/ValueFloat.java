package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/12/17 at 12:59 PM.
 */
public class ValueFloat extends Value {
    public float value;

    public ValueFloat(float value) {
        this.value = value;
    }

    @Override
    public String toString(Runtime runtime, int indent) {
        return String.valueOf(value);
    }

    @Override
    public Value operatorPlus(Value right) {
        return new ValueFloat(value + ((ValueFloat)right).value);
    }

    @Override
    public Value operatorMinus(Value right) {
        return new ValueFloat(value + ((ValueFloat)right).value);
    }

    @Override
    public String toString() {
        return "float: " + value;
    }

    @Override
    public Value operatorMultiply(Value right) {
        return new ValueFloat(value + ((ValueFloat)right).value);
    }

    @Override
    public Value operatorDivide(Value right) {
        return new ValueFloat(value + ((ValueFloat)right).value);
    }

    @Override
    public Value operatorEquals(Value right) {
        return new ValueBoolean(value == ((ValueFloat)right).value);
    }

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "float");
        tag.setFloat("value", value);
        return tag;
    }

    public static boolean isValid(String value) {
        return value.endsWith("f") && value.matches("[0-9]*.[0-9]*f");
    }
}
