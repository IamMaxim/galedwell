package ru.iammaxim.tesitems.Scripting;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions.FunctionDumpVariableStorage;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions.FunctionPrint;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 11/5/16 at 6:58 PM.
 */
public class ScriptEngine {
    public static Runtime runtime;

    static {
        runtime = new Runtime();
        runtime.variableStorage.setField("print", new FunctionPrint());
        runtime.variableStorage.setField("dumpVarStorage", new FunctionDumpVariableStorage());
    }
}
