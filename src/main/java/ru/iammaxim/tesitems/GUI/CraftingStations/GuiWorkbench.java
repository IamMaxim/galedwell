package ru.iammaxim.tesitems.GUI.CraftingStations;

import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.Craft.CraftRecipe;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.GUI.ResManager;
import ru.iammaxim.tesitems.GUI.Screen;

/**
 * Created by maxim on 3/5/17 at 2:35 PM.
 */
public class GuiWorkbench extends Screen {
    private VerticalLayout listLayout, inputLayout, outputLayout;
    private ElementBase selected;
    private Text recipeName;
    private int selectedRecipeID = -1;

    public GuiWorkbench() {
        contentLayout.setElement(new HorizontalLayout()
                .add(new ScrollableLayout().setElement(listLayout = new VerticalLayout()))
                .add(new VerticalLayout()
                        .add(recipeName = new Text("Select recipe"))
                        .add(new HorizontalLayout()
                                .add(new VerticalDivider())
                                .add(new VerticalLayout()
                                        .add(new Text("Input"))
                                        .add(inputLayout = new VerticalLayout())))
                        .add(new HorizontalLayout()
                                .add(new VerticalDivider())
                                .add(new VerticalLayout()
                                        .add(new Text("Output"))
                                        .add(outputLayout = new VerticalLayout())))
                        .add(new Button("Craft").setOnClick(b -> {
                            //send msg to server
                        }))
                        .center(true).setHeightOverride(ElementBase.FILL)));

        root.doLayout();
    }

    public ElementBase getElement(int id, CraftRecipe recipe) {
        return new Text(recipe.name).setOnClick(e -> {
            if (selected != null) {
                selected.setBackground(null);
            }
            e.setBackground(new Image(ResManager.inv_entry_bg_selected));
            selectedRecipeID = id;

            recipeName.setText(recipe.name);
            inputLayout.clear();
            for (ItemStack is : recipe.input)
                inputLayout.add(new Text("(" + is.stackSize + ") " + is.getDisplayName()));

            outputLayout.clear();
            for (ItemStack is : recipe.output)
                outputLayout.add(new Text("(" + is.stackSize + ") " + is.getDisplayName()));

            root.doLayout();
        });
    }
}
