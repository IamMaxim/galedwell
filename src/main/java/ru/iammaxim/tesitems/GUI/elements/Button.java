package ru.iammaxim.tesitems.GUI.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import ru.iammaxim.tesitems.TESItems;

import java.util.function.Consumer;

/**
 * Created by maxim on 11/8/16 at 5:50 PM.
 */
public class Button extends ElementBase {
    public Consumer<Button> onClick;
    private String text = "A button";
    private int padding = 4, textColor = 0xff481f09;
    private FontRenderer fontRenderer;
    private static ResourceLocation
            button_short = new ResourceLocation("tesitems:textures/gui/button_short.png"),
            button_short_on = new ResourceLocation("tesitems:textures/gui/button_short_on.png"),
            button_long = new ResourceLocation("tesitems:textures/gui/button_long.png"),
            button_long_on = new ResourceLocation("tesitems:textures/gui/button_long_on.png"),
            button_xtralong = new ResourceLocation("tesitems:textures/gui/button_xtralong.png"),
            button_xtralong_on = new ResourceLocation("tesitems:textures/gui/button_xtralong_on.png");

    public Button setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public int getPadding() {
        return padding;
    }

    @Override
    public int getWidth() {
        return fontRenderer.getStringWidth(text) + 2 * padding;
    }

    @Override
    public int getHeight() {
        return padding * 2 + 8;
    }

    public Button(ElementBase parent) {
        super(parent);
        fontRenderer = TESItems.fontRenderer;
    }

    public Button setOnClick(Consumer<Button> onCLick) {
        this.onClick = onCLick;
        return this;
    }

    @Override
    public void click(int relativeX, int relativeY) {
        onClick.accept(this);
    }

    public Button setText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Tessellator tess = Tessellator.getInstance();
        boolean hovered = false;
        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom)
            hovered = true;
        drawTexturedRect(tess, left, top, (int) (right + width * getMultiplier(width)), bottom + height, getTexture(width, hovered));
        fontRenderer.drawString(text, left + padding, top + padding, textColor);
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
}
