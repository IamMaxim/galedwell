package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by maxim on 11/5/16 at 8:40 PM.
 */
public class GuiVerticalLinearLayout extends RenderableBase {
    public ArrayList<RenderableBase> elements = new ArrayList<>();
    private int space = 0;
    private int minWidth = 0;
    private int scroll = 0;

    @Override
    public void checkClick(int mouseX, int mouseY) {
        elements.forEach(e -> {
            if (mouseX > e.left && mouseX < e.right && mouseY > e.top && mouseY < e.bottom) {
                if (!e.focused)
                    e.onFocus();
                e.focused = true;
                e.click(mouseX - left, mouseY - top);
            } else {
                if (e.focused)
                    e.onFocusLost();
                e.focused = false;
            }
        });
    }

    private int paddingTop = 0;
    private int paddingLeft = 0;
    private int paddingBottom = 0;
    private int paddingRight = 0;
    private ScaledResolution res;
    private Minecraft mc;

    public GuiVerticalLinearLayout() {
        mc = Minecraft.getMinecraft();
        resize();
    }

    public void add(RenderableBase element) {
        elements.add(element);
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public void setMinWidth(int width) {
        this.minWidth = width;
        if (this.width < minWidth) this.width = minWidth;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
    }

    public void setPadding(int left, int top, int right, int bottom) {
        this.paddingBottom = bottom;
        this.paddingTop = top;
        this.paddingLeft = left;
        this.paddingRight = right;
    }

    public void setBounds(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.width = right - left;
        this.height = bottom - top;
    }

    public void resize() {
        res = new ScaledResolution(mc);
    }

    public void doLayout(int top, int left) {
        this.top = top;
        this.left = left;
        int tmpHeight = top - scroll;
        for (RenderableBase e : elements) {
            e.calculateBounds(tmpHeight, left);
            tmpHeight += e.getHeight() + space;
        }
    }

    public int calculateWidth() {
        int w = minWidth, tmp;
        for (RenderableBase e : elements) {
            if ((tmp = e.width) > w) w = tmp;
        }
        return w;
    }

    public void draw(int mouseX, int mouseY) {
        if (Mouse.isButtonDown(0)) {
            checkClick(mouseX, mouseY);
        }

        Tessellator tess = Tessellator.getInstance();

        float scaleX = mc.displayWidth / res.getScaledWidth(),
                scaleY = mc.displayHeight / res.getScaledHeight();

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int)((left - paddingLeft) * scaleX), (int)(mc.displayHeight - (bottom + paddingBottom) * scaleY), (int)((width + paddingLeft + paddingRight) * scaleX), (int)((height + paddingTop + paddingBottom) * scaleY));

        drawColoredRect(tess, top - paddingTop, left - paddingLeft, bottom + paddingBottom, right + paddingBottom, 0, 0, 0, 0.5f);
        elements.forEach(e -> e.draw(mouseX, mouseY));

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        elements.forEach(e -> e.keyTyped(typedChar, keyCode));
    }
}
