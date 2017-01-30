package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.renderer.Tessellator;

/**
 * Created by maxim on 11/8/16 at 7:36 PM.
 */
public class VerticalDivider extends ElementBase {
    private int color = 0xff481f09;

    public void setColor(int color) {
        this.color = color;
    }

    public VerticalDivider() {
        width = 1;
    }

    @Override
    public int getHeight() {
        return FILL;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        drawColoredRect(Tessellator.getInstance(), left, top, right, bottom, color);
    }
}
