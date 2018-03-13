package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.FrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

public class GuiAttributes extends Screen {
    Table table;


    public GuiAttributes() {
        contentLayout.setPadding(4);
        contentLayout.setElement(new VerticalLayout()
                .add(new HeaderLayout("Attributes"))
                .add(table = new Table(
                        (TableEntry) new TableEntry(0)
                                .add(new Text("Attribute").center(true)._setwidth(90))
                                .add(new Text("Value").center(true)._setwidth(40))
                                .add(new Text("Progress").center(true)._setwidth(90)))
                )
        );

        table.setWidth(table.getHeader().getWidth());

        IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());
        cap.getAttributes().forEach((k, v) -> addAttribute(k, v));


        root.doLayout();
    }

    private void addAttribute(String name, float value) {
        table.add((ElementBase) new TableEntry()
                .add(new FrameLayout().setElement(new Text(name)).setPaddingTop(2).setPaddingBottom(2).setPaddingLeft(4).setPaddingRight(4))
                .add(new FrameLayout().setElement(new Text(String.valueOf((int) value)).center(true)).setPaddingTop(2).setPaddingBottom(2).setPaddingLeft(4).setPaddingRight(4))
                .add(new FrameLayout().setElement(new ProgressBar().setProgress(value - (int) value)).setPaddingTop(3).setPaddingBottom(3).setPaddingLeft(4).setPaddingRight(4)));
    }
}
