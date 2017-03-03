package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by maxim on 2/11/17 at 7:46 PM.
 */
public class ScreenStack extends GuiScreen {
    public static ScreenStack instance;
    protected boolean wasClicked = false, wasRightClicked = false;
    private ArrayList<Screen> screens = new ArrayList<>();

    public ScreenStack() {
        mc = Minecraft.getMinecraft();
    }

    public static void addScreen(Screen screen) {
        instance._addScreen(screen);
    }

    public static Screen lastScreen() {
        if (instance.screens.isEmpty()) return null;
        return instance.screens.get(instance.screens.size() - 1);
    }

    public static void close() {
        instance._close();
    }

    public static void forceClose() {
        instance.screens.remove(instance.screens.size() - 1);
    }

    private void _addScreen(Screen screen) {
        screens.add(screen);

        if (mc.currentScreen == null || mc.currentScreen != this) {
            mc.displayGuiScreen(this);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //check shaders
        if (ResManager.gaussianBlurShader.getFramebufferRaw("swap").framebufferTextureWidth != mc.displayWidth ||
                ResManager.gaussianBlurShader.getFramebufferRaw("swap").framebufferTextureHeight != mc.displayHeight)
            ResManager.gaussianBlurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);

        for (int i = 0; i < screens.size() - 1; i++)
            screens.get(i).drawScreen(-1, -1, partialTicks);
        if (screens.size() > 0) {
            screens.get(screens.size() - 1).drawScreen(mouseX, mouseY, partialTicks);

            if (Mouse.isButtonDown(0)) {
                if (!wasClicked) {
                    lastScreen().checkClick(mouseX, mouseY);
                    wasClicked = true;
                }
            } else {
                wasClicked = false;
            }

            if (Mouse.isButtonDown(1)) {
                if (!wasRightClicked) {
                    lastScreen().checkRightClick(mouseX, mouseY);
                    wasRightClicked = true;
                }
            } else {
                wasRightClicked = false;
            }

            NotificationManager.draw();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            close();
        } else if (screens.size() > 0)
            screens.get(screens.size() - 1).keyTyped(typedChar, keyCode);
    }

    private void _close() {
        if (screens.isEmpty()) return;

        if (screens.get(screens.size() - 1).close())
            screens.remove(screens.size() - 1);

        if (screens.size() == 0) {
            mc.setIngameFocus();
        }
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        screens.forEach(s -> s.onResize(mcIn, w, h));
    }
}
