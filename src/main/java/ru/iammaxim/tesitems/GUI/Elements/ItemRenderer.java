package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 2/25/17 at 12:15 PM.
 */
public class ItemRenderer extends ElementBase {
    public ItemStack itemStack;
    public RenderItem renderItem = TESItems.getMinecraft().getRenderItem();
    public EntityPlayer player = TESItems.getClientPlayer();

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
        renderItem.renderItemAndEffectIntoGUI(player, itemStack, left, top);
    }

    @Override
    public String getName() {
        return "ItemRenderer";
    }
}
