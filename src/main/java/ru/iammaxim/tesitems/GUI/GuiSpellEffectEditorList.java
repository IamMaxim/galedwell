package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.Magic.SpellEffectManager;

public class GuiSpellEffectEditorList extends Screen {
    VerticalLayout list;

    public GuiSpellEffectEditorList() {
        contentLayout.setElement(new VerticalLayout()
        .add(new HeaderLayout("Spell effects editor"))
        .add(new ScrollableLayout().setElement(list = new VerticalLayout())));

        root.doLayout();
    }
}
