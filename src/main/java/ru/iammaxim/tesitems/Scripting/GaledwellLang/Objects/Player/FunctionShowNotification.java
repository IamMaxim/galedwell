package ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Player;

import net.minecraft.entity.player.EntityPlayerMP;
import ru.iammaxim.tesitems.Networking.MessageShowNotification;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 2/20/17 at 6:19 PM.
 */
public class FunctionShowNotification extends ValueFunction {
    public FunctionShowNotification() {
        super("showNotificaion", "text");
    }

    @Override
    public void call(Runtime runtime, Value... args) throws InvalidOperationException {
        TESItems.networkWrapper.sendTo(new MessageShowNotification(args[0].valueToString()), (EntityPlayerMP) ((ValuePlayer) runtime.variableStorage.getField("player")).player);

        runtime.stack.push(null);
    }
}
