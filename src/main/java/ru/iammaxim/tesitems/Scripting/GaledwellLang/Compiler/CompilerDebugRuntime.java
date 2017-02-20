package ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler;

import java.util.HashMap;

/**
 * Created by maxim on 2/20/17 at 10:41 AM.
 */
public class CompilerDebugRuntime {
    public static HashMap<Integer, String> names = new HashMap<>(); // contains all names replaced with name.hashCode()

    public static void addName(int id, String name) {
        names.put(id, name);
    }

    public static String getName(int id) {
        return names.get(id);
    }
}
