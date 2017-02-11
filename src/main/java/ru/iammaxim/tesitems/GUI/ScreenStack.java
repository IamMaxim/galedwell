package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by maxim on 2/11/17 at 7:46 PM.
 */
public class ScreenStack extends GuiScreen {
    public static ScreenStack instance;
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
        Minecraft.getMinecraft().addScheduledTask(() -> instance._close());
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
        for (int i = 0; i < screens.size(); i++)
            screens.get(i).drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            close();
        } else
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
    }
}
