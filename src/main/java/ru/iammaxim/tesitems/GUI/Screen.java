package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import ru.iammaxim.tesitems.GUI.Elements.FancyFrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.FrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.ScreenCenteredLayout;

import java.io.IOException;

/**
 * Created by maxim on 11/9/16 at 9:34 PM.
 */
public class Screen extends GuiScreen {
    protected Minecraft mc;
    protected ScaledResolution res;
    protected ScreenCenteredLayout root;
    protected FrameLayout contentLayout;
    protected boolean wasClicked = false;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public Screen() {
        mc = Minecraft.getMinecraft();
        res = new ScaledResolution(mc);
        root = new ScreenCenteredLayout(null);
        contentLayout = new FancyFrameLayout(root);
        root.setElement(contentLayout);
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        res = new ScaledResolution(mcIn);
        int width = root.getWidth();
        int height = root.getHeight();
        root.setBounds((res.getScaledWidth() - width)/2, (res.getScaledHeight() - height)/2, (res.getScaledWidth() + width)/2, (res.getScaledHeight() + height)/2);
        root.doLayout();
        root.onRescale();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Mouse.isButtonDown(0)) {
            if (!wasClicked) {
                System.out.println("going to check click");
                root.checkClick(mouseX, mouseY);
                wasClicked = true;
            }
        } else {
            wasClicked = false;
        }
        root.checkHover(mouseX, mouseY);
        root.draw(mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        root.keyTyped(typedChar, keyCode);
    }
}
