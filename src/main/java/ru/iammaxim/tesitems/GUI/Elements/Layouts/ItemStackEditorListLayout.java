package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.GUI.Elements.Button;

import java.util.ArrayList;

/**
 * Created by maxim on 07.03.2017.
 */
public class ItemStackEditorListLayout extends FrameLayout {
    private ArrayList<ItemStackEditorLayout> editors = new ArrayList<>();
    //    private ArrayList<ElementBase> elements = new ArrayList<>();
    private VerticalLayout layout;

    public ItemStackEditorListLayout(ItemStack[] initialElements) {
        this();
        init(initialElements);
        doLayout();
    }

    public ItemStackEditorListLayout() {
        setElement(new WrapFrameLayout()
                .setElement(new VerticalLayout()
                        .add((layout = new VerticalLayout()).setWidthOverride(FILL))
                        .add(new Button("Add stack").setOnClick(e -> {
                            addElement(new ItemStack(Blocks.DIRT));
                        }))));
    }

    public ItemStackEditorListLayout init(ItemStack[] initialElements) {
        for (ItemStack is : initialElements) {
            addElement(is);
        }
        return this;
    }

    private void addElement(ItemStack is) {
        ItemStackEditorLayout editor = new ItemStackEditorLayout(is);
        editors.add(editor);
        layout.add(new StartFromRightHorizontalLayout()
                .add(editor.setWidthOverride(FILL))
                .add(new Button("Remove").setOnClick(e -> {
                    int index = editors.indexOf(editor);
                    editors.remove(index);
                    layout.remove(index);
                    ((LayoutBase) getRoot()).doLayout();
                }))
                .setWidthOverride(FILL));
        ((LayoutBase) getRoot()).doLayout();
    }

    public ArrayList<ItemStack> getStacks() {
        ArrayList<ItemStack> stacks = new ArrayList<>(editors.size());
        for (ItemStackEditorLayout editor : editors) {
            stacks.add(editor.getItemStack());
        }
//        System.out.println("Stacks: " + stacks.toString());
        return stacks;
    }

    public int size() {
        return editors.size();
    }
}
