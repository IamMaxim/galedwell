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
        return parser.tokens;
    }

    private void addLineNumber() {
        tokens.add(new Token(TokenType.NEW_LINE, "line: " + line_number));
        line_number++;
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
            if (Token.is(TokenType.SCOPE_OPEN, s) || Token.is(TokenType.SCOPE_CLOSE, s)) {
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
