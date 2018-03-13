package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.GUI.Elements.Image;
import ru.iammaxim.tesitems.GUI.Elements.ItemRenderer;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.GUI.Elements.TextField;

/**
 * Created by maxim on 07.03.2017.
 */
public class ItemStackEditorLayout extends HorizontalLayout {
    private ItemStack is;
    private ItemRenderer itemRenderer;

    public ItemStackEditorLayout() {
        this(null);
    }

    public ItemStackEditorLayout(ItemStack itemStack) {
        if (itemStack == null)
            this.is = new ItemStack(Blocks.DIRT);
        else this.is = itemStack;

        add(new VerticalLayout().add(itemRenderer = new ItemRenderer(is)).center(true));
        add(new VerticalLayout()
                .add(new TextField().setHint("Item name").setText(is.getItem().getRegistryName().toString()).setOnType(tf -> {
                    Item item = Item.getByNameOrId(tf.getText());
                    if (item != null) {
                        is = new ItemStack(item, is.stackSize);
                        itemRenderer.setItemStack(is);
                    }
                }).setWidthOverride(FILL))
                .add(new TextField().setHint("Count").setText(String.valueOf(is.stackSize)).setOnType(tf -> {
                    try {
                        int size = Integer.parseInt(tf.getText());
                        is.stackSize = Math.min(Math.max(size, 0), 64);
                    } catch (NumberFormatException e) {
                    }
                }).setWidthOverride(FILL))
                .setWidthOverride(FILL));
    }

    public ItemStack getItemStack() {
        return is;
    }
}
