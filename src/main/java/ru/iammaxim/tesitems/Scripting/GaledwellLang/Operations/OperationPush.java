package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/17/17 at 9:32 PM.
 */
public class OperationPush extends Operation {
    public Value value;

    public OperationPush(Value value) {
        this.value = value;
    }

    @Override
    public void run(Runtime runtime) {
        runtime.stack.push(value);
    }

    @Override
    public String toString() {
        return "push: " + value;
    }
}
