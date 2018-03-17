package ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Player;

import net.minecraft.util.DamageSource;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFloat;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueInt;

/**
 * Created by maxim on 2/19/17 at 12:21 AM.
 */
public class FunctionPlayerDamage extends ValueFunction {
    public FunctionPlayerDamage() {
        super("damage", "damage");
    }

    @Override
    public void call(Runtime runtime, Value... args) throws InvalidOperationException {
        if (args[0] instanceof ValueInt)
            args[0] = new ValueFloat(((ValueInt) args[0]).value);

        ((ValuePlayer) runtime.variableStorage.getField("player")).player.attackEntityFrom(DamageSource.generic, ((ValueFloat) args[0]).value);
        runtime.stack.push(null);
    }
}
