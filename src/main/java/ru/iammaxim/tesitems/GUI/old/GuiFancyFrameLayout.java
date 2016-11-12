package ru.iammaxim.tesitems.GUI.old;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by maxim on 11/6/16 at 1:56 AM.
 */
public class GuiFancyFrameLayout extends RenderableBase {
    private static final int frameSize = 18;
    private int padding = 8;
    private static ResourceLocation
            inv_itemlist_border_LT = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_LT.png"),
            inv_itemlist_border_CT = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_CT.png"),
            inv_itemlist_border_RT = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_RT.png"),
            inv_itemlist_border_LC = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_LC.png"),
            inv_itemlist_border_RC = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_RC.png"),
            inv_itemlist_border_LB = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_LB.png"),
            inv_itemlist_border_CB = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_CB.png"),
            inv_itemlist_border_RB = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_RB.png"),
            paper_bg = new ResourceLocation("tesitems:textures/gui/generic_bg.png");

    public RenderableBase element;

    public GuiFancyFrameLayout(RenderableBase parent) {
        this.parent = parent;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public GuiFancyFrameLayout set(RenderableBase element) {
        this.element = element;
        return this;
    }

    @Override
    public int getWidth() {
        return element.getWidth() + (frameSize + padding) * 2;
    }

    @Override
    public int getHeight() {
        return element.getHeight() + (frameSize + padding) * 2;
    }

    public void doLayout() {
        element.left = left + frameSize + padding;
        element.right = right - frameSize - padding;
        element.top = top + frameSize + padding;
        element.bottom = bottom - frameSize - padding;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();
        Minecraft mc = Minecraft.getMinecraft();
        float tmp;

        drawTexturedRect(left, top, right, bottom, paper_bg);
        drawTexturedRect(left, top, left + frameSize, top + frameSize, inv_itemlist_border_LT);
        tmp = (right - left - 2 * frameSize) / 22.75f / frameSize;
        drawTexturedRect(left + frameSize, top, right - frameSize, top + frameSize, tmp, 1, inv_itemlist_border_CT);
        drawTexturedRect(left + frameSize, bottom - frameSize, right - frameSize, bottom, tmp, 1, inv_itemlist_border_CB);
        drawTexturedRect(right - frameSize, top, right, top + frameSize, inv_itemlist_border_RT);
        tmp = (bottom - top - 2 * frameSize) / 22.75f / frameSize;
        drawTexturedRect(left, top + frameSize, left + frameSize, bottom - frameSize, 1, tmp, inv_itemlist_border_LC);
        drawTexturedRect(right - frameSize, top + frameSize, right, bottom - frameSize, 1, tmp, inv_itemlist_border_RC);
        drawTexturedRect(left, bottom - frameSize, left + frameSize, bottom, inv_itemlist_border_LB);
        drawTexturedRect(right - frameSize, bottom - frameSize, right, bottom, inv_itemlist_border_RB);
        element.draw(mouseX, mouseY);
    }

    public void matchToChild() {
        width = element.getWidth() + 2 * (padding + frameSize);
        height = element.getHeight() + 2 * (padding + frameSize);
    }
}
