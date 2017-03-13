package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;

import java.util.ArrayList;

/**
 * Created by maxim on 2/13/17 at 4:25 PM.
 */
public class Tokener {
    public ArrayList<Token> tokens;
    public int index = 0;

    public Tokener(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public Tokener(Token... tokens) {
        this.tokens = new ArrayList<>();
        for (Token token : tokens) {
            this.tokens.add(token);
        }
    }

    public int left() {
        return tokens.size() - index;
    }

    public Token eat() {
        Token t = tokens.get(index++);
        while (t.type == TokenType.NEW_LINE) {
            t = tokens.get(index++);
        }
        return t;
    }

    public Tokener readTo(Token token) throws InvalidTokenException {
        ArrayList<Token> tokens = new ArrayList<>();
        while (left() > 0) {
            Token t = eat();
            if (t.equals(token))
                break;

            tokens.add(t);
        }
        if (index == tokens.size())
            throw new InvalidTokenException("Excepted " + token.token);
        return new Tokener(tokens);
    }

    public Tokener readToSkippingScopes(Token token) throws InvalidTokenException {
        ArrayList<Token> tokens = new ArrayList<>();
        int level = 0; //because first brace/paren has already been read
        while (left() > 0) {
            Token t = eat();

            if (t.type == TokenType.SCOPE_OPEN)
                level++;
            if (t.type == TokenType.SCOPE_CLOSE)
                level--;

            if (level > 0) {
                tokens.add(t);
                continue;
            }

            if (t.equals(token))
                break;

            tokens.add(t);
        }
        if (index == tokens.size())
            throw new InvalidTokenException("Excepted " + token.token);

        tokens.remove(0); //remove first scope
        return new Tokener(tokens);
    }

    @Override
    public String toString() {
        return tokens.toString();
    }

    public boolean isEmpty() {
        return tokens.isEmpty();
    }

    public ArrayList<Tokener> splitByFirst(Token token) {
        ArrayList<Tokener> parts = new ArrayList<>();
        ArrayList<Token> tokens = new ArrayList<>();
        index = 0;
        while (left() > 0) {
            Token t = eat();
            if (t.equals(token)) {
                parts.add(new Tokener(tokens));
                tokens = new ArrayList<>();
                while (left() > 0) {
                    tokens.add(eat());
                }
                break;
            } else
                tokens.add(t);
        }
        //add last part that is not followed by delimiter
        parts.add(new Tokener(tokens));
        return parts;
    }

    //including begin, excluding end
    public Tokener subtokener(int begin, int end) {
        ArrayList<Token> subList = new ArrayList<>(end - begin);
        for (int i = begin; i < end; i++)
            subList.add(tokens.get(i));
        return new Tokener(subList);
    }

    public int size() {
        return tokens.size();
    }

    public void trimParentheses() throws InvalidTokenException {
        if (size() >= 2)
            while (tokens.get(0).equals(new Token("(")) && tokens.get(tokens.size() - 1).equals(new Token(")"))) {
                tokens.remove(0);
                tokens.remove(tokens.size() - 1);
            }
    }

    public Token get() {
        return tokens.get(index);
    }

    public Token get(int i) {
        return tokens.get(i);
    }

    public ArrayList<Tokener> splitSkippingScopes(Token token) {
        ArrayList<Tokener> parts = new ArrayList<>();
        ArrayList<Token> tokens = new ArrayList<>();
        index = 0;
        int level = 0;
        while (left() > 0) {
            Token t = eat();

            if (t.type == TokenType.SCOPE_OPEN)
                level++;
            if (t.type == TokenType.SCOPE_CLOSE)
                level--;

            if (level > 0) {
                tokens.add(t);
                continue;
            }

            if (t.equals(token)) {
                if (tokens.size() > 0) {
                    parts.add(new Tokener(tokens));
                    tokens = new ArrayList<>();
                }
            } else
                tokens.add(t);
        }
        //add last part that is not followed by delimiter
        if (tokens.size() > 0)
            parts.add(new Tokener(tokens));
        return parts;
    }
}
