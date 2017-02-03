package ru.iammaxim.tesitems.GUI.old;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 11/6/16 at 12:35 AM.
 */
public class GuiButton extends RenderableBase {
    private static ResourceLocation
            button_short = new ResourceLocation("tesitems:textures/gui/button_short.png"),
            button_short_on = new ResourceLocation("tesitems:textures/gui/button_short_on.png"),
            button_long = new ResourceLocation("tesitems:textures/gui/button_long.png"),
            button_long_on = new ResourceLocation("tesitems:textures/gui/button_long_on.png"),
            button_xtralong = new ResourceLocation("tesitems:textures/gui/button_xtralong.png"),
            button_xtralong_on = new ResourceLocation("tesitems:textures/gui/button_xtralong_on.png");
    private String text;
    private int padding = 6;
    private int color = 0xff481f09;
    private boolean clicked = false;
    private Runnable onClick;

    public GuiButton(RenderableBase parent) {
        this.parent = parent;
        height = 16;
        setText("A button");
    }

    public GuiButton(RenderableBase parent, String text) {
        this(parent);
        setText(text);
    }

    public void setText(String text) {
        this.text = text;
        width = padding * 3 + TESItems.ClientThings.fontRenderer.getStringWidth(text);
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
        boolean hovered = false;
        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom)
            hovered = true;
        drawTexturedRect(left, top, (int) (right + width * getMultiplier(width)), bottom + height, getTexture(width, clicked || hovered));
        TESItems.ClientThings.fontRenderer.drawString(text, (int) (left + padding * 1.5f), top + 4, color);
        clicked = false;
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
