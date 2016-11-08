package ru.iammaxim.tesitems.GUI.old;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Created by maxim on 11/6/16 at 1:53 AM.
 */
public class GuiAlertDialog extends GuiScreen {
    private String text;
    private GuiFancyFrameLayout layout;
    private ScaledResolution res;

    public GuiAlertDialog(String text) {
        this.text = text;
        mc = Minecraft.getMinecraft();
        res = new ScaledResolution(mc);
        layout = new GuiFancyFrameLayout(null);
        GuiVerticalLinearLayout layout1 = new GuiVerticalLinearLayout(layout);
        layout1.setSpace(5);
        GuiText guiText = new GuiText(layout1, text);
        layout1.add(guiText)
                .add(new GuiButton(layout1, "OK").setOnClick(() -> mc.displayGuiScreen(null)).centerHorizontally(true));
        layout1.width = Math.min(guiText.getWidth(), res.getScaledWidth()/2);
        guiText.calculateWidth();
        layout1.calculateWidth();
        layout.set(layout1);
        layout.matchToChild();
        layout.calculateBounds((res.getScaledHeight() - layout.getHeight())/2, (res.getScaledWidth() - layout.getWidth())/2);
        layout.doLayout();
        layout1.doLayout(layout.element.top, layout.element.left);
//        layout.doLayout();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        layout.draw(mouseX, mouseY);
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
    }
}
