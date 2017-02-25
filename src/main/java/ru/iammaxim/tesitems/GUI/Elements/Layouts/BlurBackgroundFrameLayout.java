package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
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
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(left * res.getScaleFactor(),
                    (res.getScaledHeight() - bottom) * res.getScaleFactor(),
                    width * res.getScaleFactor(),
                    height * res.getScaleFactor());
            ResManager.gaussianBlurShader.loadShaderGroup(0);
            Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(false);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

        super.draw(mouseX, mouseY);
    }
}
