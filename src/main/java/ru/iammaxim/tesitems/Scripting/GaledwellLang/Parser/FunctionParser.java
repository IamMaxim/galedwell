package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.CompilerDebugRuntime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.GaledwellLang;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression.*;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;

import java.util.ArrayList;

/**
 * Created by maxim on 2/12/17 at 3:03 PM.
 */
public class FunctionParser {
    private Tokener tokener;

    public FunctionParser(ArrayList<Token> tokens) {
        tokener = new Tokener(getLineNumber(tokens), tokens);
    }

    private Expression parseExpression(Tokener tokener) throws InvalidTokenException {
        GaledwellLang.log("parsing " + tokener);
        tokener.trimParentheses();

        //check if this is value
        if (tokener.size() == 1) {
            int ln = tokener.getLineNumber();
            Value val = Value.get(tokener.eat().token);
            if (val != null)
                return new ExpressionValue(ln, val);
        }

        //check if this is return
        if (tokener.size() >= 2) {
            if (tokener.get().equals(new Token("return"))) {
                tokener.eat(); // eat return
                GaledwellLang.log("parsing return");

                int ln = tokener.getLineNumber();
                return new ExpressionReturn(ln, parseExpression(tokener.subtokener(1, tokener.size())));
            }
        }

        //check if this is condition
        if (tokener.left() > 0 && tokener.get().equals(new Token("if"))) {
            GaledwellLang.log("parsing condition");

            tokener.eat(); //eat if
            if (!tokener.get().equals(new Token("(")))
                throw new InvalidTokenException(tokener.getLineNumber(), "Excepted (");
            Tokener condition = tokener.readToSkippingScopes(new Token(")"));

            GaledwellLang.log("parsed condition: " + condition);

            Tokener body;
            ArrayList<Expression> bodyExps = new ArrayList<>();
            ArrayList<Tokener> bodyTokeners;
            if (!tokener.get().equals(new Token("{"))) { //no braces; read 1 statement
                body = tokener.readTo(new Token(";"));
                bodyTokeners = new ArrayList<>();
                bodyTokeners.add(body);
            } else {
                body = tokener.readToSkippingScopes(new Token("}"));
                bodyTokeners = body.splitSkippingScopes(new Token(";"));
            }

            GaledwellLang.log("parsed body: " + bodyTokeners);

            for (Tokener t : bodyTokeners) {
                bodyExps.add(parseExpression(t));
            }

            ArrayList<Expression> elseBodyExps = new ArrayList<>();
            if (tokener.left() > 2 /* else, {, } */ && tokener.get().equals(new Token("else"))) {
                tokener.eat(); //eat else

                Tokener elseBody;
                ArrayList<Tokener> elseBodyTokeners;
                if (!tokener.get().equals(new Token("{"))) {
                    elseBody = tokener.readTo(new Token(";"));
                    elseBodyTokeners = new ArrayList<>();
                    elseBodyTokeners.add(elseBody);
                } else {
                    elseBody = tokener.readToSkippingScopes(new Token("}"));
                    elseBodyTokeners = elseBody.splitSkippingScopes(new Token(";"));
                }

                GaledwellLang.log("parsed elseBody: " + elseBodyTokeners);

                for (Tokener t : elseBodyTokeners) {
                    elseBodyExps.add(parseExpression(t));
                }

                //check situation when semicolon is not set after if(){}else{}
                Tokener nextExpr = tokener.readTo(new Token(";"));
                if (!nextExpr.isEmpty())
                    throw new InvalidTokenException(tokener.getLineNumber(), "Excepted ';'");
            } else {
                //check situation when semicolon is not set after if(){}
                Tokener nextExpr = tokener.readTo(new Token(";"));
                if (!nextExpr.isEmpty())
                    throw new InvalidTokenException(tokener.getLineNumber(), "Excepted ';'");
            }

            return new ExpressionCondition(tokener.getLineNumber(), parseExpression(condition), bodyExps, elseBodyExps);
        }

        //check if this is for loop
        if (tokener.left() > 0 && tokener.get().equals(new Token("for"))) {
            tokener.eat(); //eat 'for'
            if (!tokener.get().equals(new Token("(")))
                throw new InvalidTokenException(tokener.getLineNumber(), "Excepted '('");
            Tokener args = tokener.readToSkippingScopes(new Token(")"));
            ArrayList<Tokener> argTokens = args.splitSkippingScopes(new Token(";"));
            GaledwellLang.log("for loop args: " + argTokens);
            if (argTokens.size() != 3)
                throw new InvalidTokenException(tokener.getLineNumber(), "Excepted 3 args in for construction, but got " + argTokens.size());
            if (!tokener.eat().equals(new Token("{")))
                throw new InvalidTokenException(tokener.getLineNumber(), "Excepted '{'");
            Tokener body = tokener.readTo(new Token("}"));
            return new ExpressionForLoop(
                    tokener.getLineNumber(),
                    parseExpression(argTokens.get(0)),
                    parseExpression(argTokens.get(1)),
                    parseExpression(argTokens.get(2)),
                    parseExpressions(body.splitSkippingScopes(new Token(";"))));
        }

        int level = 0;
        Token t, highest = null;
        int highestPriorityIndex = -1;
        for (int i = 0; tokener.left() > 0; i++) {
            t = tokener.eat();
            if (t.equals(new Token("("))) {
                level++;
            } else if (t.equals(new Token(")"))) {
                level--;
            }
            if (level == 0) {
                if (t.type == TokenType.OPERATOR) {
                    if (highest == null) {
                        highest = t;
                        highestPriorityIndex = i;
                    } else {
                        if (isOrderHigher(tokener.getLineNumber(), highest, t)) {
                            highest = t;
                            highestPriorityIndex = i;
                        }
                    }
                }
            }
        }

        if (highest == null) {
            //check if this is function call
            tokener.index = 0;
            for (int i = 0; tokener.left() > 0; i++) {
                t = tokener.eat();
                if (t.equals(new Token("(")) && i > 0 && tokener.tokens.get(i - 1).type == TokenType.IDENTIFIER)
                    try {
                        int index = tokener.index;
                        Tokener argsTokener = tokener.readTo(new Token(")"));
                        ArrayList<Tokener> args = argsTokener.splitSkippingScopes(new Token(","));
                        tokener.index = index;
                        return new ExpressionFunctionCall(
                                tokener.getLineNumber(),
                                tokener.tokens.get(i - 1),
                                parseExpressions(args));
                    } catch (InvalidTokenException e) {
                        e.printStackTrace();
                    }
            }

            if (tokener.size() == 0)
                return new ExpressionTree();
            return new ExpressionTree(tokener.getLineNumber(), tokener.tokens.get(0));
        }

        return new ExpressionTree(tokener.getLineNumber(), highest,
                parseExpression(tokener.subtokener(0, highestPriorityIndex)),
                parseExpression(tokener.subtokener(highestPriorityIndex + 1, tokener.size())));
    }

    /**
     * @param first  first token
     * @param second second token
     * @return true if second's order is higher than first's
     * @throws InvalidTokenException if one of operators unknown
     */
    private boolean isOrderHigher(int lineNumber, Token first, Token second) throws InvalidTokenException {
        return getOrder(lineNumber, first) < getOrder(lineNumber, second);
    }

    /**
     * @param t token to check
     * @return operator's order
     * @throws InvalidTokenException if operator unknown
     */
    private int getOrder(int lineNumber, Token t) throws InvalidTokenException {
        int level = 0;
        switch (t.token) {
            case "*":
            case "/":
                level = 1;
                break;
            case "+":
            case "-":
                level = 2;
                break;
            case "++":
            case "--":
                level = 3;
                break;
            case "==":
                level = 4;
                break;
            case "=":
            case "+=":
            case "-=":
                level = 5;
                break;
            default:
                throw new InvalidTokenException(lineNumber, "Excepted operator, but got " + t.token);
        }

        return level;
    }

    public int getLineNumber(ArrayList<Token> tokens) {
        int line_number = 1;
        for (Token token : tokens)
            if (token.type == TokenType.NEW_LINE) {
                line_number = Integer.parseInt(token.token.substring(6));
                break;
            }
        return line_number;
    }

    private Token eat() {
        return tokener.eat();
    }

    public ArrayList<ParsedFunction> build() throws InvalidTokenException {
        ArrayList<ParsedFunction> functions = new ArrayList<>();

        while (tokener.left() > 0) { //while !EOF
            Token functionName;
            int[] args;
            ArrayList<Expression> exps;

            //read function path
            if ((functionName = eat()).type != TokenType.IDENTIFIER)
                throw new InvalidTokenException(tokener.getLineNumber(), "Excepted identifier while parsing function path");

            GaledwellLang.log("parsing function " + functionName.token);

            //read function args
            if (!tokener.get().equals(new Token("(")))
                throw new InvalidTokenException(tokener.getLineNumber(), "Excepted (");
            Tokener argsTokener = tokener.readToSkippingScopes(new Token(")"));
            ArrayList<Tokener> argsTokeners = argsTokener.splitSkippingScopes(new Token(","));

            GaledwellLang.log("parsed function args: " + argsTokeners);

            args = new int[argsTokeners.size()];
            for (int i = 0; i < argsTokeners.size(); i++) {
                Tokener argTokener1 = argsTokeners.get(i);
                if (argTokener1.size() > 1)
                    throw new InvalidTokenException(tokener.getLineNumber(), "Excepted 1 identifier, got " + argTokener1.size() + " while parsing argument");
                if (argTokener1.size() > 0) {
                    CompilerDebugRuntime.addName(args[i], argTokener1.tokens.get(0).token);

                    args[i] = argTokener1.tokens.get(0).token.hashCode();
                }
            }

            //read function body
            if (!tokener.get().equals(new Token("{")))
                throw new InvalidTokenException(tokener.getLineNumber(), "Excepted { while parsing function body");
            Tokener body = tokener.readToSkippingScopes(new Token("}"));

            GaledwellLang.log("parsed function body: " + body);

            //process body
            exps = parseExpressions(body.splitSkippingScopes(new Token(";")));

            GaledwellLang.log("parsed expressions:");
            exps.forEach(e -> GaledwellLang.log(e.toString()));
            GaledwellLang.log("parsed expressions end");

            //build function
            CompilerDebugRuntime.addName(functionName.token.hashCode(), functionName.token);
            functions.add(new ParsedFunction(functionName.token.hashCode(), args, exps));
        }

        return functions;
    }

    private ArrayList<Expression> parseExpressions(ArrayList<Tokener> tokeners) throws InvalidTokenException {
        ArrayList<Expression> exps = new ArrayList<>(tokeners.size());
        for (Tokener t : tokeners) {
            exps.add(parseExpression(t));
        }
        return exps;
    }
}
