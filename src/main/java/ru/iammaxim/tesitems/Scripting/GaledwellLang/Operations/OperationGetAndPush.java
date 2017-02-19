package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/18/17 at 4:19 PM.
 */
public class OperationGetAndPush extends Operation {
    public int id;

    public OperationGetAndPush(int id) {
        this.id = id;
    }

    @Override
    public Value run(Runtime runtime) throws InvalidOperationException {
        ValueObject obj = (ValueObject) runtime.stack.pop();
        runtime.stack.push(obj.getField(id));
        return null;
    }

    @Override
    public String toString(Runtime runtime, int indent) {
        return "getAndPush: " + id;
    }
}
