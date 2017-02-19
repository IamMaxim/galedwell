package ru.iammaxim.tesitems.Scripting.GaledwellLang;

/**
 * Created by maxim on 2/12/17 at 11:32 AM.
 */
public class Utils {
    public static final int INDENT_SIZE = 2;

    public static String[] splitByComma(String src) {
        return src.split("[ \\t]*,[ \\t]*");
    }

    public static String indent(int count) {
        StringBuilder outputBuffer = new StringBuilder(count * INDENT_SIZE);
        for (int i = 0; i < count * INDENT_SIZE; i++){
            outputBuffer.append(" ");
        }
        return outputBuffer.toString();
    }
}
