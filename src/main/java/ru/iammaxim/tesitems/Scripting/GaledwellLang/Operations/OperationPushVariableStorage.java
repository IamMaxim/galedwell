package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/18/17 at 4:00 PM.
 */
public class OperationPushVariableStorage extends Operation {
    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        runtime.stack.push(runtime.variableStorage);
    }

    @Override
    public String toString() {
        return "pushVariableStorage";
    }
}
