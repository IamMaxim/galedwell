package ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueVoid;

/**
 * Created by maxim on 2/12/17 at 12:27 PM.
 */
public class FunctionPrint extends ValueFunction {
    public FunctionPrint() {
        super("print", "values");
    }

    @Override
    public void call(Runtime runtime, Value... arguments) {
        String[] newArgs = new String[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            newArgs[i] = arguments[i].valueToString();
        }
        System.out.println(String.join(" ", newArgs));

        runtime.stack.push(new ValueVoid());
    }
}
