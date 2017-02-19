package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/19/17 at 2:51 PM.
 */
public class OperationMoreEquals extends Operation {
    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        runtime.stack.push(runtime.stack.pop().operatorMoreEquals(runtime.stack.pop()));
    }

    @Override
    public String toString() {
        return "moreEquals";
    }
}
