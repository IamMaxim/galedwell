package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/12/17 at 10:56 AM.
 */
public abstract class Operation {
    public abstract void run(Runtime runtime) throws InvalidOperationException;

}
