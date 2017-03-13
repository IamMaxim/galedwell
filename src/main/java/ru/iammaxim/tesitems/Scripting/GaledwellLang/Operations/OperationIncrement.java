package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueInt;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueReference;

/**
 * Created by maxim on 2/17/17 at 9:50 PM.
 */
public class OperationIncrement extends Operation {
    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        Value value = runtime.stack.get();
        value.operatorIncrement();
    }

    @Override
    public String toString() {
        return "increment";
    }
}
