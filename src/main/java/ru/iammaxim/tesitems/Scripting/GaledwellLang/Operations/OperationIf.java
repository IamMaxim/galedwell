package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueBoolean;

/**
 * Created by maxim on 2/19/17 at 3:34 PM.
 */
public class OperationIf extends Operation {
    public OperationLabel labelToGo; // operation index to go if condition is false

    public OperationIf(OperationLabel labelToGo) {
        this.labelToGo = labelToGo;
    }

    @Override
    public String toString() {
        return "if: labelID " + labelToGo.index;
    }

    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        if (!((ValueBoolean) runtime.stack.pop()).value)
            runtime.currentCursorPos = labelToGo.index - 1;
    }
}
