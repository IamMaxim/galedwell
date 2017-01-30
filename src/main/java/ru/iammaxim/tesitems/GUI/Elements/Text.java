package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.gui.FontRenderer;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutBase;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 11/7/16 at 4:56 PM.
 */
public class Text extends ElementBase {
    protected String text = "";
    protected FontRenderer fontRenderer;
    protected List<String> strs = new ArrayList<>();
    protected boolean dirty = false;
    protected int lineSpacing = 4, lineHeight = 8;
    protected int color = 0xff481f09;
    protected boolean center = false;
    protected int textWidth = 0;
    public int leftPadding = 0;

    public Text setLeftPadding(int padding) {
        leftPadding = padding;
        return this;
    }

    public Text center(boolean needToCenter) {
        center = needToCenter;
        return this;
    }

    public Text() {
        fontRenderer = TESItems.fontRenderer;
    }

    public Text(String text) {
        this();
        setText(text);
    }

    public Text setText(String text) {
        this.text = text;
        dirty = true;
        return this;
    }

    public Text setColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public int getWidth() {
        return (fontRenderer.getStringWidth(text)) + leftPadding;
    }

    @Override
    public int getHeight() {
        return Math.max(getNonDirtyHeight(), lineHeight + marginBottom + marginTop);
    }

    public int getNonDirtyHeight() {
        return strs.size() * lineHeight + (strs.size() - 1) * lineSpacing + marginBottom + marginTop;
    }

    protected void update() {
        try {
            ((LayoutBase) getRoot()).doLayout();
            if (width == 0) {
                return;
            }
            strs = fontRenderer.listFormattedStringToWidth(text, width);
            textWidth = fontRenderer.getStringWidth(strs.get(0));
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (dirty) {
            update();
        }

        int x = left + leftPadding;
        int y = top;
        for (String str : strs) {
            if (center) {
                fontRenderer.drawString(str, x + (width - textWidth) / 2, y, color);
                y += lineHeight + lineSpacing;
            } else {
                fontRenderer.drawString(str, x, y, color);
                y += lineHeight + lineSpacing;
            }
        }
    }
}
