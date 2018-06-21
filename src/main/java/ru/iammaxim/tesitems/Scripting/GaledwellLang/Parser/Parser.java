package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.CompilerDebugRuntime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.GaledwellLang;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression.*;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;

import java.util.ArrayList;

/**
 * Created by maxim on 2/12/17 at 3:03 PM.
 */
public class Parser {
    private Tokener tokener;

    public Parser(ArrayList<Token> tokens) {
        tokener = new Tokener(tokens);
    }

    private void assertToken(Tokener tokener, Token token) throws InvalidTokenException {
        if (!tokener.peek().eq(token))
            throw new InvalidTokenException("Expected " + token.token);
    }

    private void assertType(Tokener tokener, TokenType type) throws InvalidTokenException {
        if (tokener.peek().type != type)
            throw new InvalidTokenException("Expected " + type);
    }

    private Expression indentAndParseExpression(Tokener tokener) throws InvalidTokenException {
        GaledwellLang.logger.increateIndent();
        Expression exp = parseExpression(tokener);
        GaledwellLang.logger.decreaseIndent();
        return exp;
    }

    private ArrayList<Expression> indentAndParseExpressions(ArrayList<Tokener> tokeners) throws InvalidTokenException {
        GaledwellLang.logger.increateIndent();
        ArrayList<Expression> exps = parseExpressions(tokeners);
        GaledwellLang.logger.decreaseIndent();
        return exps;
    }

    private Expression parseExpression(Tokener tokener) throws InvalidTokenException {
        GaledwellLang.log("parsing " + tokener + " with line number: " + tokener.currentLineNumber);
        tokener.trimParentheses();

        // check if this is value
        if (tokener.size() == 1) {
            GaledwellLang.log("parsing value");
            Value val = Value.get(tokener.eat().token);
            return new ExpressionValue(val);
        }

        // check if this is return
        if (tokener.size() >= 2) {
            if (tokener.peek().eq("return")) {
                tokener.eat(); // eat return
                GaledwellLang.log("parsing return");
                return new ExpressionReturn(indentAndParseExpression(tokener.subtokener(1, tokener.size())));
            }
        }

        // check if this is condition
        if (tokener.left() > 0 && tokener.peek().eq("if")) {
            GaledwellLang.log("parsing condition");

            tokener.eat(); // eat if
            assertType(tokener, TokenType.SCOPE_PARENS);
            Tokener condition = new Tokener(((TokenScope) tokener.eat()).tokens, tokener.preEatenLineNumber);

            GaledwellLang.log("parsed condition: " + condition);

            ArrayList<Expression> bodyExps = new ArrayList<>();
            ArrayList<Tokener> bodyTokeners = readBody(tokener);

            GaledwellLang.log("parsed body: " + bodyTokeners);

            for (Tokener t : bodyTokeners) {
                bodyExps.add(indentAndParseExpression(t));
            }

            ArrayList<Expression> elseBodyExps = new ArrayList<>();
            if (tokener.left() >= 2 /* else, braces scope */ && tokener.peek().eq("else")) {
                tokener.eat(); // eat else

                ArrayList<Tokener> elseBodyTokeners;
                elseBodyTokeners = readBody(tokener);

                GaledwellLang.log("parsed elseBody: " + elseBodyTokeners);

                for (Tokener t : elseBodyTokeners) {
                    elseBodyExps.add(indentAndParseExpression(t));
                }

                // check situation when semicolon is not set after if(){}else{}
                Tokener nextExpr = tokener.readTo(new Token(";"));
                if (!nextExpr.isEmpty())
                    throw new InvalidTokenException("Expected ';' after 'if () {} else {}'");
            } else {
                // check situation when semicolon is not set after if(){}
                Tokener nextExpr = tokener.readTo(new Token(";"));
                if (!nextExpr.isEmpty())
                    throw new InvalidTokenException("Expected ';' after 'if () {}'");
            }

            return new ExpressionCondition(
                    indentAndParseExpression(condition),
                    bodyExps,
                    elseBodyExps);
        }

        // check if this is for loop
        if (tokener.left() > 0 && tokener.peek().eq("for")) {
            GaledwellLang.log("parsing for loop");
            tokener.eat(); //eat 'for'
            assertType(tokener, TokenType.SCOPE_PARENS);
            Tokener args = new Tokener(((TokenScope) tokener.eat()).tokens, tokener.preEatenLineNumber);
            ArrayList<Tokener> argTokens = args.splitSkippingScopes(new Token(";"));
            GaledwellLang.log("for loop args: " + argTokens);
            if (argTokens.size() != 3)
                throw new InvalidTokenException("Expected 3 args in 'for' construction, but got " + argTokens.size());
            ArrayList<Tokener> body = readBody(tokener);

            // check situation when semicolon is not set after for(){}
            Tokener nextExpr = tokener.readTo(new Token(";"));
            if (!nextExpr.isEmpty())
                throw new InvalidTokenException("Expected ';' after 'for () {}'");

            return new ExpressionForLoop(
                    indentAndParseExpression(argTokens.get(0)),
                    indentAndParseExpression(argTokens.get(1)),
                    indentAndParseExpression(argTokens.get(2)),
                    indentAndParseExpressions(body));
        }

        int level = 0;
        Token t, highest = null;
        int highestPriorityIndex = -1;
        for (int i = 0; tokener.left() > 0; i++) {
            t = tokener.eat();
            if (t.eq("(")) {
                level++;
            } else if (t.eq(")")) {
                level--;
            }
            if (level == 0) {
                if (t.type == TokenType.OPERATOR) {
                    if (highest == null) {
                        highest = t;
                        highestPriorityIndex = i;
                    } else {
                        if (isOrderHigher(highest, t)) {
                            highest = t;
                            highestPriorityIndex = i;
                        }
                    }
                }
            }
        }

        if (highest == null) {
            // check if this is function call
            tokener.index = 0;
            while (tokener.left() > 0) {
                t = tokener.eat();
                if (t.type == TokenType.IDENTIFIER && tokener.hasNext() && tokener.peekNext().type == TokenType.SCOPE_PARENS) {
                    GaledwellLang.log("parsing function call");
                    // check if this is function declaration
                    if (tokener.left() >= 2) {
                        if (tokener.peekNextNext().type == TokenType.SCOPE_BRACES) {
                            throw new InvalidTokenException("Line " + tokener.preEatenLineNumber + ": Unexpected function declaration");
                        }
                    }
                    int index = tokener.index;
                    Tokener argsTokener = new Tokener(((TokenScope) tokener.peekNext()).tokens, tokener.prePeekedLineNumber);
                    ArrayList<Tokener> args = argsTokener.splitSkippingScopes(new Token(","));
                    tokener.index = index;

                    return new ExpressionFunctionCall(
                            t,
                            indentAndParseExpressions(args));
                }

                if (t.type == TokenType.IDENTIFIER && tokener.hasNext() && tokener.peekNext().type == TokenType.SCOPE_BRACKETS) {
                    GaledwellLang.log("parsing value at");
                    Tokener argTokener = new Tokener(((TokenScope) tokener.peekNext()).tokens, tokener.prePeekedLineNumber);

                    ArrayList<Tokener> list = new ArrayList<>();
                    list.add(argTokener);
                    return new ExpressionValueAt(t.token,
                            (ExpressionValue) indentAndParseExpressions(list).get(0));
                }
            }

            if (tokener.isEmpty())
                return new ExpressionTree();
            return new ExpressionTree(tokener.tokens.get(0));
        }

        return new ExpressionTree(highest,
                indentAndParseExpression(tokener.subtokener(0, highestPriorityIndex)),
                indentAndParseExpression(tokener.subtokener(highestPriorityIndex + 1, tokener.size())));
    }

    private ArrayList<Tokener> readBody(Tokener tokener) throws InvalidTokenException {
        Tokener body;
        ArrayList<Tokener> BodyTokeners;
        if (tokener.peek().type != TokenType.SCOPE_BRACES) { // no braces; read 1 statement
            body = tokener.readTo(new Token(";"));
            BodyTokeners = new ArrayList<>();
            BodyTokeners.add(body);
        } else {
            body = new Tokener(((TokenScope) tokener.eat()).tokens, tokener.preEatenLineNumber);
            BodyTokeners = body.splitSkippingScopes(new Token(";"));
        }
        return BodyTokeners;
    }

    /**
     * @param first  first token
     * @param second second token
     * @return true if second's order is higher than first's
     * @throws InvalidTokenException if one of operators unknown
     */
    private boolean isOrderHigher(Token first, Token second) throws InvalidTokenException {
        return getOrder(first) < getOrder(second);
    }

    /**
     * @param t token to check
     * @return operator's order
     * @throws InvalidTokenException if operator unknown
     */
    private int getOrder(Token t) throws InvalidTokenException {
        int level;
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
                throw new InvalidTokenException("Expected operator, but got " + t.token);
        }

        return level;
    }

    private Token eat() {
        return tokener.eat();
    }

    public ArrayList<ParsedFunction> parse() throws InvalidTokenException {
        ArrayList<ParsedFunction> functions = new ArrayList<>();
        while (tokener.left() > 0) { // while there are tokens left
            Token token = eat();

            // check function declaration
            if (token.eq("function")) {
                String functionName;
                int[] args;
                ArrayList<Expression> exps;

                Token functionNameToken = tokener.eat();
                if (functionNameToken.type != TokenType.IDENTIFIER)
                    throw new InvalidTokenException("Line " + tokener.preEatenLineNumber + ": Expected identifier as function name, got " + functionNameToken);
                functionName = functionNameToken.token;

                GaledwellLang.log("parsing function " + functionName);

                // read function args
                if (tokener.peek().type != TokenType.SCOPE_PARENS)
                    throw new InvalidTokenException("Expected parens scope as arguments");
                TokenScope argsToken = (TokenScope) tokener.eat();
                ArrayList<Tokener> argsTokeners = new Tokener(argsToken.tokens, tokener.preEatenLineNumber).splitSkippingScopes(new Token(","));

                GaledwellLang.log("parsed function args: " + argsTokeners);

                args = new int[argsTokeners.size()];
                for (int i = 0; i < argsTokeners.size(); i++) {
                    Tokener argTokener1 = argsTokeners.get(i);
                    if (argTokener1.size() > 1)
                        throw new InvalidTokenException("Expected 1 identifier, got " + argTokener1.size() + " while parsing argument");
                    if (argTokener1.size() > 0) {
                        CompilerDebugRuntime.addName(args[i], argTokener1.tokens.get(0).token);

                        args[i] = argTokener1.tokens.get(0).token.hashCode();
                    }
                }

                // read function body
                if (tokener.peek().type != TokenType.SCOPE_BRACES)
                    throw new InvalidTokenException("Expected braces scope as function body");
                Tokener body = new Tokener(((TokenScope) tokener.eat()).tokens, tokener.preEatenLineNumber);

                GaledwellLang.log("parsed function body: " + body);

                // process body
                exps = indentAndParseExpressions(body.splitSkippingScopes(new Token(";")));

                GaledwellLang.log("parsed expressions:");
                GaledwellLang.logger.increateIndent();
                exps.forEach(e -> GaledwellLang.log(e.toString()));
                GaledwellLang.logger.decreaseIndent();
                GaledwellLang.log("parsed expressions end");

                // parse function
                // TODO: remove if release mode is set
                CompilerDebugRuntime.addName(functionName.hashCode(), functionName);
                functions.add(new ParsedFunction(functionName.hashCode(), args, exps));
                continue;
            }

            // TODO: more root declarations here

            if (token.eq(";")) // skip processing of ";"
                continue;

            throw new InvalidTokenException("Couldn't find parsing block for " + token);
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
