package ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.GaledwellLang;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.*;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression.*;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.InvalidTokenException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Token;
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

    public static ArrayList<Operation> compileFunction(ArrayList<Expression> tokens) throws InvalidTokenException {
        Compiler compiler = new Compiler(tokens);
        compiler._compileFunction();
        return compiler.operations;
    }

    private void _compileFunction() throws InvalidTokenException {
        for (int i = 0; i < exps.size(); i++) {
            Expression exp = exps.get(i);
            compileExpression(exp, 0);
        }

        System.out.println("processing labels...");
        //process labels
        for (int i = 0; i < operations.size(); i++) {
            Operation op = operations.get(i);
            if (op instanceof OperationLabel) {
                System.out.println("found label at " + i);
                ((OperationLabel) op).index = i;
                System.out.println("changed index to " + i);
                operations.remove(i);
                i--; //repeat this index
            }
        }
    }

    private void compileExpression(Expression exp, int depth) throws InvalidTokenException {
        GaledwellLang.log("compiling: " + exp.toString());

        //check if this is function call
        if (exp instanceof ExpressionFunctionCall) {
            GaledwellLang.log("compiling function call");

            ExpressionFunctionCall call = (ExpressionFunctionCall) exp;

            for (int j = call.args.size() - 1; j >= 0; j--) {
                Expression arg = call.args.get(j);
                compileExpression(arg, depth + 1);
            }

            compilePathToVar(call.functionName);
            operations.add(new OperationCall(call.args.size()));
            if (depth == 0)
                operations.add(new OperationPop()); //pop return value of function if it won't be used

            GaledwellLang.log("compiled function call");
        } else if (exp instanceof ExpressionReturn) { //check if this is return statement
            GaledwellLang.log("compiling return");

            compileExpression(((ExpressionReturn) exp).returnExp, depth + 1);
            operations.add(new OperationReturn());

            GaledwellLang.log("compiled return");
        } else if (exp instanceof ExpressionValue) { //check if this is value expression
            GaledwellLang.log("compiling value");

            ExpressionValue val = (ExpressionValue) exp;
            if (val.value instanceof ValueReference) { //if this is reference, push parent and field index, so value can be got during execution
                compilePathToVar(((ValueReference) val.value).path);
            } else
                operations.add(new OperationPush(val.value)); //this is constant, just push it every time

            GaledwellLang.log("compiled value");
        } else if (exp instanceof ExpressionTree) { //check if this is tree expression (left + operator + right)
            GaledwellLang.log("compiling tree");

            ExpressionTree tree = ((ExpressionTree) exp);

            if (tree.operator.type == TokenType.OPERATOR) {
                compileExpression(tree.right, depth + 1);
                compileExpression(tree.left, depth + 1);

                if (tree.operator.equals(new Token("+"))) {
                    convertLastReferencesToValues();
                    operations.add(new OperationAdd());
                } else if (tree.operator.equals(new Token("-"))) {
                    convertLastReferencesToValues();
                    operations.add(new OperationSub());
                } else if (tree.operator.equals(new Token("*"))) {
                    convertLastReferencesToValues();
                    operations.add(new OperationMul());
                } else if (tree.operator.equals(new Token("/"))) {
                    convertLastReferencesToValues();
                    operations.add(new OperationDiv());
                } else if (tree.operator.equals(new Token("<"))) {
                    convertLastReferencesToValues();
                    operations.add(new OperationLess());
                } else if (tree.operator.equals(new Token("<="))) {
                    convertLastReferencesToValues();
                    operations.add(new OperationLessEquals());
                } else if (tree.operator.equals(new Token("=="))) {
                    convertLastReferencesToValues();
                    operations.add(new OperationEquals());
                } else if (tree.operator.equals(new Token(">="))) {
                    convertLastReferencesToValues();
                    operations.add(new OperationMoreEquals());
                } else if (tree.operator.equals(new Token(">"))) {
                    convertLastReferencesToValues();
                    operations.add(new OperationMore());
                } else if (tree.operator.equals(new Token("="))) {
                    operations.add(new OperationAssign());
                    if (depth == 0)
                        operations.add(new OperationPop()); //pop value if it won't be used
                }
            }
            GaledwellLang.log("compiled tree");
        } else if (exp instanceof ExpressionCondition) {
            GaledwellLang.log("compiling condition");

            ExpressionCondition cond = (ExpressionCondition) exp;
            OperationLabel ifNotLabel = new OperationLabel(), endIfLabel = null;
            boolean elseExists = false; //true if else block exists
            if (!cond.elseBody.isEmpty()) {
                elseExists = true;
                endIfLabel = new OperationLabel();
            }

            compileExpression(cond.cond, depth + 1); //compile condition
            operations.add(new OperationIf(ifNotLabel));

            for (Expression expression : cond.body) {
                compileExpression(expression, depth + 1);
            }

            if (elseExists) //skip else block from if block
                operations.add(new OperationGoTo(endIfLabel));

            operations.add(ifNotLabel); //add label after body

            if (elseExists) {
                for (Expression expression : cond.elseBody) {
                    compileExpression(expression, depth + 1);
                }
                operations.add(endIfLabel);
            }

        GaledwellLang.log("compiled condition");
    }
}

    private void convertLastReferencesToValues() {
        int lastOpOffset = 1, preLastOpOffset = 2;
        if (!(operations.get(operations.size() - lastOpOffset) instanceof OperationCall)) { //if this is call, it pushes its value, not reference
            OperationPush v1 = (OperationPush) operations.get(operations.size() - lastOpOffset); //get last push operation
            if (v1.value instanceof ValueReference) { //check if it pushes reference to variable
                operations.remove(operations.size() - lastOpOffset); //remove this push
                operations.add(new OperationGetAndPush(((ValueReference) v1.value).id)); //replace it with getAndPush, so reference's value is pushed to stack

                preLastOpOffset = 3;
            } else {
                preLastOpOffset = 2;
            }
        }

        if (!(operations.get(operations.size() - preLastOpOffset) instanceof OperationCall)) { //if this is call, it pushes its value, not reference
            OperationPush v2 = (OperationPush) operations.get(operations.size() - preLastOpOffset); //get pre-last push operation
            if (v2.value instanceof ValueReference) { //check if it pushes reference to variable
                operations.remove(operations.size() - preLastOpOffset); //remove this push
                operations.add(operations.size() - preLastOpOffset, new OperationGetAndPush(((ValueReference) v2.value).id)); //replace it with getAndPush, so reference's value is pushed to stack
            }
        }
    }

    private void compilePathToVar(String path) {
        GaledwellLang.log("compiling path to var");

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
