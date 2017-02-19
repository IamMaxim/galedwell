package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;

/**
 * Created by maxim on 2/18/17 at 4:00 PM.
 */
public class OperationPushVariableStorage extends Operation {
    @Override
    public Value run(Runtime runtime) throws InvalidOperationException {
        runtime.stack.push(runtime.variableStorage);
        return null;
    }

    @Override
    public String toString(Runtime runtime, int indent) {
        return "pushVariableStorage";
    }
}
