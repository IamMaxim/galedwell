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
            case SCOPE:
                return t.equals("(") ||
                        t.equals(")") ||
                        t.equals("{") ||
                        t.equals("}");
            case OPERATOR:
                return t.equals("==") ||
                        t.equals("+") ||
                        t.equals("-") ||
                        t.equals("*") ||
                        t.equals("/") ||
                        t.equals("=") ||
/*                        t.equals("++") ||
                        t.equals("--") ||*/
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
                        t.equals("else");
            case IDENTIFIER:
                return true;
            default:
                throw new InvalidTokenException("Token.is() is not implemented for type " + type.toString());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Token))
            return false;
        Token t = (Token) obj;
        return t.type == type && t.token.equals(token);
    }

    public boolean empty() {
        return token.isEmpty();
    }

    @Override
    public String toString() {
        return "{\"type\": \"" + type + "\", \"token\": \"" + token + "\"}";
    }

    public String toShortString() {
        return token;
    }

    public static TokenType getType(String token) throws InvalidTokenException {
        if (is(TokenType.SCOPE, token))
            return TokenType.SCOPE;
        else if (is(TokenType.OPERATOR, token))
            return TokenType.OPERATOR;
        else if (is(TokenType.DELIMITER, token))
            return TokenType.DELIMITER;
        else if (is(TokenType.KEYWORD, token))
            return TokenType.KEYWORD;
        else
            return TokenType.IDENTIFIER;
    }
}
