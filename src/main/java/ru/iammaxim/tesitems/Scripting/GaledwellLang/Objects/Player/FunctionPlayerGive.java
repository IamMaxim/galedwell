package ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Player;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueInt;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueString;

/**
 * Created by maxim on 2/19/17 at 12:36 AM.
 */
public class FunctionPlayerGive extends ValueFunction {
    public FunctionPlayerGive() {
        super("give", "itemName", "count");
    }

    @Override
    public void call(Runtime runtime, Value... args) throws InvalidOperationException {
        ((ValuePlayer)runtime.variableStorage.getField("player")).cap.getInventory().addItem(new ItemStack(Item.getByNameOrId(((ValueString) args[0]).value), ((ValueInt)args[1]).value));

        runtime.stack.push(null);
    }
}
