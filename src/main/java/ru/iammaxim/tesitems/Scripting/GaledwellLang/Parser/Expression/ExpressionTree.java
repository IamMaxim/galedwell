package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Token;

/**
 * Created by maxim on 2/17/17 at 7:33 PM.
 */
public class ExpressionTree extends Expression {
    public Token operator;
    public Expression left, right;

    public ExpressionTree(Token operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public ExpressionTree() {};

    public ExpressionTree(Token operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        if (operator == null)
            return "null";

        StringBuilder sb = new StringBuilder();
        if (left != null)
            sb.append("(").append(left).append(")");
        sb.append(operator.token);
        if (right != null)
            sb.append("(").append(right).append(")");
        return sb.toString();
    }
}
