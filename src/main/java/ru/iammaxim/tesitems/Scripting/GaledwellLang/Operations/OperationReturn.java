package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/17/17 at 8:59 PM.
 */
public class OperationReturn extends Operation {
    public OperationReturn() {
    }

    @Override
    public Value run(Runtime runtime) {
        runtime.goToFunctionEnd();
        return null;
    }

    @Override
    public String toString(Runtime runtime, int indent) {
        return "return";
    }
}
