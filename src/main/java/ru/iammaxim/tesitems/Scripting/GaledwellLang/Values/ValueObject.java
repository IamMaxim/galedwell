package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;


import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;


import java.util.HashMap;

/**
 * Created by maxim on 2/12/17 at 10:18 AM.
 */
public class ValueObject extends Value {
    protected HashMap<Integer, Value> fields = new HashMap<>();

    public ValueObject(String value) {
    }

    public ValueObject() {}

    @Override
    public String toString() {
        return "value: " + this.toString(null, 0);
    }

    @Override
    public Value operatorPlus(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorMinus(Value right) throws InvalidOperationException {
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
    public Value operatorEquals(Value right) throws InvalidOperationException {
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
        setField(name.hashCode(), value);
    }

    public Value getField(int id) {
        return fields.get(id);
    }

    public void removeField(int id) {
        fields.remove(id);
    }

    @Override
    public String toString(Runtime runtime, int indent) {
/*        StringJoiner sj = new StringJoiner(",\n" + Utils.indent(indent + 3));
        fields.forEach((path, value) -> sj.add("\"" + path + "\"" + ": " + value.toString(runtime, indent + 3)));
        return "\"object\":\n" +
                Utils.indent(indent + 1) + "{\n" +
                Utils.indent(indent + 2) + sj.toString() + "\n" +
                Utils.indent(indent + 1) + "}";*/

        return "object";
    }

    public static boolean isValid(String value) {
        return value.equals("Object");
    }
}
