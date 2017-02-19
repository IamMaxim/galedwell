package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/17/17 at 11:09 PM.
 */
public class OperationSub extends Operation {
    @Override
    public Value run(Runtime runtime) throws InvalidOperationException {
        Value first = runtime.stack.pop();
        Value second = runtime.stack.pop();
        Value result = first.operatorMinus(second);
        runtime.stack.push(result);

        return result;
    }

    @Override
    public String toString(Runtime runtime, int indent) {
        return "sub";
    }
}
