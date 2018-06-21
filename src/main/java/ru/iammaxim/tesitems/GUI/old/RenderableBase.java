package ru.iammaxim.tesitems.GUI.old;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by maxim on 11/5/16 at 8:41 PM.
 */
public abstract class RenderableBase {
    public RenderableBase parent;
    protected int width, height;
    public int top, bottom, left, right;
    public boolean focused = false;
    public boolean centerHorizontally = false;

    public RenderableBase centerHorizontally(boolean center) {
        centerHorizontally = center;
        return this;
    }

    public void calculateBounds(int top, int left) {
        this.top = top;
        this.left = left;
        this.bottom = top + getHeight();
        this.right = left + getWidth();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void checkClick(int mouseX, int mouseY) {
        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom) {
            if (!focused)
                onFocus();
            focused = true;
            click(mouseX - left, mouseY - top);
        } else {
            if (focused)
                onFocusLost();
            focused = false;
        }
    }

    public void onFocus() {}

    public void onFocusLost() {}

    public void click(int relativeX, int relativeY) {}

    public abstract void draw(int mouseX, int mouseY);

    public static void drawColoredRect(Tessellator tess, int top, int left, int bottom, int right, int color) {
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

    public void drawTexturedRect(int left, int top, int right, int bottom, ResourceLocation texture) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(0, 1).endVertex();
        vb.pos(right, bottom, 0).tex(1, 1).endVertex();
        vb.pos(right, top, 0).tex(1, 0).endVertex();
        vb.pos(left, top, 0).tex(0, 0).endVertex();
        tess.draw();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public void drawTexturedRect(int left, int top, int right, int bottom, float UVx, float UVy, ResourceLocation texture) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        Tessellator tess = Tessellator.getInstance();
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

    public void keyTyped(char typedChar, int keyCode) {}
}
