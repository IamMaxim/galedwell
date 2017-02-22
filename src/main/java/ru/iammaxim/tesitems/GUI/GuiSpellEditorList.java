package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;

/**
 * Created by maxim on 2/21/17 at 4:10 PM.
 */
public class GuiSpellEditorList extends Screen {

    public GuiSpellEditorList() {
        VerticalLayout layout = new VerticalLayout();
        layout.add(new HeaderLayout("Spell list"));


//        layout.add(new Button("Create new spell").setOnClick());

        contentLayout.setElement(new ScrollableLayout().setElement(new VerticalLayout().add(layout)));
        root.doLayout();
    }
}
