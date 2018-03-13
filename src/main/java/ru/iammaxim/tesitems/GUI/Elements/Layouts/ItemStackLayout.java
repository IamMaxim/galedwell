package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.GUI.Elements.ItemRenderer;
import ru.iammaxim.tesitems.GUI.Elements.Text;

/**
 * Created by maxim on 07.03.2017.
 */
public class ItemStackLayout extends HorizontalLayout {
    private ItemStack is;
    private ItemRenderer itemRenderer;

    public ItemStackLayout(ItemStack itemStack) {
        if (itemStack == null)
            this.is = new ItemStack(Blocks.DIRT);
        else this.is = itemStack;

        add(new VerticalLayout().add(itemRenderer = new ItemRenderer(is)).center(true));
        add(new VerticalLayout().add(new Text(is.getDisplayName() + (is.stackSize == 1 ? "" : " (" + is.stackSize + ")"))).center(true).setHeightOverride(FILL));
    }

    public ItemStack getItemStack() {
        return is;
    }
}
