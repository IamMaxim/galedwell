package ru.iammaxim.tesitems;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Maxim on 12.07.2016.
 */
public class ReflectionUtils {
    public static Object createInstance(Class clazz, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Class[] args1 = new Class[args.length];
        for (int i = 0; i < args1.length; i++) {
            args1[i] = args[i].getClass();
        }
        return Class.forName(clazz.getName()).getConstructor(args1).newInstance(args);
    }
}
