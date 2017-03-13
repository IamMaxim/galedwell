package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression;

/**
 * Created by maxim on 2/18/17 at 8:09 PM.
 */
public class ExpressionReturn extends Expression {
    public Expression returnExp;
    private int lineNumber;

    public ExpressionReturn(int lineNumber, Expression returnExp) {
        this.lineNumber = lineNumber;
        this.returnExp = returnExp;
    }

    @Override
    public String toString() {
        return "return: " + returnExp.toString();
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }
}
