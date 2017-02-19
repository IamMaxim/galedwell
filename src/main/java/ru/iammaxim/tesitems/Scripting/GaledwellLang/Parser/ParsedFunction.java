package ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.Expression.Expression;

import java.util.ArrayList;

/**
 * Created by maxim on 2/17/17 at 7:27 PM.
 */
public class ParsedFunction {
    public int id;
    public int[] args;
    public ArrayList<Expression> tokens;

    public ParsedFunction(int id, int[] args, ArrayList<Expression> tokens) {
        this.id = id;
        this.args = args;
        this.tokens = tokens;
    }
}
