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

    public static Expression parseExpression(Tokener tokener) throws InvalidTokenException {
        GaledwellLang.log("parsing " + tokener);
        tokener.trimParentheses();

        //check if this is value
        if (tokener.size() == 1) {
            Value val = Value.get(tokener.get().token);
            if (val != null)
                return new ExpressionValue(tokener.getLineNumber(), val);
        }

        //check if this is return
        if (tokener.size() >= 2) {
            if (tokener.get().equals(new Token("return"))) {
                GaledwellLang.log("parsing return");
                return new ExpressionReturn(tokener.getLineNumber(), parseExpression(tokener.subtokener(1, tokener.size())));
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
            GaledwellLang.log("for loop args: " + args);
            ArrayList<Tokener> argTokens = args.splitSkippingScopes(new Token(";"));
            GaledwellLang.log("for loop arg tokens list: " + argTokens);
            if (argTokens.size() != 3)
                throw new InvalidTokenException(tokener.getLineNumber(), "Excepted 3 args in for construction, but got " + argTokens.size());
            if (!tokener.eat().equals(new Token("{")))
                throw new InvalidTokenException(tokener.getLineNumber(), "Excepted '{'");
            Tokener body = tokener.readTo(new Token("}"));
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
                        return new ExpressionFunctionCall(tokener.getLineNumber(), tokener.tokens.get(i - 1), args);
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
    private static boolean isOrderHigher(int lineNumber, Token first, Token second) throws InvalidTokenException {
        return getOrder(lineNumber, first) < getOrder(lineNumber, second);
    }

    /**
     * @param t token to check
     * @return operator's order
     * @throws InvalidTokenException if operator unknown
     */
    private static int getOrder(int lineNumber, Token t) throws InvalidTokenException {
        int level = 0;
        if (t.token.equals("*") || t.token.equals("/"))
            level = 1;
        else if (t.token.equals("+") || t.token.equals("-"))
            level = 2;
        else if (t.token.equals("++") || t.token.equals("--"))
            level = 3;
        else if (t.token.equals("=="))
            level = 4;
        else if (t.token.equals("=") || t.token.equals("+=") || t.token.equals("-="))
            level = 5;
        else throw new InvalidTokenException(lineNumber, "Excepted operator, but got " + t.token);

        return level;
    }

    public int getLineNumber(ArrayList<Token> tokens) {
        int line_number = 1;
        for (int i = 0; i < tokens.size(); i++)
            if (tokens.get(i).type == TokenType.NEW_LINE) {
                line_number = Integer.parseInt(tokens.get(i).token.substring(6));
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
            ArrayList<Expression> exps = new ArrayList<>();

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
            ArrayList<Tokener> bodyParts = body.splitSkippingScopes(new Token(";"));

            GaledwellLang.log("parsed statements:");
            bodyParts.forEach(s -> GaledwellLang.log(s.toString()));
            GaledwellLang.log("parsed statements end");

            for (Tokener statement : bodyParts) {
                //skip empty statements
                if (statement.isEmpty())
                    continue;
                Expression exp = parseExpression(statement);
                exps.add(exp);
            }

            //build function
            CompilerDebugRuntime.addName(functionName.token.hashCode(), functionName.token);
            functions.add(new ParsedFunction(functionName.token.hashCode(), args, exps));
        }

        return functions;
    }
}
