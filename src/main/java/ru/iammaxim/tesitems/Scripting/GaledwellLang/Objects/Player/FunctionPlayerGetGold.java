package ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Player;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueInt;

/**
 * Created by maxim on 06.03.2017.
 */
public class FunctionPlayerGetGold extends ValueFunction {
    public FunctionPlayerGetGold() {
        super("getGold", "player");
    }

    @Override
    public void call(Runtime runtime, Value... args) {
        runtime.stack.push(new ValueInt(((ValuePlayer) runtime.variableStorage.getField("player")).cap.getGold()));
    }
}
