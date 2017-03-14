package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;

import java.util.ArrayList;

/**
 * Created by maxim on 2/12/17 at 2:24 PM.
 */
public class TokenParser {
    private Token ct = new Token(); //current token
    private ArrayList<Token> tokens = new ArrayList<>();
    private String src;
    private int line_number = 1;

    private TokenParser(String src) {
        this.src = src;
    }

    public static ArrayList<Token> parse(String src) throws InvalidTokenException {
        TokenParser parser = new TokenParser(src);
        parser._parse();
        parser.tokens = parser.assembleTokenScopes(parser.tokens);
        return parser.tokens;
    }

    private void addLineNumber() {
        tokens.add(new Token(TokenType.NEW_LINE, "line: " + line_number));
        line_number++;
    }

    private ArrayList<Token> assembleTokenScopes(ArrayList<Token> tokens) throws InvalidTokenException {
        System.out.println("processing " + tokens);
        Tokener tokener = new Tokener(tokens);
        for (int i = 0; i < tokener.size(); i++) {
            Token token = tokener.get(i);
            TokenType type = token.type;
            if (type == TokenType.BRACE_OPEN) {
                tokener.index = i;
                Tokener tokener1 = tokener.readToSkippingScopes(new Token("}"));
                if (tokener.left() > 0)
                    System.out.println("next token: " + tokener.get());
                System.out.println("ate " + tokener1);

                try {
                    //remove tokens and replace them with TokenScope
                    for (int j = 0; j < tokener1.size() + 2 /* content + wrapping braces */; j++) {
                        tokener.remove(i);
                    }

                    System.out.println("after processing: " + tokener);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("current tokener: " + tokener);
                    System.out.println(e.toString());
                }

                //recursively process nested scopes
                tokener1.tokens = assembleTokenScopes(tokener1.tokens);

                tokener.add(i, new TokenScope(TokenScope.Type.BRACES, tokener1.tokens));

                System.out.println("final tokener: " + tokener);
            } else if (type == TokenType.PAREN_OPEN) {
                tokener.index = i;
                Tokener tokener1 = tokener.readToSkippingScopes(new Token(")"));
                if (tokener.left() > 0)
                    System.out.println("next token: " + tokener.get());
                System.out.println("ate " + tokener1);

                try {
                    //remove tokens and replace them with TokenScope
                    for (int j = 0; j < tokener1.size() + 2 /* content + wrapping parens */; j++) {
                        tokener.remove(i);
                    }

                    System.out.println("after processing: " + tokener);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("current tokener: " + tokener);
                    System.out.println(e.toString());
                }

                //recursively process nested scopes
                tokener1.tokens = assembleTokenScopes(tokener1.tokens);

                tokener.add(i, new TokenScope(TokenScope.Type.PARENS, tokener1.tokens));

                System.out.println("final tokener: " + tokener);
            }
        }

        return tokener.tokens;
    }

    private void _parse() throws InvalidTokenException {
        addLineNumber();
        char[] chars = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            char c = chars[i];

            if (c == '/')
                if (chars.length >= i + 1 && chars[i + 1] == '/') { // comment
                    i = src.indexOf('\n', i) - 1; // go to next line and leave \n, so parser will parse newline
                    continue;
                }

            if (c == '\n')
                addLineNumber();

            //parse value
            if (c == '"') {
                ct.token += c;
                while ((c = chars[++i]) != '"') {
                    if (c == '\n')
                        addLineNumber();
                    ct.token += c;
                }
                ct.token += chars[i];
                continue;
            }

            if (c == ' ') {
                cutOffToken();
                continue;
            }

            String s;

            //check if this is operator
            //check double-character operators (==, !=, ++, --)
            if (src.length() > i + 1) {
                s = new String(new char[]{c, chars[i + 1]});
                if (Token.is(TokenType.OPERATOR, s)) {
                    cutOffToken();
                    tokens.add(new Token(s));
                    i++; // skip next '='
                    continue;
                }
            }
            //check single-character operators (+, -, *, /, *)
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

            ct.token += c;
        }
    }

    private void cutOffToken() throws InvalidTokenException {
        ct.token = ct.token.trim();
        ct.type = Token.getType(ct.token);
        if (!ct.empty()) {
            tokens.add(ct);
            ct = new Token();
        }
    }
}
