package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;


import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;


/**
 * Created by maxim on 2/12/17 at 10:09 AM.
 */
public abstract class Value {
//    public abstract String toString(Runtime runtime, int indent);
    public Value() {}
    public static Value get(String value) {
        if (ValueInt.isValid(value))
            return new ValueInt(Integer.parseInt(value));
        else if (ValueFloat.isValid(value))
            return new ValueFloat(Float.parseFloat(value));
        else if (ValueString.isValid(value))
            return new ValueString(value.substring(1, value.length() - 1)); //remove quotes
        else if (ValueObject.isValid(value))
            return new ValueObject();
        else return new ValueReference(value);
    }
    public abstract Value operatorPlus(Value right) throws InvalidOperationException;
    public abstract Value operatorSubtract(Value right) throws InvalidOperationException;
    public abstract Value operatorMultiply(Value right) throws InvalidOperationException;
    public abstract Value operatorDivide(Value right) throws InvalidOperationException;
    public abstract Value operatorLess(Value right) throws InvalidOperationException;
    public abstract Value operatorLessEquals(Value right) throws InvalidOperationException;
    public abstract Value operatorEquals(Value right) throws InvalidOperationException;
    public abstract Value operatorMoreEquals(Value right) throws InvalidOperationException;
    public abstract Value operatorMore(Value right) throws InvalidOperationException;

    public abstract NBTTagCompound writeToNBT();

    public static Value loadFromNBT(NBTTagCompound tag) {
        return null;
    }

}
