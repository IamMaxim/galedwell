package ru.iammaxim.tesitems.GUI.elements;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

/**
 * Created by maxim on 11/7/16 at 4:38 PM.
 */
public class FancyFrameLayout extends FrameLayout {
    private int frameSize = 18;
    private static ResourceLocation
            inv_itemlist_border_LT = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_LT.png"),
            inv_itemlist_border_CT = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_CT.png"),
            inv_itemlist_border_RT = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_RT.png"),
            inv_itemlist_border_LC = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_LC.png"),
            inv_itemlist_border_RC = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_RC.png"),
            inv_itemlist_border_LB = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_LB.png"),
            inv_itemlist_border_CB = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_CB.png"),
            inv_itemlist_border_RB = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_RB.png"),
            paper_bg = new ResourceLocation("tesitems:textures/gui/paper_bg.png");

    public FancyFrameLayout(ElementBase parent) {
        super(parent);
        setPadding(8);
    }

    @Override
    public void setPadding(int padding) {
        super.setPadding(padding + frameSize);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Tessellator tess = Tessellator.getInstance();
        float tmp;

        //draw a frame
        drawTexturedRect(tess, left, top, right, bottom, paper_bg);
        drawTexturedRect(tess, left, top, left + frameSize, top + frameSize, inv_itemlist_border_LT);
        tmp = (right - left - 2 * frameSize) / 22.75f / frameSize;
        drawTexturedRect(tess, left + frameSize, top, right - frameSize, top + frameSize, tmp, 1, inv_itemlist_border_CT);
        drawTexturedRect(tess, left + frameSize, bottom - frameSize, right - frameSize, bottom, tmp, 1, inv_itemlist_border_CB);
        drawTexturedRect(tess, right - frameSize, top, right, top + frameSize, inv_itemlist_border_RT);
        tmp = (bottom - top - 2 * frameSize) / 22.75f / frameSize;
        drawTexturedRect(tess, left, top + frameSize, left + frameSize, bottom - frameSize, 1, tmp, inv_itemlist_border_LC);
        drawTexturedRect(tess, right - frameSize, top + frameSize, right, bottom - frameSize, 1, tmp, inv_itemlist_border_RC);
        drawTexturedRect(tess, left, bottom - frameSize, left + frameSize, bottom, inv_itemlist_border_LB);
        drawTexturedRect(tess, right - frameSize, bottom - frameSize, right, bottom, inv_itemlist_border_RB);

        super.draw(mouseX, mouseY);
    }
}
