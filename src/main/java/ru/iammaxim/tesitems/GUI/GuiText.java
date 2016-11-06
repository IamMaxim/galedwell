package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;

import java.util.List;

/**
 * Created by maxim on 11/5/16 at 9:30 PM.
 */
public class GuiText extends RenderableBase {
    public String text;
    public List<String> strs;
    private Minecraft mc;
    private int lineSpacing = 4, color = 0xff481f09;

    public void calculateWidth() {
        width = mc.fontRendererObj.getStringWidth(text);
        if (width > parent.width) width = parent.width;
        if (width == 0) {
            System.out.println("ERROR! GuiText.width == 0. Resetting to 100");
            width = 100;
        }
        strs = mc.fontRendererObj.listFormattedStringToWidth(text, width);
        height = strs.size() * (8 + lineSpacing) - lineSpacing;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getWidth() {
        return mc.fontRendererObj.getStringWidth(text);
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public GuiText(RenderableBase parent, String s) {
        this.parent = parent;
        mc = Minecraft.getMinecraft();
        text = s;
        calculateWidth();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        for (int i = 0; i < strs.size(); i++) {
            mc.fontRendererObj.drawString(strs.get(i), left, top + (8 + lineSpacing) * i, color);
        }
    }
}
