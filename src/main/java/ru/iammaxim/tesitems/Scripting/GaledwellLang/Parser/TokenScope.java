package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;

import java.util.ArrayList;

/**
 * Created by maxim on 10.03.2017.
 */
public class TokenScope extends Token {
    public ArrayList<Token> tokens;
    public Type type;

    public TokenScope(Type type, ArrayList<Token> tokens) {
        this.type = type;
        this.tokens = tokens;
    }

    enum Type {
        BRACES,
        PARENS
    }

    @Override
    public String toString() {
        return tokens.toString();
    }
}
