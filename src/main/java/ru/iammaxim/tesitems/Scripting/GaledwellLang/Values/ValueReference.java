package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;


import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.CompilerDebugRuntime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;


/**
 * Created by maxim on 2/12/17 at 1:13 PM.
 */
public class ValueReference extends Value {
    public int id;
    public String path;

    public ValueReference(int id) {
        this.id = id;
    }

    public ValueReference(String path) {
        this.path = path;
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
        tag.setString("type", "reference");
        tag.setInteger("index", id);
        tag.setString("path", path);
        return tag;
    }

    @Override
    public String toString() {
        if (path != null)
            return "reference: " + path;
        return "reference: " + CompilerDebugRuntime.getName(id);
    }

/*    @Override
    public String toString(Runtime runtime, int indent) {
        return "reference: " + CompilerDebugRuntime.getName(id);
    }*/
}
