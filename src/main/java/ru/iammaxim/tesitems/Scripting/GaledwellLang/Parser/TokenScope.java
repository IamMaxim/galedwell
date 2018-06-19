package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;

import java.util.ArrayList;

/**
 * Created by maxim on 10.03.2017.
 */
public class TokenScope extends Token {
    public ArrayList<Token> tokens;
    public Type _type;

    public TokenScope(Type type, ArrayList<Token> tokens) {
        this.type = (type == Type.BRACES ? TokenType.SCOPE_BRACES : type == Type.PARENS ? TokenType.SCOPE_PARENS : TokenType.SCOPE_BRACKETS);
        this._type = type;
        this.tokens = tokens;
    }

    enum Type {
        BRACES,
        PARENS,
        BRACKETS
    }

    @Override
    public String toString() {
        return (_type == Type.BRACES ? "b" : _type == Type.PARENS ? "p" : "br") + tokens.toString();
    }
}
