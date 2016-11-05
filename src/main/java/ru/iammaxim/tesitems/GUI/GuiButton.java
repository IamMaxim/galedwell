package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

/**
 * Created by maxim on 11/6/16 at 12:35 AM.
 */
public class GuiButton extends RenderableBase {
    private String text;
    private int padding = 6;
    private int color = 0xffffffff;
    private boolean clicked = false;
    private Runnable onClick;

    public GuiButton(RenderableBase parent) {
        this.parent = parent;
        height = padding * 2 + 8;
        setText("A button");
    }

    public GuiButton(RenderableBase parent, String text) {
        this(parent);
        setText(text);
    }

    public void setText(String text) {
        this.text = text;
        width = padding * 2 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
    }

    @Override
    public void click(int relativeX, int relativeY) {
        clicked = true;
        if (onClick != null)
            onClick.run();
    }

    public GuiButton setOnClick(Runnable action) {
        onClick = action;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Tessellator tess = Tessellator.getInstance();

        if (clicked)
            drawColoredRect(tess, top, left, bottom, right, 0.5f, 0.5f, 0.5f, 0.5f);
        else
            drawColoredRect(tess, top, left, bottom, right, 0.3f, 0.3f, 0.3f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString(text, left + padding, top + padding, color);
        clicked = false;
    }
}
