package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;

/**
 * Created by maxim on 2/12/17 at 2:28 PM.
 */
public class Token {
    public TokenType type = TokenType.IDENTIFIER;
    public String token = "";

    public Token() {
    }

    public Token(TokenType type, String token) {
        this.type = type;
        this.token = token;
    }

    public Token(String t) throws InvalidTokenException {
        this.token = t;
        this.type = getType(t);
    }

    public static boolean is(TokenType type, String t) throws InvalidTokenException {
        switch (type) {
            case BRACE_OPEN:
                return t.equals("{");
            case PAREN_OPEN:
                return t.equals("(");
            case PAREN_CLOSE:
                return t.equals(")");
            case BRACE_CLOSE:
                return t.equals("}");
            case BRACKET_OPEN:
                return t.equals("[");
            case BRACKET_CLOSE:
                return t.equals("]");
            case OPERATOR:
                return t.equals("==") ||
                        t.equals("+") ||
                        t.equals("-") ||
                        t.equals("*") ||
                        t.equals("/") ||
                        t.equals("=") ||
                        t.equals("++") ||
                        t.equals("--") ||
                        t.equals("-=") ||
                        t.equals("+=") ||
                        t.equals("<") ||
                        t.equals("<=") ||
                        t.equals(">=") ||
                        t.equals(">") ||
                        t.equals("!=");
            case DELIMITER:
                return t.equals(";") ||
                        t.equals(",");
            case KEYWORD:
                return t.equals("global") ||
                        t.equals("if") ||
                        t.equals("return") ||
                        t.equals("else") ||
                        t.equals("for") ||
                        t.equals("function");
            case IDENTIFIER:
                return true;
            default:
                throw new InvalidTokenException("Token.is() is not implemented for type " + type.toString());
        }
    }

    public static TokenType getType(String token) throws InvalidTokenException {
        if (is(TokenType.BRACE_OPEN, token))
            return TokenType.BRACE_OPEN;
        else if (is(TokenType.BRACE_CLOSE, token))
            return TokenType.BRACE_CLOSE;
        else if (is(TokenType.PAREN_OPEN, token))
            return TokenType.PAREN_OPEN;
        else if (is(TokenType.PAREN_CLOSE, token))
            return TokenType.PAREN_CLOSE;
        else if (is(TokenType.BRACKET_OPEN, token))
            return TokenType.BRACKET_OPEN;
        else if (is(TokenType.BRACKET_CLOSE, token))
            return TokenType.BRACKET_CLOSE;
        else if (is(TokenType.OPERATOR, token))
            return TokenType.OPERATOR;
        else if (is(TokenType.DELIMITER, token))
            return TokenType.DELIMITER;
        else if (is(TokenType.KEYWORD, token))
            return TokenType.KEYWORD;
        else
            return TokenType.IDENTIFIER;
    }

    public boolean eq(String s) throws InvalidTokenException {
        Token t = new Token(s);
        return t.type == type && t.token.equals(token);
    }

    public boolean eq(Token t) {
        return t.type == type && t.token.equals(token);
    }

    public boolean empty() {
        return token.isEmpty();
    }

    @Override
    public String toString() {
//        return "{\"type\": \"" + type + "\", \"token\": \"" + token + "\"}";
        return "\'" + token + "\'";
    }

    public String toShortString() {
        return token;
    }
}
