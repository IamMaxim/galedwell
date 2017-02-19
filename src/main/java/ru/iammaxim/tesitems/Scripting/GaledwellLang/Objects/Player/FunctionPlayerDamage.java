package ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFloat;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;

/**
 * Created by maxim on 2/19/17 at 12:21 AM.
 */
public class FunctionPlayerDamage extends ValueFunction {
    public FunctionPlayerDamage(EntityPlayer player) {
        super("damage", "player", "damage");
    }

    @Override
    public void call(Runtime runtime, Value... args) throws InvalidOperationException {
        ((ValuePlayer)args[0]).player.attackEntityFrom(DamageSource.generic, ((ValueFloat) args[1]).value);
    }
}
