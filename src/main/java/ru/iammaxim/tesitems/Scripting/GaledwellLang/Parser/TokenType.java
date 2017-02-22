package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;

/**
 * Created by maxim on 2/12/17 at 2:15 PM.
 */
public enum TokenType {
    IDENTIFIER,
    DELIMITER, //;,
    SCOPE_OPEN, // ({
    SCOPE_CLOSE, // )}
    OPERATOR, // +-*/= != < <= == >= >
    KEYWORD
}
