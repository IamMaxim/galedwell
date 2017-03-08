package ru.iammaxim.tesitems.GUI;

import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.Craft.CraftRecipe;
import ru.iammaxim.tesitems.Craft.CraftRecipes;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;

/**
 * Created by maxim on 06.03.2017.
 */
public class GuiCraftingRecipesList extends Screen {
    private VerticalLayout list;

    public GuiCraftingRecipesList() {
        contentLayout.setElement(new VerticalLayout()
                .add(new ScrollableLayout().setElement(list = new VerticalLayout()).setWidthOverride(ElementBase.FILL))
                .add(new Button("Add new recipe").setOnClick(e -> ScreenStack.addScreen(new GuiCraftingRecipeEditor(new CraftRecipe("New recipe", new ItemStack[] {}, new ItemStack[] {}))))));

        CraftRecipes.recipes.forEach((id, recipe) -> {
            list.add(new Text(recipe.name).setColor(ResManager.CLICKABLE_TEXT_COLOR).setOnClick(e -> {
                ScreenStack.addScreen(new GuiCraftingRecipeEditor(recipe));
            }));
        });

        root.doLayout();
    }
}
