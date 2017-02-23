package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueBoolean;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueNull;

/**
 * Created by maxim on 2/19/17 at 2:51 PM.
 */
public class OperationEquals extends Operation {
    @Override
    public String toString() {
        return "equals";
    }

    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        Value left = runtime.stack.pop();
        Value right = runtime.stack.pop();

        if (left == null && right instanceof ValueNull ||
                right == null && left instanceof ValueNull ||
                left == null && right == null)
            runtime.stack.push(new ValueBoolean(true));
        else {
            if (left == null || right == null)
                runtime.stack.push(new ValueBoolean(false));
            else if (left.getClass() != right.getClass())
                runtime.stack.push(new ValueBoolean(false));
            else
                runtime.stack.push(left.operatorEquals(right));
        }

    }
}
