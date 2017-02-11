package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import ru.iammaxim.tesitems.GUI.ResManager;

/**
 * Created by maxim on 2/11/17 at 6:46 PM.
 */
public class DarkBackgroundFrameLayout extends WrapFrameLayout {

    public DarkBackgroundFrameLayout() {
        setFrameColor(ResManager.DARK_FRAME_COLOR);
    }

    @Override
    public String getName() {
        return "DarkBackgroundFrameLayout";
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        drawColoredRect(Tessellator.getInstance(), left, top, right, bottom, ResManager.DARK_TRANSPARENT_BG_COLOR);
        super.draw(mouseX, mouseY);
    }
}
