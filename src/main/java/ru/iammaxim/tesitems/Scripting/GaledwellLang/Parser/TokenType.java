package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;

/**
 * Created by maxim on 2/12/17 at 2:15 PM.
 */
public enum TokenType {
    IDENTIFIER,
    DELIMITER, // ;,
    BRACE_OPEN, // {
    BRACE_CLOSE, // }
    PAREN_OPEN, // (
    PAREN_CLOSE, // )
    BRACKET_OPEN, // [
    BRACKET_CLOSE, // ]
    OPERATOR, // +-*/= != < <= == >= >
    KEYWORD,
    SCOPE_PARENS, // multiple tokens in parens
    SCOPE_BRACES, // multiple tokens in braces
    SCOPE_BRACKETS, // multiple tokens in brackets
    NEW_LINE
}
