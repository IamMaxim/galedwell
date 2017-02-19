package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/19/17 at 2:51 PM.
 */
public class OperationLessEquals extends Operation {
    @Override
    public String toString() {
        return "lessEquals";
    }

    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        runtime.stack.push(runtime.stack.pop().operatorLessEquals(runtime.stack.pop()));
    }
}
