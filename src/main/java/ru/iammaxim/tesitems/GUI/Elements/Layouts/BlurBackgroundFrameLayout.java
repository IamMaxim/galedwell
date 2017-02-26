package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import ru.iammaxim.tesitems.GUI.ResManager;

/**
 * Created by maxim on 2/11/17 at 6:52 PM.
 */
public class BlurBackgroundFrameLayout extends FrameLayout {
    private ScaledResolution res;

    public BlurBackgroundFrameLayout() {
        res = new ScaledResolution(Minecraft.getMinecraft());
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (ResManager.enableBlur) {
            drawBlur(res, left, top, right, bottom);
        }

        super.draw(mouseX, mouseY);
    }
}
