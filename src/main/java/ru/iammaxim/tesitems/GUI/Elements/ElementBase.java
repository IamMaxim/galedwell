package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by maxim on 11/7/16 at 4:22 PM.
 */
public abstract class ElementBase {
    protected int left;
    protected int top;
    protected int right;
    protected int bottom;
    protected int width;
    protected int height;

    public void setParent(ElementBase parent) {
        this.parent = parent;
    }

    protected ElementBase parent;
    protected int marginLeft = 0;
    protected int marginRight = 0;
    protected int marginTop = 0;
    protected int marginBottom = 0;
    protected boolean focused;

    public int width() {
        return width;
    }

    //force set this.width
    public void _setwidth(int width) {
        this.width = width;
    }

    public int height() {
        return height;
    }

    public ElementBase() {}

    public static void drawColoredRect(Tessellator tess, int left, int top, int right, int bottom, int color) {
        float b = (float)(color & 0xFF)/255;
        float g = (float)((color >> 8) & 0xFF)/255;
        float r = (float)((color >> 16) & 0xFF)/255;
        float a = (float)((color >> 24) & 0xFF)/255;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GlStateManager.color(r, g, b, a);
        VertexBuffer buf = tess.getBuffer();
        buf.begin(7, DefaultVertexFormats.POSITION);
        buf.pos(left, bottom, 0.0D).endVertex();
        buf.pos(right, bottom, 0.0D).endVertex();
        buf.pos(right, top, 0.0D).endVertex();
        buf.pos(left, top, 0.0D).endVertex();
        tess.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.disableBlend();
    }

    public void checkClick(int mouseX, int mouseY) {
        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom) {
            click(mouseX - left, mouseY - top);
        }
    }

    public void checkHover(int mouseX, int mouseY) {
        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom) {
            if (!focused)
                onFocus();
            focused = true;
        } else {
            if (focused)
                onFocusLost();
            focused = false;
        }
    }

    public void onFocusLost() {
    }

    public void onFocus() {
    }

    public ElementBase getParent() {
        return parent;
    }

    public ElementBase getRoot() {
        if (parent == null) return this;
        return parent.getRoot();
    }

    public int getLeftMargin() {
        return marginLeft;
    }

    public int getRightMargin() {
        return marginRight;
    }

    public ElementBase setLeftMargin(int margin) {
        this.marginLeft = margin;
        return this;
    }

    public ElementBase setRightMargin(int margin) {
        this.marginRight = margin;
        return this;
    }

    public int getTopMargin() {
        return marginTop;
    }

    public int getBottomMargin() {
        return marginTop;
    }

    public ElementBase setVerticalMargin(int margin) {
        this.marginTop = margin;
        this.marginBottom = margin;
        return this;
    }

    public ElementBase setTopMargin(int margin) {
        this.marginTop = margin;
        return this;
    }

    public ElementBase setBottomMargin(int margin) {
        this.marginBottom = margin;
        return this;
    }

    public int getWidth() {
        return width + marginLeft + marginRight;
    }

    public int getHeight() {
        return height + marginBottom + marginTop;
    }

    public void setBounds(int left, int top, int right, int bottom) {
        this.left = left + marginLeft;
        this.right = right - marginRight;
        this.top = top + marginTop;
        this.bottom = bottom - marginBottom;
        this.width = this.right - this.left;
        this.height = this.bottom - this.top;

        System.out.println(getPath() + "\nset bounds: " + this.left + " " + this.top + " " + this.right + " " + this.bottom + " " + this.width + " " + this.height);
    }

    public abstract void draw(int mouseX, int mouseY);

    public void keyTyped(char c, int keyCode) {}

    public void click(int relativeX, int relativeY) {}

    public void drawTexturedRect(Tessellator tess, int left, int top, int right, int bottom, ResourceLocation texture) {
        boolean isTexture2Denabled = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
        if (!isTexture2Denabled)
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        VertexBuffer vb = tess.getBuffer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(0, 1).endVertex();
        vb.pos(right, bottom, 0).tex(1, 1).endVertex();
        vb.pos(right, top, 0).tex(1, 0).endVertex();
        vb.pos(left, top, 0).tex(0, 0).endVertex();
        tess.draw();
        GlStateManager.disableBlend();
        if (!isTexture2Denabled)
            GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public void drawTexturedRect(Tessellator tess, int left, int top, int right, int bottom, float UVx, float UVy, ResourceLocation texture) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        VertexBuffer vb = tess.getBuffer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(0, UVy).endVertex();
        vb.pos(right, bottom, 0).tex(UVx, UVy).endVertex();
        vb.pos(right, top, 0).tex(UVx, 0).endVertex();
        vb.pos(left, top, 0).tex(0, 0).endVertex();
        tess.draw();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public void onResize() {}

    public ElementBase setHorizontalMargin(int margin) {
        this.marginLeft = this.marginRight = margin;
        return this;
    }

    public String getPath() {
        ElementBase e = this;
        ArrayList<String> path = new ArrayList<>();

        while (e != null) {
            path.add(e.getClass().getSimpleName());
            e = e.getParent();
        }

        ArrayList<String> pathInverted = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--)
            pathInverted.add(path.get(i));
        return String.join(" -> ", pathInverted);
    }
}
