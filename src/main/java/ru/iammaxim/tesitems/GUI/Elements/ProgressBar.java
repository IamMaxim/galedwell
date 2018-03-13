package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.renderer.Tessellator;

public class ProgressBar extends ElementBase {
    private float progress = 0;

    public ProgressBar setProgress(float progress) {
        if (progress < 0)
            progress = 0;
        if (progress > 1)
            progress = 1;

        this.progress = progress;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        drawColoredRect(Tessellator.getInstance(), left, top, right, bottom, 0x55555555);
        drawColoredRect(Tessellator.getInstance(), left, top, (int) (left + (right - left) * progress), bottom, 0xffff5555);
    }

    @Override
    public String getName() {
        return "ProgressBar";
    }
}
