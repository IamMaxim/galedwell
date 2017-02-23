package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.GUI.Elements.ElementBase;

/**
 * Created by maxim on 2/22/17 at 11:20 PM.
 */
public interface LayoutWithList {
    LayoutWithList add(ElementBase element);
    void remove(ElementBase element);
    void clear();
}
