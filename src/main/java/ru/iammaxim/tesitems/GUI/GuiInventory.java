package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.FrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.Inventory.InventoryClient;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 2/24/17 at 8:57 PM.
 */
public class GuiInventory extends Screen {
    private static final int left_padding = 10, top_padding = 8, bottom_padding = 8;
    private InventoryClient inv;
    private TableEntry header;
    private Table table;
    private float playerScale = 1;

    public GuiInventory() {
        inv = (InventoryClient) TESItems.getCapability(TESItems.getClientPlayer()).getInventory();

        root = new FrameLayout() {
            @Override
            public void draw(int mouseX, int mouseY) {
                //draw player model

                super.draw(mouseX, mouseY);
            }
        };

        header = (TableEntry) new TableEntry()
                .add(new VerticalLayout().add(new HorizontalLayout().add(new Text("Name")).center(true).setWidthOverride(ElementBase.FILL)).center(true)._setwidth(200))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_value)).center(true)._setwidth(20))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_carryweight)).center(true)._setwidth(20))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_damage)).center(true)._setwidth(20))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_durability)).center(true)._setwidth(20));

        System.out.println("header width: " + header.getWidth());

        root.setElement(contentLayout);
        contentLayout.setElement(new ScrollableLayout().setElement(new VerticalLayout().add(table = new Table(header))));
        table.setWidthOverride(ElementBase.FILL);
        table.setWidth(280);


        table.add(getEntryFor("name1", 1, 1, 2, 3));
        table.add(getEntryFor("name2", 0, 1, -1, -1));
        for (int i = 0; i < 30; i++) {
            table.add(getEntryFor("dummy", i, 0, 0, 0));
        }

        root.setBounds(left_padding, top_padding, left_padding + root.getWidth(), res.getScaledHeight() - top_padding - bottom_padding);
        root.doLayout();
    }

    private void drawPlayer() {

    }

    private TableEntry getEntryFor(String name, float value, float weight, float damage, float durability) {
        return (TableEntry) ((TableEntry) new TableEntry()
                .add(new Text(name)._setwidth(200))
                .add(new HorizontalLayout().center(true).add(new Text(value == (int) value ? String.valueOf((int) value) : String.valueOf(value))))
                .add(new HorizontalLayout().center(true).add(new Text(weight == (int) weight ? String.valueOf((int) weight) : String.valueOf(weight))))
                .add(damage == -1 ?
                        new HorizontalLayout()._setwidth(20) :
                        new HorizontalLayout().center(true).add(new Text(damage == (int) damage ? String.valueOf((int) damage) : String.valueOf(damage))))
                .add(durability == -1 ?
                        new HorizontalLayout()._setwidth(20) :
                        new HorizontalLayout().center(true).add(new Text(durability == (int) durability ? String.valueOf((int) durability) : String.valueOf(durability)))))
                .setPaddingTop(4).setPaddingBottom(4);
    }
}
