package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueReference;

/**
 * Created by maxim on 2/12/17 at 11:13 AM.
 */
public class OperationCall extends Operation {
    public int argsCount;

    public OperationCall(int argsCount) {
        this.argsCount = argsCount;
    }

    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        int id = ((ValueReference) runtime.stack.pop()).id; //function ID
        ValueObject parent = (ValueObject) runtime.stack.pop(); //object that contains this function

        Value[] newArgs = new Value[argsCount]; //arguments to pass
        for (int i = 0; i < newArgs.length; i++) {
            Value arg = runtime.stack.pop();
            if (arg instanceof ValueReference) {
                int argID = ((ValueReference) arg).id;
                ValueObject argParent = (ValueObject) runtime.stack.pop();
                newArgs[i] = argParent.getField(argID);
            } else {
                newArgs[i] = arg;
            }
        }

        int currentPosBackup = runtime.currentCursorPos;
        int currentFunctionLengthBackup = runtime.currentFunctionLength;
        ((ValueFunction) parent.getField(id)).call(runtime, newArgs);
        runtime.currentCursorPos = currentPosBackup;
        runtime.currentFunctionLength = currentFunctionLengthBackup;
    }

    @Override
    public String toString() {
        return "call";
    }
}
