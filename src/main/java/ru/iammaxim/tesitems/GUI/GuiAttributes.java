package ru.iammaxim.tesitems.GUI;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.FrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

public class GuiAttributes extends Screen {
    Table table, table2;


    public GuiAttributes() {
        contentLayout.setPadding(4);
        contentLayout.setElement(new HorizontalLayout()
                .add(new VerticalLayout()
                        .add(new HeaderLayout("Stats"))
                        .add(table2 = new Table(
                                        (TableEntry) new TableEntry(0)
                                                .add(new Text("Attribute").center(true)._setwidth(60))
                                                .add(new Text("Value").center(true)._setwidth(30))
                                                .add(new Text("Max").center(true)._setwidth(30)))
                        ))
                .add(new VerticalDivider())
                .add(new VerticalLayout()
                        .add(new HeaderLayout("Attributes"))
                        .add(table = new Table(
                                (TableEntry) new TableEntry(0)
                                        .add(new Text("Attribute").center(true)._setwidth(90))
                                        .add(new Text("Value").center(true)._setwidth(40))
                                        .add(new Text("Progress").center(true)._setwidth(90)))
                        ))
        );

        table.setWidth(table.getHeader().getWidth());
        table2.setWidth(table2.getHeader().getWidth());

        EntityPlayer player = TESItems.getClientPlayer();
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        cap.getAttributes().forEach((k, v) -> addAttribute(k, v));

        addEntry2("Health", player.getHealth(), player.getMaxHealth());
        addEntry2("Magicka", cap.getMagicka(), cap.getMaxMagicka());
        addEntry2("Hunger", player.getFoodStats().getFoodLevel(), 20);

        root.doLayout();
    }

    private void addAttribute(String name, float value) {
        table.add((ElementBase) new TableEntry()
                .add(new FrameLayout().setElement(new Text(name)).setPaddingTop(2).setPaddingBottom(2).setPaddingLeft(4).setPaddingRight(4))
                .add(new FrameLayout().setElement(new Text(String.valueOf((int) value)).center(true)).setPaddingTop(2).setPaddingBottom(2).setPaddingLeft(4).setPaddingRight(4))
                .add(new FrameLayout().setElement(new ProgressBar().setProgress(value - (int) value)).setPaddingTop(3).setPaddingBottom(3).setPaddingLeft(4).setPaddingRight(4)));
    }

    private void addEntry2(String name, float value, float max) {
        table2.add((ElementBase) new TableEntry()
                .add(new FrameLayout().setElement(new Text(name)).setPaddingTop(2).setPaddingBottom(2).setPaddingLeft(4).setPaddingRight(4))
                .add(new FrameLayout().setElement(new Text(String.valueOf((int) value)).center(true)).setPaddingTop(2).setPaddingBottom(2).setPaddingLeft(4).setPaddingRight(4))
                .add(new FrameLayout().setElement(new Text(String.valueOf((int) max)).center(true)).setPaddingTop(2).setPaddingBottom(2).setPaddingLeft(4).setPaddingRight(4)));
    }
}
