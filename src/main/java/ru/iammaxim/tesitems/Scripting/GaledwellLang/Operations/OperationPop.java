package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/17/17 at 9:35 PM.
 */
public class OperationPop extends Operation {
    @Override
    public Value run(Runtime runtime) {
        return runtime.stack.pop();
    }

    @Override
    public String toString(Runtime runtime, int indent) {
        return "pop";
    }
}
