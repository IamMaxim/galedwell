package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/17/17 at 11:11 PM.
 */
public class OperationDiv extends Operation {
    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        runtime.stack.push(runtime.stack.pop().operatorDivide(runtime.stack.pop()));
    }

    @Override
    public String toString() {
        return "div";
    }
}
