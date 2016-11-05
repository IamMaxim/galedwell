package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;
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
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public GuiText(RenderableBase parent, String s) {
        this.parent = parent;
        mc = Minecraft.getMinecraft();
        text = s;
        calculateWidth();
        strs = mc.fontRendererObj.listFormattedStringToWidth(text, width);
        height = strs.size() * (8 + lineSpacing) - lineSpacing;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        for (int i = 0; i < strs.size(); i++) {
            mc.fontRendererObj.drawString(strs.get(i), left, top + (8 + lineSpacing) * i, color);
        }
    }
}
