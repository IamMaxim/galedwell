package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.renderer.Tessellator;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;

/**
 * Created by maxim on 11/8/16 at 7:36 PM.
 */
public class HorizontalDivider extends ElementBase {
    private int color = 0xff481f09;

    public HorizontalDivider() {
        setWidthOverride(FILL);
        height = 1;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        drawColoredRect(Tessellator.getInstance(), left, top, right, bottom, color);
    }
}
