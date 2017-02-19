package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/17/17 at 9:35 PM.
 */
public class OperationPop extends Operation {
    @Override
    public void run(Runtime runtime) {
        runtime.stack.pop();
    }

    @Override
    public String toString() {
        return "pop";
    }
}
