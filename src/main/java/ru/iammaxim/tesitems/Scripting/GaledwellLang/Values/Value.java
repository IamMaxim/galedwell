package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;


import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;


/**
 * Created by maxim on 2/12/17 at 10:09 AM.
 */
public abstract class Value {
    public abstract String valueToString();

    public Value() {
    }

    public static Value get(String value) {
        if (ValueInt.isValid(value))
            return new ValueInt(Integer.parseInt(value));
        else if (ValueFloat.isValid(value))
            return new ValueFloat(Float.parseFloat(value));
        else if (ValueString.isValid(value))
            return new ValueString(value.substring(1, value.length() - 1)); //remove quotes
        else if (ValueObject.isValid(value))
            return new ValueObject();
        else if (ValueNull.isValid(value))
            return new ValueNull();
        else return new ValueReference(value);
    }

    public Value operatorPlus(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    public Value operatorSubtract(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    public Value operatorMultiply(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    public Value operatorDivide(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    public Value operatorLess(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    public Value operatorLessEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    public Value operatorEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    public Value operatorMoreEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    public Value operatorMore(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    public abstract NBTTagCompound writeToNBT();

    public static Value loadValueFromNBT(NBTTagCompound tag) {
        String type = tag.getString("type");

        switch (type) {
            case "object":
                return ValueObject.loadValueFromNBT(tag);
        }

        return new ValueNull();
    }

}
