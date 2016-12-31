package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.gui.FontRenderer;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 11/7/16 at 4:56 PM.
 */
public class Text extends ElementBase {
    private String text = "";
    private FontRenderer fontRenderer;
    private List<String> strs = new ArrayList<>();
    private boolean dirty = false;
    private int lineSpacing = 4, lineHeight = 8;
    private int color = 0xff481f09;
    private boolean center = false;
    private int textWidth = 0;
    public int leftPadding = 0;

    public Text setLeftPadding(int padding) {
        leftPadding = padding;
        return this;
    }

    public Text center(boolean needToCenter) {
        center = needToCenter;
        return this;
    }

    public Text(ElementBase parent) {
        super(parent);
        fontRenderer = TESItems.fontRenderer;
    }

    public Text(ElementBase parent, String text) {
        this(parent);
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
        return textWidth = fontRenderer.getStringWidth(text) + leftPadding;
    }

    @Override
    public int getHeight() {
        if (dirty) {
            return 8;
        }
        return strs.size() * lineHeight + (strs.size() - 1) * lineSpacing;
    }

    private void update() {
        if (width == 0) {
            return;
        }
        strs = fontRenderer.listFormattedStringToWidth(text, width);
        dirty = false;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (dirty) {
            ((LayoutBase)getRoot()).doLayout();
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
