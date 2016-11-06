package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

/**
 * Created by maxim on 11/6/16 at 1:56 AM.
 */
public class GuiFancyFrameLayout extends RenderableBase {
    private static final int frameSize = 10, padding = 4;
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

    public RenderableBase element;

    public GuiFancyFrameLayout(RenderableBase parent) {
        this.parent = parent;
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
    public void click(int relativeX, int relativeY) {
        element.click(relativeX - padding - frameSize, relativeY - padding - frameSize);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();
        Minecraft mc = Minecraft.getMinecraft();
        float tmp;
        mc.getTextureManager().bindTexture(paper_bg);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(0, 1).endVertex();
        vb.pos(right, bottom, 0).tex(1, 1).endVertex();
        vb.pos(right, top, 0).tex(1, 0).endVertex();
        vb.pos(left, top, 0).tex(0, 0).endVertex();
        tess.draw();

        //LT
        mc.getTextureManager().bindTexture(inv_itemlist_border_LT);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, top + frameSize, 0).tex(0, 1).endVertex();
        vb.pos(left + frameSize, top + frameSize, 0).tex(1, 1).endVertex();
        vb.pos(left + frameSize, top, 0).tex(1, 0).endVertex();
        vb.pos(left, top, 0).tex(0, 0).endVertex();
        tess.draw();

        //CT
        tmp = Math.round((right - left - 2 * frameSize) / frameSize);
        mc.getTextureManager().bindTexture(inv_itemlist_border_CT);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left + frameSize, top + frameSize, 0).tex(0, 1).endVertex();
        vb.pos(right - frameSize, top + frameSize, 0).tex(tmp, 1).endVertex();
        vb.pos(right - frameSize, top, 0).tex(tmp, 0).endVertex();
        vb.pos(left + frameSize, top, 0).tex(0, 0).endVertex();
        tess.draw();

        //CB
        mc.getTextureManager().bindTexture(inv_itemlist_border_CB);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left + frameSize, bottom, 0).tex(0, 1).endVertex();
        vb.pos(right - frameSize, bottom, 0).tex(tmp, 1).endVertex();
        vb.pos(right - frameSize, bottom - frameSize, 0).tex(tmp, 0).endVertex();
        vb.pos(left + frameSize, bottom - frameSize, 0).tex(0, 0).endVertex();
        tess.draw();

        //RT
        mc.getTextureManager().bindTexture(inv_itemlist_border_RT);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(right - frameSize, top + frameSize, 0).tex(0, 1).endVertex();
        vb.pos(right, top + frameSize, 0).tex(1, 1).endVertex();
        vb.pos(right, top, 0).tex(1, 0).endVertex();
        vb.pos(right - frameSize, top, 0).tex(0, 0).endVertex();
        tess.draw();

        //LC
        tmp = Math.round((bottom - top - 2 * frameSize) / frameSize);
        mc.getTextureManager().bindTexture(inv_itemlist_border_LC);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom - frameSize, 0).tex(0, tmp).endVertex();
        vb.pos(left + frameSize, bottom - frameSize, 0).tex(1, tmp).endVertex();
        vb.pos(left + frameSize, top + frameSize, 0).tex(1, 0).endVertex();
        vb.pos(left, top + frameSize, 0).tex(0, 0).endVertex();
        tess.draw();

        //RC
        mc.getTextureManager().bindTexture(inv_itemlist_border_RC);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(right - frameSize, bottom - frameSize, 0).tex(0, tmp).endVertex();
        vb.pos(right, bottom - frameSize, 0).tex(1, tmp).endVertex();
        vb.pos(right, top + frameSize, 0).tex(1, 0).endVertex();
        vb.pos(right - frameSize, top + frameSize, 0).tex(0, 0).endVertex();
        tess.draw();

        //LB
        mc.getTextureManager().bindTexture(inv_itemlist_border_LB);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(0, 1).endVertex();
        vb.pos(left + frameSize, bottom, 0).tex(1, 1).endVertex();
        vb.pos(left + frameSize, bottom - frameSize, 0).tex(1, 0).endVertex();
        vb.pos(left, bottom - frameSize, 0).tex(0, 0).endVertex();
        tess.draw();

        //RB
        mc.getTextureManager().bindTexture(inv_itemlist_border_RB);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(right - frameSize, bottom, 0).tex(0, 1).endVertex();
        vb.pos(right, bottom, 0).tex(1, 1).endVertex();
        vb.pos(right, bottom - frameSize, 0).tex(1, 0).endVertex();
        vb.pos(right - frameSize, bottom - frameSize, 0).tex(0, 0).endVertex();
        tess.draw();

        element.draw(mouseX, mouseY);
    }

    public void matchToChild() {
        width = element.getWidth() + 2 * (padding + frameSize);
        height = element.getHeight() + 2 * (padding + frameSize);
    }
}
