package ru.iammaxim.tesitems.GUI;

import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.Craft.CraftRecipe;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.HorizontalDivider;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.GUI.Elements.TextField;

/**
 * Created by maxim on 06.03.2017.
 */
public class GuiCraftingRecipeEditor extends Screen {
    private TextField name;
    private ItemStackEditorListLayout input, output;
    private CraftRecipe recipe;

    public GuiCraftingRecipeEditor(CraftRecipe _recipe) {
        recipe = _recipe.copy();
        contentLayout.setElement(new VerticalLayout()
                .add(new HeaderLayout("Crafting recipe editor"))
                .add(name = new TextField().setHint("Name").setText(recipe.name))
                .add(new HorizontalDivider())
                .add(new HeaderLayout("Input"))
                .add(input = new ItemStackEditorListLayout(recipe.input))
                .add(new HorizontalDivider())
                .add(new HeaderLayout("Output"))
                .add(output = new ItemStackEditorListLayout(recipe.output))
                .add(new HorizontalDivider())
                .add(new Button("Delete recipe"))
        );

        root.doLayout();
    }
}
