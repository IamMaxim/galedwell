package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.FunctionParser;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.InvalidTokenException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Token;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Tokener;

import java.util.ArrayList;

/**
 * Created by maxim on 2/17/17 at 7:34 PM.
 */
public class ExpressionFunctionCall extends Expression {
    public String functionName;
    public ArrayList<Expression> args;
    public int lineNumber;

    public ExpressionFunctionCall(int lineNumber, Token functionName, ArrayList<Tokener> args) throws InvalidTokenException {
        this.lineNumber = lineNumber;
        this.functionName = functionName.token;
        this.args = new ArrayList<>(args.size());
        for (Tokener arg : args) {
            this.args.add(FunctionParser.parseExpression(arg));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("call: ");
        sb.append(functionName);
        sb.append("(");
        for (int i = 0; i < args.size(); i++) {
            sb.append(args.get(i));
            if (i < args.size() - 1)
                sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }
}
