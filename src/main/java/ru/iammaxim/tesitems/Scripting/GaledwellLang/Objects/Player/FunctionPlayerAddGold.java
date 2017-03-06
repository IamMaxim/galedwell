package ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Player;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueInt;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueNull;

/**
 * Created by maxim on 06.03.2017.
 */
public class FunctionPlayerAddGold extends ValueFunction {
    public FunctionPlayerAddGold() {
        super("addGold", "player", "gold");
    }

    @Override
    public void call(Runtime runtime, Value... args) throws InvalidOperationException {
        ((ValuePlayer) runtime.variableStorage.getField("player")).cap.addGold(((ValueInt) args[0]).value);
        runtime.stack.push(new ValueNull());
    }
}
