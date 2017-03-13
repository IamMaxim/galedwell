package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;

/**
 * Created by maxim on 2/19/17 at 1:55 AM.
 */
public class ExpressionForLoop extends Expression {
    public Expression statement;
    public Expression condition;
    public Expression iterCompletedStatement;
    public ArrayList<Expression> body;
    private int lineNumber;

    @Override
    public String toString() {
        return "for (" + statement + "; " + condition + "; " + iterCompletedStatement + ") {" + body.toString() + "}";
    }

    public ExpressionForLoop(int lineNumber, Expression exp, Expression cond, Expression iterCompletedExp, ArrayList<Expression> body) {
        this.lineNumber = lineNumber;
        this.statement = exp;
        this.condition = cond;
        this.iterCompletedStatement = iterCompletedExp;
        this.body = body;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }
}
