package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import ru.iammaxim.tesitems.TESItems;

import static ru.iammaxim.tesitems.GUI.ResManager.*;

/**
 * Created by maxim on 11/8/16 at 5:50 PM.
 */
public class Button extends ElementBase {
    private String text = "A button";
    private int padding = 4, textColor = 0xff481f09;
    private FontRenderer fontRenderer;
    private boolean useInactiveBackground = true, center = true;
    private int textWidth;

    public Button center(boolean center) {
        this.center = center;
        return this;
    }

    public Button setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public int getPadding() {
        return padding;
    }

    @Override
    public int getWidth() {
        return textWidth + 2 * padding;
    }

    @Override
    public int getHeight() {
        return padding * 2 + 8;
    }

    public Button() {
        fontRenderer = TESItems.ClientThings.fontRenderer;
    }

    public Button(String text) {
        this();
        setText(text);
    }

    public Button setText(String text) {
        this.text = text;
        textWidth = fontRenderer.getStringWidth(text);
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public void checkClick(int mouseX, int mouseY) {
        int _padding = (width - textWidth) / 2 - padding;
        if (mouseX > left + _padding && mouseX < right - _padding && mouseY > top && mouseY < bottom) {
            click(mouseX - left, mouseY - top);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Tessellator tess = Tessellator.getInstance();
        int _padding = (width - textWidth) / 2 - padding;
        int tmp_w = width - 2 * _padding;
        boolean hovered = false;
        if (mouseX > left + _padding && mouseX < right - _padding && mouseY > top && mouseY < bottom)
            hovered = true;
        if (!(!useInactiveBackground && !hovered)) {
//            drawTexturedRect(tess, left + _padding, top, (int) (right + (width - _padding) * getMultiplier(width - _padding) - _padding), bottom + height, getTexture(width, hovered));
            drawTexturedRect(tess,
                    left + _padding,
                    top,
                    (int) (right - _padding + tmp_w * getMultiplier(tmp_w)),
                    bottom + height, //because button texture is only an upper half
                    getTexture(tmp_w, hovered));
        }
        fontRenderer.drawString(text, center ? left + (width - fontRenderer.getStringWidth(text))/2 : left + padding, top + padding, textColor);
    }

    @Override
    public String getName() {
        return "Button";
    }

    public float getMultiplier(int width) {
        if (width < 81)
            return 0.58f;
        else if (width > 136)
            return 0.29f;
        else return 0.88f;
    }

    public ResourceLocation getTexture(int width, boolean on) {
        if (width < 81)
            if (on)
                return button_short_on;
            else
                return button_short;
        else if (width > 136)
            if (on)
                return button_xtralong_on;
            else return button_xtralong;
        else if (on)
            return button_long_on;
        else
            return button_long;
    }

    public Button setUseInactiveBackground(boolean useInactiveBackground) {
        this.useInactiveBackground = useInactiveBackground;
        return this;
    }
}
