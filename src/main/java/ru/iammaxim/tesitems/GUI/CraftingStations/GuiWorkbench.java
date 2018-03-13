package ru.iammaxim.tesitems.GUI.CraftingStations;

import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.Craft.CraftRecipe;
import ru.iammaxim.tesitems.Craft.CraftRecipes;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.GUI.ResManager;
import ru.iammaxim.tesitems.GUI.Screen;
import ru.iammaxim.tesitems.Networking.MessageCraft;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 3/5/17 at 2:35 PM.
 */
public class GuiWorkbench extends Screen {
    private VerticalLayout listLayout, inputLayout, outputLayout;
    private ElementBase selected;
    private Text recipeName;
    private int selectedRecipeID = -1;
    private CraftRecipe currentRecipe;

    public GuiWorkbench() {
        contentLayout.setElement(new HorizontalLayout()
                .add(new WrapFrameLayout().setElement(new ScrollableLayout().setElement(listLayout = new VerticalLayout())).setPadding(4).setHeightOverride(ElementBase.FILL))
                .add(new VerticalLayout()
                        .add(new HeaderLayout("Workbench").setPaddingBottom(4).setPaddingTop(4))
                        .add(recipeName = (Text) new Text("Select recipe").center(true).setWidthOverride(ElementBase.FILL))
                        .add(new HorizontalLayout()
                                .add(new VerticalDivider())
                                .add(new VerticalLayout()
                                        .add(new HeaderLayout("Input").setWidthOverride(ElementBase.FILL))
                                        .add(inputLayout = new VerticalLayout()).setWidthOverride(ElementBase.FILL))
                                .setWidthOverride(ElementBase.FILL))
                        .add(new HorizontalLayout()
                                .add(new VerticalDivider())
                                .add(new VerticalLayout()
                                        .add(new HeaderLayout("Output").setWidthOverride(ElementBase.FILL))
                                        .add(outputLayout = new VerticalLayout()).setWidthOverride(ElementBase.FILL))
                                .setWidthOverride(ElementBase.FILL))
                        .add(new Button("Craft").setOnClick(b -> {
                            if (selectedRecipeID != -1)
                                TESItems.networkWrapper.sendToServer(new MessageCraft(CraftRecipe.Type.WORKBENCH, selectedRecipeID));
                        }))
                        .center(true).setHeightOverride(ElementBase.FILL).setRightMargin(4)));

        CraftRecipes.clientRecipes.get(CraftRecipe.Type.WORKBENCH).forEach((id, recipe) -> {
            listLayout.add(getElement(recipe));
        });

        root.doLayout();
    }

    private void updateCurrentRecipe() {
        inputLayout.clear();

        if (currentRecipe != null)
            for (ItemStack itemStack : currentRecipe.input) {
                inputLayout.add(new ItemStackLayout(itemStack));
            }


        outputLayout.clear();

        if (currentRecipe != null)
            for (ItemStack itemStack : currentRecipe.output) {
                outputLayout.add(new ItemStackLayout(itemStack));
            }
    }

    public ElementBase getElement(CraftRecipe recipe) {
        return new Text(recipe.name).setOnClick(e -> {
            if (selected != null) {
                selected.setBackground(null);
            }

            e.setBackground(new Image(ResManager.inv_entry_bg_selected));
            selectedRecipeID = recipe.id;
            currentRecipe = recipe;

            recipeName.setText(recipe.name);

            updateCurrentRecipe();
            root.doLayout();
        });
    }
}
