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

    private void addOperation(Operation op) {
        GaledwellLang.log("        adding op: " + op);
        operations.add(op);
    }

    private void addOperation(int index, Operation op) {
        GaledwellLang.log("        adding op: " + op);
        operations.add(index, op);
    }

    private void removeOperation(int index) {
        GaledwellLang.log("        removing op: " + operations.get(index));
        operations.remove(index);
    }

    private void _compileFunction() throws InvalidTokenException {
        for (int i = 0; i < exps.size(); i++) {
            Expression exp = exps.get(i);

            GaledwellLang.log("compiling: " + exp);

            compileExpression(exp, 0, false);
        }

//        System.out.println("processing labels...");
        //process labels
        for (int i = 0; i < operations.size(); i++) {
            Operation op = operations.get(i);
            if (op instanceof OperationLabel) {
//                System.out.println("found label at " + i);
                ((OperationLabel) op).index = i;
//                System.out.println("changed index to " + i);
                operations.remove(i);
                i--; //repeat this index
            }
        }
    }

    private void compileExpression(Expression exp, int depth, boolean inTree) throws InvalidTokenException {
        GaledwellLang.log("    compiling: " + exp.toString());

        //check if this is function call
        if (exp instanceof ExpressionFunctionCall) {
            ExpressionFunctionCall call = (ExpressionFunctionCall) exp;

            for (int j = call.args.size() - 1; j >= 0; j--) {
                Expression arg = call.args.get(j);
                compileExpression(arg, depth + 1, false);
            }

            compilePathToVar(call.functionName);
            addOperation(new OperationCall(call.args.size()));
            if (depth == 0)
                addOperation(new OperationPop()); //pop return value of function if it won't be used
        } else if (exp instanceof ExpressionReturn) { //check if this is return statement
            compileExpression(((ExpressionReturn) exp).returnExp, depth + 1, false);
            addOperation(new OperationReturn());
        } else if (exp instanceof ExpressionValue) { //check if this is value expression
            ExpressionValue val = (ExpressionValue) exp;
            if (val.value instanceof ValueReference) { //if this is reference, push parent and field index, so value can be got during execution
                compilePathToVar(((ValueReference) val.value).path);
                if (inTree)
                    convertLastReferenceToValue();
            } else
                addOperation(new OperationPush(val.value)); //this is constant, just push it every time

        } else if (exp instanceof ExpressionTree) { //check if this is tree expression (left + operator + right)
            ExpressionTree tree = ((ExpressionTree) exp);

            if (tree.operator.type == TokenType.OPERATOR) {
                if (tree.operator.equals(new Token("+"))) {
                    compileExpression(tree.right, depth + 1, true);
                    compileExpression(tree.left, depth + 1, true);
                    addOperation(new OperationAdd());
                } else if (tree.operator.equals(new Token("-"))) {
                    compileExpression(tree.right, depth + 1, true);
                    compileExpression(tree.left, depth + 1, true);
                    addOperation(new OperationSub());
                } else if (tree.operator.equals(new Token("*"))) {
                    compileExpression(tree.right, depth + 1, true);
                    compileExpression(tree.left, depth + 1, true);
                    addOperation(new OperationMul());
                } else if (tree.operator.equals(new Token("/"))) {
                    compileExpression(tree.right, depth + 1, true);
                    compileExpression(tree.left, depth + 1, true);
                    addOperation(new OperationDiv());
                } else if (tree.operator.equals(new Token("<"))) {
                    compileExpression(tree.right, depth + 1, true);
                    compileExpression(tree.left, depth + 1, true);
                    addOperation(new OperationLess());
                } else if (tree.operator.equals(new Token("<="))) {
                    compileExpression(tree.right, depth + 1, true);
                    compileExpression(tree.left, depth + 1, true);
                    addOperation(new OperationLessEquals());
                } else if (tree.operator.equals(new Token("=="))) {
                    compileExpression(tree.right, depth + 1, true);
                    compileExpression(tree.left, depth + 1, true);
                    addOperation(new OperationEquals());
                } else if (tree.operator.equals(new Token(">="))) {
                    compileExpression(tree.right, depth + 1, true);
                    compileExpression(tree.left, depth + 1, true);
                    addOperation(new OperationMoreEquals());
                } else if (tree.operator.equals(new Token(">"))) {
                    compileExpression(tree.right, depth + 1, true);
                    compileExpression(tree.left, depth + 1, true);
                    addOperation(new OperationMore());
                } else if (tree.operator.equals(new Token("="))) {
                    compileExpression(tree.right, depth + 1, true);
                    compileExpression(tree.left, depth + 1, false);
                    //check if we assigning a reference: if so, replace it with value
                    {
                        Operation op = operations.get(operations.size() - 3);
                        if (op instanceof OperationPush)
                            if (((OperationPush) op).value instanceof ValueReference) {
                                GaledwellLang.log("replacing reference with value in assignment");

                                removeOperation(operations.size() - 3);
                                addOperation(operations.size() - 2, new OperationGetAndPush(((ValueReference) ((OperationPush) op).value).id));
                            }
                    }

                    addOperation(new OperationAssign());
                    if (depth == 0)
                        addOperation(new OperationPop()); //pop value if it won't be used
                }
            }
        } else if (exp instanceof ExpressionCondition) {
            ExpressionCondition cond = (ExpressionCondition) exp;
            OperationLabel ifNotLabel = new OperationLabel(), endIfLabel = null;
            boolean elseExists = false; //true if else block exists
            if (!cond.elseBody.isEmpty()) {
                elseExists = true;
                endIfLabel = new OperationLabel();
            }

            compileExpression(cond.cond, depth + 1, false); //compile condition
            addOperation(new OperationIf(ifNotLabel));

            for (Expression expression : cond.body) {
                compileExpression(expression, 0, false);
            }

            if (elseExists) //skip else block from if block
                addOperation(new OperationGoTo(endIfLabel));

            addOperation(ifNotLabel); //add label after body

            if (elseExists) {
                for (Expression expression : cond.elseBody) {
                    compileExpression(expression, depth + 1, false);
                }
                addOperation(endIfLabel);
            }
        }
    }

    private void convertLastReferenceToValue() {
        GaledwellLang.log("current ops: " + operations);

        if (!(operations.get(operations.size() - 1) instanceof OperationCall)) { //if this is call, it pushes its value, not reference
            OperationPush v1 = (OperationPush) operations.get(operations.size() - 1); //get last push operation
            if (v1.value instanceof ValueReference) { //check if it pushes reference to variable
                GaledwellLang.log("replacing last reference with value");

                removeOperation(operations.size() - 1); //remove this push
                addOperation(new OperationGetAndPush(((ValueReference) v1.value).id)); //replace it with getAndPush, so reference's value is pushed to stack
            }
        }
    }

    private void compilePathToVar(String path) {
//        GaledwellLang.log("compiling path to var");

        addOperation(new OperationPushVariableStorage());
        String[] tokens = path.split("\\.");
        for (int i = 0; i < tokens.length; i++) {
            CompilerDebugRuntime.addName(tokens[i].hashCode(), tokens[i]);

            if (i == tokens.length - 1) {
                addOperation(new OperationPush(new ValueReference(tokens[i].hashCode())));
            } else {
                addOperation(new OperationGetAndPush(tokens[i].hashCode()));
            }
        }
    }
}
