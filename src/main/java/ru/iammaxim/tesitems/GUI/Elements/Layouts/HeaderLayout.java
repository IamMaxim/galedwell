package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.GUI.Elements.Decor;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.Text;

/**
 * Created by maxim on 1/2/17 at 5:35 PM.
 */
public class HeaderLayout extends HorizontalLayout {

    public HeaderLayout(String text) {
        center(true);
        add(new Decor(Decor.Side.LEFT));
        add(new Text(text));
        add(new Decor(Decor.Side.RIGHT));
    }
}
