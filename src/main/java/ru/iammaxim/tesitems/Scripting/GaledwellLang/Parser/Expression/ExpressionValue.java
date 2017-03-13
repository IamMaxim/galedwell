package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;

/**
 * Created by maxim on 2/17/17 at 7:36 PM.
 */
public class ExpressionValue extends Expression {
    public Value value;
    private int lineNumber;

    @Override
    public String toString() {
        return value.toString();
    }

    public ExpressionValue(int lineNumber, Value value) {
        this.lineNumber = lineNumber;
        this.value = value;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }
}
