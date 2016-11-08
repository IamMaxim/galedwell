package ru.iammaxim.tesitems.GUI.old;

import net.minecraft.client.renderer.Tessellator;

/**
 * Created by maxim on 11/6/16 at 12:56 AM.
 */
public class GuiDivider extends RenderableBase {
    private int padding = 3;

    public GuiDivider(RenderableBase parent) {
        this.parent = parent;
        height = 7;
        width = parent.width;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        drawColoredRect(Tessellator.getInstance(), top + padding, left + padding, bottom - padding, right - padding, 0xFF4C4C4C);
    }
}
