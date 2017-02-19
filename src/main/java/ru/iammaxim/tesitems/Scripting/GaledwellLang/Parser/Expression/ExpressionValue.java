package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;

/**
 * Created by maxim on 2/17/17 at 7:36 PM.
 */
public class ExpressionValue extends Expression {
    public Value value;

    @Override
    public String toString() {
        return value.toString(null, 0);
    }

    public ExpressionValue(Value value) {
        this.value = value;
    }
}
