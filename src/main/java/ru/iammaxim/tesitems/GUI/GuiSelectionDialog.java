package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutWithList;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.Utils.ClientThings;

import java.util.function.Consumer;

/**
 * Created by maxim on 2/22/17 at 10:23 PM.
 */
public class GuiSelectionDialog extends Screen {
    public Consumer<Integer> onSelect;
    public Consumer<GuiSelectionDialog> onDismiss;

    @Override
    public boolean close() {
        if (onDismiss != null) {
            ScreenStack.forceClose();
            onDismiss.accept(this);
        }
        return false;
    }

    public GuiSelectionDialog(String message, String... entries) {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(4);
        layout.add(new Text(message));

        int entriesWidth = 0;
        for (String entry : entries) {
            entriesWidth += ClientThings.fontRenderer.getStringWidth(entry);
        }

        LayoutWithList selectionsLayout;
        if (entriesWidth > res.getScaledWidth() * 0.6) {
            //add selections vertically
            selectionsLayout = new VerticalLayout();

        } else {
            //add selections horizontally
            selectionsLayout = new HorizontalLayout();
        }

        for (int i = 0; i < entries.length; i++) {
            int finalI = i;
            selectionsLayout.add(new Button(entries[i]).setUseInactiveBackground(false).setOnClick(b -> {
                ScreenStack.forceClose();
                onSelect.accept(finalI);
            }));
        }

        layout.add((ElementBase) selectionsLayout);
        contentLayout.setElement(layout);
        root.doLayout();
    }

    public GuiSelectionDialog setOnSelect(Consumer<Integer> onSelect) {
        this.onSelect = onSelect;
        return this;
    }

    public GuiSelectionDialog setOnDismiss(Consumer<GuiSelectionDialog> gui) {
        this.onDismiss = gui;
        return this;
    }
}
