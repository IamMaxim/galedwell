package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/17/17 at 9:50 PM.
 */
public class OperationAdd extends Operation {
    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        runtime.stack.push(runtime.stack.pop().operatorPlus(runtime.stack.pop()));
    }

    @Override
    public String toString() {
        return "add";
    }
}
