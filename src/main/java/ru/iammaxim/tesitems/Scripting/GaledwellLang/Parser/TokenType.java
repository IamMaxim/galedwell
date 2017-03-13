package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;

/**
 * Created by maxim on 2/12/17 at 2:15 PM.
 */
public enum TokenType {
    IDENTIFIER,
    DELIMITER, // ;,
    SCOPE_OPEN, // ({
    SCOPE_CLOSE, // )}
    OPERATOR, // +-*/= != < <= == >= >
    KEYWORD,
    SCOPE_PARENS, // multiple tokens in parens
    SCOPE_BRACES, // multiple tokens in braces
    NEW_LINE
}
