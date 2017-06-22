package ru.iammaxim.tesitems.Scripting.GaledwellLang;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.CompilerDebugRuntime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.InvalidExpressionException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions.FunctionParsed;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.Operation;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.*;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.Compiler;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.Scripting.Logger;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by maxim on 2/18/17 at 11:30 PM.
 */
public class GaledwellLang {
    private static FileOutputStream tokens;
    private static FileOutputStream ops;
    public static Logger logger;

    public static void log(String s) {
        logger.log(s);
    }

    public static void loadSrcInto(String src, ValueObject object) throws InvalidTokenException, InvalidExpressionException {
        if (src.isEmpty())
            return;

        try {
            GaledwellLang.tokens = new FileOutputStream("tokens.txt");
            GaledwellLang.ops = new FileOutputStream("operations.txt");
            logger = new Logger("log.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Token> tokens = TokenParser.parse(src);

        tokens.forEach(t -> {
            try {
                GaledwellLang.tokens.write((t.toString() + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ArrayList<ParsedFunction> parsedFuncs = new FunctionParser(tokens).build();
        ArrayList<ValueFunction> funcs = new ArrayList<>(parsedFuncs.size());
        for (ParsedFunction parsedFunc : parsedFuncs) {
            GaledwellLang.log(">>> compiling function " + CompilerDebugRuntime.getName(parsedFunc.id));

            funcs.add(new FunctionParsed(parsedFunc.id, parsedFunc.args, Compiler.compileFunction(parsedFunc.tokens)));
        }

        for (ValueFunction f : funcs) {
            object.setField(f.id, f);
        }

        funcs.forEach(f -> {
            try {
                ops.write((">>> function: " + CompilerDebugRuntime.getName(f.id) + "\n").getBytes());
                ops.write((">>> args: " + Arrays.toString(Utils.getNames(f.args)) + "\n").getBytes());

                int opIndex = 0;
                for (Operation o : ((FunctionParsed) f).operations) {
                    try {
                        ops.write(((opIndex++) + ": " + o.toString() + "\n").getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.gc();
    }
}
