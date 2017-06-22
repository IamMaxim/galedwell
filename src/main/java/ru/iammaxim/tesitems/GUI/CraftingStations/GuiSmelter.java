package ru.iammaxim.tesitems.GUI.CraftingStations;

import ru.iammaxim.tesitems.Craft.CraftRecipe;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.GUI.Screen;

/**
 * Created by maxim on 6/17/17 at 3:31 PM.
 */
public class GuiSmelter extends Screen {
    private final CraftRecipe.Type type = CraftRecipe.Type.SMELTER;
    private final VerticalLayout recipeListLayout;
    private final VerticalLayout requiredListLayout;
    private final VerticalLayout resultListLayout;
    private int selectedRecipeIndex = 0;

    public GuiSmelter() {
        contentLayout.setElement(new HorizontalLayout()
                .add(new ScrollableLayout()
                        .setElement(recipeListLayout = new VerticalLayout()))
                .add(new VerticalLayout()
                        .add(new HeaderLayout("Required"))
                        .add(requiredListLayout = new VerticalLayout())
                        .add(new HeaderLayout("Result"))
                        .add(resultListLayout = new VerticalLayout())
                        .add(new Button("Craft"))));

        root.doLayout();
    }
}
