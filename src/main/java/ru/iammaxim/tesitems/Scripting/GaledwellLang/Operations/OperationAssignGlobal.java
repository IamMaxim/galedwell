package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/12/17 at 4:35 PM.
 */
public class OperationAssignGlobal extends Operation {
    public int left;
    public Value right;

    public OperationAssignGlobal(int left, Value right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Value run(Runtime runtime) {
        runtime.variableStorage.setGlobalVar(left, right);
        return right;
    }

    @Override
    public String toString(Runtime runtime, int indent) {
        return "assignGlobal: " + left + " <- " + right.toString(runtime, indent + 4);
    }
}
