package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueReference;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/12/17 at 10:59 AM.
 */
public class OperationAssign extends Operation {
    @Override
    public Value run(Runtime runtime) {
        int name = ((ValueReference) runtime.stack.pop()).id;
        ValueObject parent = (ValueObject) runtime.stack.pop();
        Value value = runtime.stack.pop();
        parent.setField(name, value);
        runtime.stack.push(value);
        return value;
    }

    @Override
    public String toString(Runtime runtime, int indent) {
        return "assign";
    }
}
