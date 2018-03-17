package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.gui.FontRenderer;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutBase;
import ru.iammaxim.tesitems.GUI.ResManager;
import ru.iammaxim.tesitems.Utils.ClientThings;

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
    protected int color = ResManager.MAIN_COLOR;
    protected boolean center = false;
    protected ArrayList<Integer> textWidths = new ArrayList<>();
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
        fontRenderer = ClientThings.fontRenderer;
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
        String[] strs = text.split("\n");
        int max = 0;
        for (String str : strs) {
            int w = fontRenderer.getStringWidth(str);
            if (w > max)
                max = w;
        }
        return max + leftPadding;
    }

    @Override
    public int getHeight() {
        return Math.max(getNonDirtyHeight(), lineHeight + marginBottom + marginTop);
    }

    public int getNonDirtyHeight() {
        return strs.size() * lineHeight + (strs.size() - 1) * lineSpacing + marginBottom + marginTop;
    }

    public void update() {
        try {
            ((LayoutBase) getRoot()).doLayout();
            if (width == 0) {
                return;
            }
            strs = fontRenderer.listFormattedStringToWidth(text, width);
            textWidths.clear();
            for (String str : strs) {
                textWidths.add(fontRenderer.getStringWidth(str));
            }
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
        super.draw(mouseX, mouseY);

        int x = left + leftPadding;
        int y = top;
        for (int i = 0; i < strs.size(); i++) {
            String str = strs.get(i);
            if (center) {
                fontRenderer.drawString(str, x + (width - textWidths.get(i)) / 2, y, color);
                y += lineHeight + lineSpacing;
            } else {
                fontRenderer.drawString(str, x, y, color);
                y += lineHeight + lineSpacing;
            }
        }
    }

    @Override
    public String getName() {
        return "Text";
    }
}
