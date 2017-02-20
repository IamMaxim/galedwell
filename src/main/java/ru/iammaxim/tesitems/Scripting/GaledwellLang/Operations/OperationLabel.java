package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/19/17 at 3:20 PM.
 */
//this class is just a placeholder, it is being removed during compilation
public class OperationLabel extends Operation {
    public static int nextLabelID = 0;
    public int index;

    public OperationLabel() {
        index = nextLabelID++;
    }

    @Override
    public String toString() {
        return "label ID " + index;
    }

    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        throw new InvalidOperationException("Heeeey, labels shouldn't be executed!");
    }
}
