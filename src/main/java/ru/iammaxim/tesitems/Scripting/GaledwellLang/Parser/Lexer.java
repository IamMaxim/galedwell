package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;

import java.util.ArrayList;

/**
 * Created by maxim on 2/12/17 at 2:24 PM.
 */
public class Lexer {
    private StringBuilder ct = new StringBuilder(); // current token
    private ArrayList<Token> tokens = new ArrayList<>();
    private String src;
    private int line_number = 1;

    private Lexer(String src) {
        this.src = src;
    }

    public static ArrayList<Token> lex(String src) throws InvalidTokenException {
        Lexer lexer = new Lexer(src);
        lexer._lex();
        lexer.tokens = lexer.assembleTokenScopes(lexer.tokens);
        return lexer.tokens;
    }

    private void addLineNumber() {
        tokens.add(new Token(TokenType.NEW_LINE, "line: " + line_number));
        line_number++;
    }

    private ArrayList<Token> assembleTokenScopes(ArrayList<Token> tokens) throws InvalidTokenException {
        Tokener tokener = new Tokener(tokens);
        for (int i = 0; i < tokener.size(); i++) {
            Token token = tokener.get(i);
            TokenType type = token.type;
            if (type == TokenType.BRACE_OPEN) {
                tokener.index = i;
                Tokener tokener1 = tokener.readToSkippingScopes(new Token("}"));

                try {
                    //remove tokens and replace them with TokenScope
                    for (int j = 0; j < tokener1.size() + 2 /* content + wrapping braces */; j++) {
                        tokener.remove(i);
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                //recursively process nested scopes
                tokener1.tokens = assembleTokenScopes(tokener1.tokens);

                tokener.add(i, new TokenScope(TokenScope.Type.BRACES, tokener1.tokens));
            } else if (type == TokenType.PAREN_OPEN) {
                tokener.index = i;
                Tokener tokener1 = tokener.readToSkippingScopes(new Token(")"));

                try {
                    //remove tokens and replace them with TokenScope
                    for (int j = 0; j < tokener1.size() + 2 /* content + wrapping parens */; j++) {
                        tokener.remove(i);
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                //recursively process nested scopes
                tokener1.tokens = assembleTokenScopes(tokener1.tokens);

                tokener.add(i, new TokenScope(TokenScope.Type.PARENS, tokener1.tokens));
            }
        }

        return tokener.tokens;
    }

    private void _lex() throws InvalidTokenException {
        addLineNumber();
        char[] chars = src.toCharArray();
        int len = src.length();
        for (int i = 0; i < len; i++) {
            if (i == -1) // source code ended; no newline found
                break;

            // get current char
            char c = chars[i];

            if (c == '/') { // comment
                if (len - i >= 1 && chars[i + 1] == '/') { // singleline comment
                    i = src.indexOf('\n', i) - 1; // go to next line and leave \n, so lexer will lex newline
                    continue;
                }
                if (len - i > 1 && chars[i + 1] == '*') { // multiline comment
                    i += 3; // **/ if comment is 0 characters long; ......*/ otherwise
                    while (true) { // search for */
                        if (len - i < 2)
                            throw new InvalidTokenException("Expected multiline comment end, but EOF reached");

                        char c1 = chars[i - 1];
                        char c2 = chars[i];

                        if (c1 == '\n') {
                            addLineNumber();
                            i++;
                            continue;
                        }
                        if (c1 == '*' && c2 == '/') // end of comment
                            break;
                        i++;
                    }
                    continue;
                }
            }

            if (c == '\n') { // newline
                addLineNumber();
                continue;
            }

            //lex value
            if (c == '"') { // string
                ct.append(c);
                while ((c = chars[++i]) != '"') {
                    if (c == '\n')
                        addLineNumber();
                    ct.append(c);
                }
                ct.append(chars[i]);
                continue;
            }

            if (c == ' ') {
                cutOffToken();
                continue;
            }

            String s;

            // check if this is operator

            // check double-character operators (==, !=, ++, --)
            if (src.length() > i + 1) {
                s = new String(new char[]{c, chars[i + 1]});
                if (Token.is(TokenType.OPERATOR, s)) {
                    cutOffToken();
                    tokens.add(new Token(s));
                    i++; // skip next '='
                    continue;
                }
            }
            // check single-character operators (+, -, *, /, *)
            s = String.valueOf(c);
            if (Token.is(TokenType.OPERATOR, s)) {
                cutOffToken();
                tokens.add(new Token(s));
                continue;
            }

            // check if this is scope
            if (Token.is(TokenType.BRACE_OPEN, s)
                    || Token.is(TokenType.BRACE_CLOSE, s)
                    || Token.is(TokenType.PAREN_OPEN, s)
                    || Token.is(TokenType.PAREN_CLOSE, s)) {
                cutOffToken();
                tokens.add(new Token(s));
                continue;
            }

            //check if this is delimiter
            if (Token.is(TokenType.DELIMITER, s)) {
                cutOffToken();
                tokens.add(new Token(s));
                continue;
            }

            ct.append(c);
        }
    }

    private void cutOffToken() throws InvalidTokenException {
        Token token = new Token(ct.toString().trim());
        token.type = Token.getType(token.token);
        if (!token.empty()) {
            tokens.add(token);
            ct = new StringBuilder();
        }
    }
}
