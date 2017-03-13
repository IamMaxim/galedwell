package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueInt;

/**
 * Created by maxim on 2/17/17 at 9:50 PM.
 */
public class OperationDecrement extends Operation {
    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        Value value = runtime.stack.get();
        value.operatorDecrement();
    }

    @Override
    public String toString() {
        return "decrement";
    }
}
