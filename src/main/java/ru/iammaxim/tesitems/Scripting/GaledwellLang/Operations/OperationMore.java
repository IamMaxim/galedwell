package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/19/17 at 2:51 PM.
 */
public class OperationMore extends Operation {
    @Override
    public String toString() {
        return "more";
    }

    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        runtime.stack.push(runtime.stack.pop().operatorMore(runtime.stack.pop()));
    }
}
