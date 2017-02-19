package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;

/**
 * Created by maxim on 2/18/17 at 4:19 PM.
 */
public class OperationGetAndPush extends Operation {
    public int id;

    public OperationGetAndPush(int id) {
        this.id = id;
    }

    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        runtime.stack.push(((ValueObject) runtime.stack.pop()).getField(id));
    }

    @Override
    public String toString() {
        return "getAndPush: " + id;
    }
}
