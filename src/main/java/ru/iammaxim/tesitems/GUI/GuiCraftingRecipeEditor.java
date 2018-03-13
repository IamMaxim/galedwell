package ru.iammaxim.tesitems.GUI;

import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.Craft.CraftRecipe;
import ru.iammaxim.tesitems.Craft.CraftRecipes;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ItemStackEditorListLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.Networking.MessageRecipe;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 06.03.2017.
 */
public class GuiCraftingRecipeEditor extends Screen implements IGuiUpdatable {
    private TextField name;
    private ItemStackEditorListLayout input, output;
    private int id;
    private CraftRecipe.Type type;
    private CraftRecipe recipe;
    private boolean updated = true;

    @Override
    public boolean close() {
        ScreenStack.addScreen(new GuiConfirmationDialog("Are you sure you want to quit?", ScreenStack::forceClose));
        return false;
    }

    public GuiCraftingRecipeEditor(CraftRecipe.Type type, CraftRecipe _recipe) {
        this.id = _recipe.id;
        this.type = type;
        recipe = _recipe.copy();
        contentLayout.setElement(new VerticalLayout()
                .add(new HeaderLayout("Crafting recipe editor"))
                .add(name = new TextField().setHint("Name").setText(recipe.name).setOnType(tf -> recipe.name = tf.getText()))
                .add(new Text("Recipe id: " + id))
                .add(new HorizontalDivider())
                .add(new HeaderLayout("Input"))
                .add((input = new ItemStackEditorListLayout(recipe.input)).setWidthOverride(ElementBase.FILL))
                .add(new HorizontalDivider())
                .add(new HeaderLayout("Output"))
                .add((output = new ItemStackEditorListLayout(recipe.output)).setWidthOverride(ElementBase.FILL))
                .add(new HorizontalDivider())
                .add(new HorizontalLayout()
                        .add(new Button("Delete recipe").setOnClick(e -> {
                            if (!updated())
                                return;

                            CraftRecipes.clientRecipes.get(type).remove(id);
                            TESItems.networkWrapper.sendToServer(new MessageRecipe(type, id, null));
                            ScreenStack.processCallback("recipeListUpdated");
                            ScreenStack.forceClose();
                        }))
                        .add(new Button("Save").setOnClick(e -> {
                            if (!updated())
                                return;

                            recipe.input = input.getStacks().toArray(new ItemStack[input.size()]);
                            recipe.output = output.getStacks().toArray(new ItemStack[output.size()]);
                            TESItems.networkWrapper.sendToServer(new MessageRecipe(type, id, recipe));
                            unupdate();
                        })))
                .setPadding(4)
        );

        root.getScreen().addCallback("recipeUpdated", this::update);

        root.doLayout();
    }

    @Override
    public void update() {
        updated = true;
    }

    @Override
    public void unupdate() {
        updated = false;
    }

    @Override
    public boolean updated() {
        return updated;
    }
}
