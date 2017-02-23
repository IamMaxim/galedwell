package ru.iammaxim.tesitems.Scripting.GaledwellLang;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;

/**
 * Created by maxim on 2/12/17 at 1:33 AM.
 */
public class Runtime {
    public Stack stack = new Stack(128);
    public ValueObject variableStorage = new ValueObject();

    //this variable stores current operation index
    public int currentCursorPos = -1;
    //this variable stores current function length
    public int currentFunctionLength = -1;

    public void goToFunctionEnd() {
        currentCursorPos = currentFunctionLength;
    }
}
