package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.LayoutBase;

/**
 * Created by maxim on 27.01.2017.
 */
public class DoubleStateFrameLayout extends FrameLayout {
    public ElementBase first, second;

    public DoubleStateFrameLayout() {}

    public DoubleStateFrameLayout setFirstState(ElementBase element) {
        first = element;
        element.setParent(this);
        return this;
    }

    public DoubleStateFrameLayout setSecondState(ElementBase element) {
        second = element;
        element.setParent(this);
        return this;
    }

    public DoubleStateFrameLayout selectFirst() {
        setElement(first);
        return this;
    }


    public DoubleStateFrameLayout selectSecond() {
        setElement(second);
        return this;
    }


    public DoubleStateFrameLayout toggleSelection() {
        if (element == first)
            selectSecond();
        else
            selectFirst();
        return this;
    }
}
