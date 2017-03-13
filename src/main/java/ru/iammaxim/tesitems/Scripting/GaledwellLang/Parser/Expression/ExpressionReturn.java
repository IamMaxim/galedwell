package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression;

/**
 * Created by maxim on 2/18/17 at 8:09 PM.
 */
public class ExpressionReturn extends Expression {
    public Expression returnExp;

    public ExpressionReturn(Expression returnExp) {
        this.returnExp = returnExp;
    }

    @Override
    public String toString() {
        return "return: " + returnExp.toString();
    }
}
