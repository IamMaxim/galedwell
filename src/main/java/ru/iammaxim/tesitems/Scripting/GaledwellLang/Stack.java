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
//        System.out.println("        pushed " + o);
        stack[++cursor] = o;
    }

    public Value pop() {
//        Value value = stack[cursor];
//        stack[cursor] = null;
//        cursor--;
//        System.out.println("        popped " + value);
        return stack[cursor--];
    }

    public Value get() {
//        System.out.println("        got " + stack[cursor]);
        return stack[cursor];
    }

    @Override
    public String toString() {
        return "cursor: " + cursor + "; " + Arrays.toString(stack).replace("\n", " ");
    }
}
