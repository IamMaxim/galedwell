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

    public static ArrayList<Operation> compileFunction(ArrayList<Expression> tokens) throws InvalidTokenException, InvalidExpressionException {
        Compiler compiler = new Compiler(tokens);
        compiler._compileFunction();
        return compiler.operations;
    }

    private void addOperation(Operation op) {
        GaledwellLang.log("adding op: " + op);
        operations.add(op);
    }

    private void addOperation(int index, Operation op) {
        GaledwellLang.log("adding op: " + op);
        operations.add(index, op);
    }

    private void removeOperation(int index) {
        GaledwellLang.log("removing op: " + operations.get(index));
        operations.remove(index);
    }

    private void _compileFunction() throws InvalidTokenException, InvalidExpressionException {
        for (Expression exp : exps) {
            compileExpression(exp, 0, false);
        }

        //process labels
        for (int i = 0; i < operations.size(); i++) {
            Operation op = operations.get(i);
            if (op instanceof OperationLabel) {
                ((OperationLabel) op).index = i;
                operations.remove(i);
                i--; //repeat this index
            }
        }
    }

    private void compileExpression(Expression exp, int depth, boolean inTree) throws InvalidTokenException, InvalidExpressionException {
        GaledwellLang.logger.increateIndent();
        GaledwellLang.log("compiling " + exp.getClass().getSimpleName() + ": " + exp.toString());
        GaledwellLang.logger.increateIndent();

        if (exp instanceof ExpressionFunctionCall) {
            compileFunctionCall((ExpressionFunctionCall) exp, depth, inTree);
        } else if (exp instanceof ExpressionReturn) {
            compileReturn((ExpressionReturn) exp, depth, inTree);
        } else if (exp instanceof ExpressionValue) {
            compileValue((ExpressionValue) exp, depth, inTree);
        } else if (exp instanceof ExpressionTree) {
            compileTree((ExpressionTree) exp, depth, inTree);
        } else if (exp instanceof ExpressionCondition) {
            compileCondition((ExpressionCondition) exp, depth, inTree);
        } else if (exp instanceof ExpressionForLoop) {
            compileForLoop((ExpressionForLoop) exp, depth, inTree);
        } else throw new InvalidExpressionException("Can't find compiler part for " + exp.getClass().getName());
        GaledwellLang.logger.decreaseIndent();
        GaledwellLang.logger.decreaseIndent();
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

    private void compileFunctionCall(ExpressionFunctionCall exp, int depth, boolean inTree) throws InvalidTokenException, InvalidExpressionException {
        for (int j = exp.args.size() - 1; j >= 0; j--) {
            Expression arg = exp.args.get(j);
            compileExpression(arg, depth + 1, false);
        }

        compilePathToVar(exp.functionName);
        addOperation(new OperationCall(exp.args.size()));
        if (depth == 0)
            addOperation(new OperationPop()); //pop return value of function if it won't be used
    }

    private void compileReturn(ExpressionReturn exp, int depth, boolean inTree) throws InvalidTokenException, InvalidExpressionException {
        compileExpression(exp.returnExp, depth + 1, false);
        addOperation(new OperationReturn());
    }

    private void compileValue(ExpressionValue exp, int depth, boolean inTree) throws InvalidTokenException {
        if (exp.value instanceof ValueReference) { //if this is reference, push parent and field index, so value can be got during execution
            compilePathToVar(((ValueReference) exp.value).path);
            if (inTree)
                convertLastReferenceToValue();
        } else
            addOperation(new OperationPush(exp.value)); //this is constant, just push it every time
    }

    private void compileTree(ExpressionTree exp, int depth, boolean inTree) throws InvalidTokenException, InvalidExpressionException {
        if (exp.operator.type == TokenType.OPERATOR) {
            if (exp.operator.equals(new Token("+"))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationAdd());
            } else if (exp.operator.equals(new Token("-"))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationSub());
            } else if (exp.operator.equals(new Token("*"))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationMul());
            } else if (exp.operator.equals(new Token("/"))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationDiv());
            } else if (exp.operator.equals(new Token("<"))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationLess());
            } else if (exp.operator.equals(new Token("<="))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationLessEquals());
            } else if (exp.operator.equals(new Token("=="))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationEquals());
            } else if (exp.operator.equals(new Token(">="))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationMoreEquals());
            } else if (exp.operator.equals(new Token(">"))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationMore());
            } else if (exp.operator.equals(new Token("="))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, false);
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
            } else if (exp.operator.equals(new Token("++"))) {
                //check if right side is not empty
                if (!exp.right.toString().equals("null"))
                    throw new InvalidTokenException("The '++' operator shouldn't have right side, but got '" + exp.right.toString() + "'");

                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationIncrement());
            } else if (exp.operator.equals(new Token("--"))) {
                //check if right side is not empty
                if (!exp.right.toString().equals("null"))
                    throw new InvalidTokenException("The '--' operator shouldn't have right side, but got '" + exp.right.toString() + "'");

                compileExpression(exp.left, depth + 1, true);
                addOperation(new OperationDecrement());
            } else if (exp.operator.equals(new Token("-="))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                // check if left side is reference
                Operation leftOP;
                if (!((leftOP = operations.get(operations.size() - 1)) instanceof OperationGetAndPush))
                    throw new InvalidTokenException("Left side of -= should be reference");
                addOperation(new OperationSub());
                addOperation(new OperationPushVariableStorage());
                addOperation(new OperationPush(new ValueReference(((OperationGetAndPush) leftOP).id)));
                addOperation(new OperationAssign());
            } else if (exp.operator.equals(new Token("+="))) {
                compileExpression(exp.right, depth + 1, true);
                compileExpression(exp.left, depth + 1, true);
                // check if left side is reference
                Operation leftOP;
                if (!((leftOP = operations.get(operations.size() - 1)) instanceof OperationGetAndPush))
                    throw new InvalidTokenException( "Left side of += should be reference");
                addOperation(new OperationAdd());
                addOperation(new OperationPushVariableStorage());
                addOperation(new OperationPush(new ValueReference(((OperationGetAndPush) leftOP).id)));
                addOperation(new OperationAssign());
            } else {
                throw new InvalidTokenException("Invalid operator '" + exp.operator.token + "'");
            }
        }
    }

    private void compileCondition(ExpressionCondition exp, int depth, boolean inTree) throws InvalidTokenException, InvalidExpressionException {
        OperationLabel ifNotLabel = new OperationLabel(), endIfLabel = null;
        boolean elseExists = false; //true if else block exists
        if (!exp.elseBody.isEmpty()) {
            elseExists = true;
            endIfLabel = new OperationLabel();
        }

        compileExpression(exp.cond, depth + 1, false); //compile condition
        addOperation(new OperationIf(ifNotLabel));

        for (Expression expression : exp.body) {
            compileExpression(expression, 0, false);
        }

        if (elseExists) //skip else block from if block
            addOperation(new OperationGoTo(endIfLabel));

        addOperation(ifNotLabel); //add label after body

        if (elseExists) {
            for (Expression expression : exp.elseBody) {
                compileExpression(expression, depth + 1, false);
            }
            addOperation(endIfLabel);
        }
    }

    private void compileForLoop(ExpressionForLoop exp, int depth, boolean inTree) throws InvalidTokenException, InvalidExpressionException {
        OperationLabel begin = new OperationLabel();
        OperationLabel end = new OperationLabel();

        compileExpression(exp.first, depth, false);
        addOperation(begin);
        compileExpression(exp.second, depth, true);
        addOperation(new OperationIf(end));
        for (Expression e : exp.body) {
            compileExpression(e, depth + 1, false);
        }
        compileExpression(exp.third, depth, false);
        addOperation(new OperationGoTo(begin));
        addOperation(end);
    }
}
