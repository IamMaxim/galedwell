package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 2/25/17 at 12:15 PM.
 */
public class ItemRenderer extends ElementBase {
    public ItemStack itemStack;
    public RenderItem renderItem = TESItems.getMinecraft().getRenderItem();

    @Override
    public int getWidth() {
        return 16 + marginLeft + marginRight;
    }

    @Override
    public int getHeight() {
        return 16 + marginTop + marginBottom;
    }

    public ItemRenderer(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.renderItemAndEffectIntoGUI(itemStack, left, top);
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public String getName() {
        return "ItemRenderer";
    }
}
