package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/17/17 at 8:59 PM.
 */
public class OperationReturn extends Operation {
    public OperationReturn() {
    }

    @Override
    public void run(Runtime runtime) {
        runtime.goToFunctionEnd();
    }

    @Override
    public String toString() {
        return "return";
    }
}
