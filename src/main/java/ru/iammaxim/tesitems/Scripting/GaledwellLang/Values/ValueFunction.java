package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Utils;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

import java.util.Arrays;

/**
 * Created by maxim on 2/12/17 at 1:16 AM.
 */
public abstract class ValueFunction extends Value {
    public int id;
    public int[] args;

    public ValueFunction(int id, int... args) {
        this.id = id;
        this.args = args;
    }

    public ValueFunction(String name, String... args) {
        this(name.hashCode(), convertNameArrayToIDArray(args));
    }

    private static int[] convertNameArrayToIDArray(String[] args) {
        int[] newArgs = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            newArgs[i] = args[i].hashCode();
        }
        return newArgs;
    }

    @Override
    public String toString(Runtime runtime, int indent) {
        return "{\n" +
                Utils.indent(indent + 1) + "\"function\":\n" +
                Utils.indent(indent + 2) + "{\n" +
                Utils.indent(indent + 3) + "\"path\": " + id + ",\n" +
                Utils.indent(indent + 3) + "\"args\": " + Arrays.toString(args) + "\n" +
                Utils.indent(indent + 2) + "}\n" +
                Utils.indent(indent + 1) + "}";
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
        tag.setString("type", "function");
        return tag;
    }

    public abstract void call(Runtime runtime, Value... args) throws InvalidOperationException;
}
