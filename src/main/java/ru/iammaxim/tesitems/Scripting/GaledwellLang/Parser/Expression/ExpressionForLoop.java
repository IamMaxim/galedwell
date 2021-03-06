package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression;

import java.util.ArrayList;

/**
 * Created by maxim on 2/19/17 at 1:55 AM.
 */
public class ExpressionForLoop extends Expression {
    public Expression first;
    public Expression second;
    public Expression third;
    public ArrayList<Expression> body;

    @Override
    public String toString() {
        return "for (" + first + "; " + second + "; " + third + ") {" + body.toString() + "}";
    }

    public ExpressionForLoop(Expression exp, Expression cond, Expression iterCompletedExp, ArrayList<Expression> body) {
        this.first = exp;
        this.second = cond;
        this.third = iterCompletedExp;
        this.body = body;
    }
}
