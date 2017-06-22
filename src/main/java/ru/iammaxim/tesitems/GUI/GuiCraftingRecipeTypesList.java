package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.Craft.CraftRecipe;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;

/**
 * Created by maxim on 06.03.2017.
 */
public class GuiCraftingRecipeTypesList extends Screen {
    private VerticalLayout list;

    public GuiCraftingRecipeTypesList() {
        contentLayout.setElement(new VerticalLayout()
                .add(new ScrollableLayout().setElement(list = new VerticalLayout()).setWidthOverride(ElementBase.FILL)));

        for (CraftRecipe.Type type : CraftRecipe.Type.values()) {
            list.add(
                    new Text(type.name()).setColor(ResManager.CLICKABLE_TEXT_COLOR).setOnClick(e ->
                            ScreenStack.addScreen(new GuiCraftingRecipesList(type))));
        }

        root.doLayout();
    }
}
