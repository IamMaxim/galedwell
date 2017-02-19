package ru.iammaxim.tesitems.Scripting.GaledwellLang;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;

import java.util.Arrays;

/**
 * Created by maxim on 2/17/17 at 7:03 PM.
 */
public class Stack {
    private Value[] stack;
    private int cursor = -1;

    public Stack(int size) {
        stack = new Value[size];
    }

    public void push(Value o) {
        stack[++cursor] = o;
    }

    public Value pop() {
        return stack[cursor--];
    }

    public Value get() {
        return stack[cursor];
    }

    @Override
    public String toString() {
        return Arrays.toString(stack).replace("\n", " ");
    }
}
