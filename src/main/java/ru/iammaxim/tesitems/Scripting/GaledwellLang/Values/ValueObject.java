package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;


import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.CompilerDebugRuntime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;

import java.util.HashMap;

/**
 * Created by maxim on 2/12/17 at 10:18 AM.
 */
public class ValueObject extends Value {
    protected HashMap<Integer, Value> fields = new HashMap<>();

    @Override
    public String valueToString() {
        StringBuilder sb = new StringBuilder();

        fields.forEach((id, value) -> {
            sb.append("\n    ").append(CompilerDebugRuntime.getName(id)).append("=").append(value);
        });

        return "object: {" + sb.toString() + "\n}";
    }

    public ValueObject() {}

    @Override
    public String toString() {
        return "object: " + fields;
    }

    @Override
    public Value operatorPlus(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorSubtract(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorMultiply(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorDivide(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorLess(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorLessEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorMoreEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorMore(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "object");
        NBTTagCompound fieldsTag = new NBTTagCompound();
        fields.forEach((id, val) -> fieldsTag.setTag(String.valueOf(id), val.writeToNBT()));
        tag.setTag("fields", fieldsTag);
        return tag;
    }

    public void setField(int id, Value value) {
        fields.put(id, value);
    }

    public void setField(String name, Value value) {
        CompilerDebugRuntime.addName(name.hashCode(), name);

        setField(name.hashCode(), value);
    }

    public Value getField(int id) {
        return fields.get(id);
    }

    public Value getField(String name) {
        return fields.get(name.hashCode());
    }

    public void removeField(int id) {
        fields.remove(id);
    }

    public static boolean isValid(String value) {
        return value.equals("Object");
    }
}
