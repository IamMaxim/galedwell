package ru.iammaxim.tesitems.Scripting.GaledwellLang;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.CompilerDebugRuntime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions.FunctionParsed;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.Operation;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.*;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.Compiler;


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
    private static FileOutputStream log;

    public static void log(String s) {
        try {
            log.write((s + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            GaledwellLang.tokens = new FileOutputStream("tokens.txt");
            GaledwellLang.ops = new FileOutputStream("ops.txt");
            GaledwellLang.log = new FileOutputStream("log.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadSrcInto(String src, Runtime runtime) throws InvalidTokenException {
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
            runtime.variableStorage.setField(f.id, f);
        }

        funcs.forEach(f -> {
            try {
                ops.write((">>> function: " + f.id + "\n").getBytes());
                ops.write((">>> args: " + Arrays.toString(f.args) + "\n").getBytes());

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
