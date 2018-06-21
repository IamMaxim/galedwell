package ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;

/**
 * Created by maxim on 2/19/17 at 7:48 PM.
 */
public class FunctionDumpVariableStorage extends ValueFunction {
    public FunctionDumpVariableStorage() {
        super("dumpVarStorage");
    }

    @Override
    public void call(Runtime runtime, Value... args) {
        System.out.println(">>> dump variable storage <<<");
        System.out.println(runtime.variableStorage.valueToString());
        runtime.stack.push(null);
    }
}
