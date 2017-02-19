package ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.GaledwellLang;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.*;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression.*;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.TokenType;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueReference;

import java.util.ArrayList;

/**
 * Created by maxim on 2/17/17 at 7:24 PM.
 */
public class Compiler {
    private ArrayList<Expression> exps;
    private ArrayList<Operation> operations = new ArrayList<>();

    private Compiler(ArrayList<Expression> exps) {
        this.exps = exps;
    }

    public static ArrayList<Operation> compileFunction(ArrayList<Expression> tokens) {
        Compiler compiler = new Compiler(tokens);
        compiler._compileFunction();
        return compiler.operations;
    }

    private void _compileFunction() {
        for (int i = 0; i < exps.size(); i++) {
            Expression exp = exps.get(i);
            compileExpression(exp, 0);
        }
    }

    private void compileExpression(Expression exp, int depth) {
        GaledwellLang.log("compiling: " + exp.toString());

        //check if this is function call
        if (exp instanceof ExpressionFunctionCall) {
            GaledwellLang.log("compiling: function call");

            ExpressionFunctionCall call = (ExpressionFunctionCall) exp;

            for (int j = call.args.size() - 1; j >= 0; j--) {
                Expression arg = call.args.get(j);
                compileExpression(arg, 0);
            }

            compilePathToVar(call.functionName);
            operations.add(new OperationCall(call.args.size()));
            if (depth == 0)
                operations.add(new OperationPop()); //pop return value of function if it won't be used
        } else if (exp instanceof ExpressionReturn) {
            GaledwellLang.log("compiling: return");

            compileExpression(((ExpressionReturn) exp).returnExp, depth + 1);
            operations.add(new OperationReturn());
        } else if (exp instanceof ExpressionValue) {
            GaledwellLang.log("compiling: value");

            ExpressionValue val = (ExpressionValue) exp;
            if (val.value instanceof ValueReference) {
                compilePathToVar(((ValueReference) val.value).path);
            } else
                operations.add(new OperationPush(val.value));
        } else if (exp instanceof ExpressionTree) {
            GaledwellLang.log("compiling: tree");

            ExpressionTree tree = ((ExpressionTree) exp);

            if (tree.operator.type == TokenType.OPERATOR) {
                compileExpression(tree.right, depth + 1);
                compileExpression(tree.left, depth + 1);

                switch (tree.operator.token) {
                    case "+":
                        operations.add(new OperationAdd());
                        break;
                    case "-":
                        operations.add(new OperationSub());
                        break;
                    case "*":
                        operations.add(new OperationMul());
                        break;
                    case "/":
                        operations.add(new OperationDiv());
                        break;
                    case "=":
                        operations.add(new OperationAssign());
                        if (depth == 0)
                            operations.add(new OperationPop()); //pop value if it won't be used
                        break;
                }

            }
        }
    }

    private void compilePathToVar(String path) {
        operations.add(new OperationPushVariableStorage());
        String[] tokens = path.split("\\.");
        for (int i = 0; i < tokens.length; i++) {
            if (i == tokens.length - 1) {
                operations.add(new OperationPush(new ValueReference(tokens[i].hashCode())));
            } else {
                operations.add(new OperationGetAndPush(tokens[i].hashCode()));
            }
        }
    }
}
