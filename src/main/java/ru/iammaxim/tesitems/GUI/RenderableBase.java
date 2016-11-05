package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

/**
 * Created by maxim on 11/5/16 at 8:41 PM.
 */
public abstract class RenderableBase {
    public RenderableBase parent;
    protected int width, height;
    public int top, bottom, left, right;
    public boolean focused = false;

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

    public void drawColoredRect(Tessellator tess, int top, int left, int bottom, int right, float red, float green, float blue, float alpha) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GlStateManager.color(1, 1, 1, 1);
        VertexBuffer b = tess.getBuffer();
        b.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        b.pos(left, bottom, 0.0D).tex(0, 1).color(red, green, blue, alpha).endVertex();
        b.pos(right, bottom, 0.0D).tex(1, 1).color(red, green, blue, alpha).endVertex();
        b.pos(right, top, 0.0D).tex(1, 0).color(red, green, blue, alpha).endVertex();
        b.pos(left, top, 0.0D).tex(0, 0).color(red, green, blue, alpha).endVertex();
        tess.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void keyTyped(char typedChar, int keyCode) {}
}
