package ru.iammaxim.tesitems.Scripting;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by maxim on 13.03.2017.
 */
public class Logger {
    private static final int indentFactor = 4;
    private int indent = 0;
    private static FileOutputStream log;

    public Logger(String path) throws FileNotFoundException {
        log = new FileOutputStream(path);
    }

    public void increateIndent() {
        indent++;
    }

    public void decreaseIndent() {
        indent--;
    }

    public void log(String s) {
        try {
            log.write((Utils.indent(indent * indentFactor) + s + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
