package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;


import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by maxim on 2/17/17 at 10:16 PM.
 */
public class ValueBoolean extends Value {
    public boolean value;

    public ValueBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public String valueToString() {
        return String.valueOf(value);
    }

    @Override
    public String toString() {
        return "bool: " + value;
    }

    @Override
    public Value operatorEquals(Value right) {
        if (!(right instanceof ValueBoolean))
            return new ValueBoolean(false);
        return new ValueBoolean(value == ((ValueBoolean)right).value);
    }

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "boolean");
        tag.setBoolean("value", value);
        return tag;
    }

    public static ValueBoolean loadValueFromNBT(NBTTagCompound tag) {
        return new ValueBoolean(tag.getBoolean("value"));
    }
}
