package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueReference;

public class OperationValueAt extends Operation {
    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
//        runtime.stack.output();
        Value key = runtime.stack.pop();
        Value val = runtime.stack.pop();
        ValueObject parent = (ValueObject) runtime.stack.pop();
        runtime.stack.push(parent.getField(((ValueReference) val).id).operatorAt(key));
    }

    @Override
    public String toString() {
        return "valueAt";
    }
}
