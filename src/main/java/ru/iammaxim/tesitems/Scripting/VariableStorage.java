package ru.iammaxim.tesitems.Scripting;

import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.StringJoiner;

/**
 * Created by maxim on 1/22/17 at 3:38 PM.
 */
public class VariableStorage {
    private HashMap<String, String> variables = new HashMap<>();

    public void setVar(String name, String value) {
        variables.put(name, value);
    }

    public String getVar(String name) {
        return variables.get(name);
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound vars = new NBTTagCompound();
        variables.forEach(vars::setString);
        return vars;
    }

    public void loadFromNBT(NBTTagCompound tag) {
        tag.getKeySet().forEach(key -> {
            variables.put(key, tag.getString(key));
        });
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ");
        variables.forEach((name, value) -> sj.add(name + ": " + value));
        return sj.toString();
    }
}
