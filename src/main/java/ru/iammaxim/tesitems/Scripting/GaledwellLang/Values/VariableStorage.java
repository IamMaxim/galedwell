package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

import java.util.HashMap;

/**
 * Created by maxim on 1/22/17 at 3:38 PM.
 */
public class VariableStorage extends ValueObject {
    private HashMap<Integer, Value> globalVariables = new HashMap<>();

    public VariableStorage(String value) {
        super(value);
    }

    public VariableStorage() {}

    public void setGlobalVar(int id, Value value) {
        globalVariables.put(id, value);
    }

    public Value getGlobalVar(int id) {
        return globalVariables.get(id);
    }

    @Override
    public String toString(Runtime runtime, int indent) {
        /*StringJoiner sj1 = new StringJoiner(",\n" + Utils.indent(indent + 2));
        StringJoiner sj2 = new StringJoiner(",\n" + Utils.indent(indent + 2));
        fields.forEach((path, value) -> sj1.add("\"" + path + "\"" + ": " + value.toString(runtime, indent + 2)));
        globalVariables.forEach((path, value) -> sj2.add("\"" + path + "\"" + ": " + value.toString(runtime, indent + 2)));
        return
                Utils.indent(indent) + "\"VariableStorage\": {\n" +
                Utils.indent(indent + 1) + "\"variables\": {" + "\n" +
                Utils.indent(indent + 2) + sj1.toString() + "\n" +
                Utils.indent(indent + 1) + "},\n" +
                Utils.indent(indent + 1) + "\"globalVariables\": {\n" +
                Utils.indent(indent + 2) + sj2.toString() + "\n" +
                Utils.indent(indent + 1) + "}\n" +
                Utils.indent(indent) + "}";*/

        return "variableStorage";
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "variableStorage");
        NBTTagCompound fieldsTag = new NBTTagCompound();
        globalVariables.forEach((id, val) -> fieldsTag.setTag(String.valueOf(id), val.writeToNBT()));
        tag.setTag("fields", fieldsTag);
        return tag;
    }

/*    public void loadFromNBT(NBTTagCompound tag) {
        tag.getKeySet().forEach(key -> globalVariables.put(Integer.parseInt(key), Value.loadFromNBT((NBTTagCompound) tag.getTag(key))));
    }*/
}
