package ru.iammaxim.tesitems.Scripting.GaledwellLang;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions.FunctionDumpVariableStorage;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions.FunctionPrint;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.InvalidTokenException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by maxim on 2/12/17 at 12:14 AM.
 */
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("src.txt")).useDelimiter("\\A")) {
            String src = scanner.next();

            Runtime runtime = new Runtime();

            runtime.variableStorage.setField("print", new FunctionPrint());
            runtime.variableStorage.setField("dumpVarStorage", new FunctionDumpVariableStorage());
            GaledwellLang.loadSrcInto(src, runtime.variableStorage);

            run(runtime);
//            benchmark(runtime);

//            long freeMemory = java.lang.Runtime.getRuntime().freeMemory();
//            long totalMemory = java.lang.Runtime.getRuntime().totalMemory();
//            System.out.println("Occupied memory: " + ((double) (totalMemory - freeMemory) / 1024 / 1024) + "MB (" + ((1 - (double) freeMemory / totalMemory) * 100) + "%)");

        } catch (FileNotFoundException | InvalidTokenException | InvalidOperationException e) {
            e.printStackTrace();
        }
    }

    public static void benchmark(Runtime runtime) throws InvalidOperationException {
        long start = System.nanoTime();
        int runCount = 10000000;
        for (int i = 0; i < runCount; i++)
            ((ValueFunction) runtime.variableStorage.getField("main")).call(runtime);
        double runTime = System.nanoTime() - start;
        System.out.println("elapsed time: " + (runTime/1000000000) + "sec");
        System.out.println("one execution time: " + (runTime/runCount) + "ns");
    }

    public static void run(Runtime runtime) throws InvalidOperationException {
        ((ValueFunction) runtime.variableStorage.getField("main")).call(runtime);
    }
}
