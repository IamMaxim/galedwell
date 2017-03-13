package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression;

import java.util.ArrayList;

/**
 * Created by maxim on 2/19/17 at 1:55 AM.
 */
public class ExpressionCondition extends Expression {
    public int lineNumber;

    @Override
    public String toString() {
        return "condition: if (" + cond.toString() + ") {" + body.toString() + "}" + (elseBody != null ? " else {" + elseBody.toString() + "}" : "");
    }

    public Expression cond;
    public ArrayList<Expression> body;
    public ArrayList<Expression> elseBody;

    public ExpressionCondition(int lineNumber, Expression exp, ArrayList<Expression> body, ArrayList<Expression> elseBody) {
        this.lineNumber = lineNumber;
        this.cond = exp;
        this.body = body;
        this.elseBody = elseBody;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }
}
