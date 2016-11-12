package ru.iammaxim.tesitems.GUI.elements;

import net.minecraft.client.renderer.Tessellator;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 11/9/16 at 6:38 PM.
 */
public class CheckBox extends ElementBase {
    private boolean checked = false;
    private String text = "";
    private int textColor = 0xff481f09;
    private CheckBoxOnClick onClick;

    public CheckBox setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    public CheckBox setOnClick(CheckBoxOnClick onClick) {
        this.onClick = onClick;
        return this;
    }

    public CheckBox(ElementBase parent) {
        super(parent);
    }

    public CheckBox setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public int getWidth() {
        return TESItems.fontRenderer.getStringWidth(text) + 8;
    }

    @Override
    public void click(int relativeX, int relativeY) {
        checked = !checked;
        if (onClick != null)
            onClick.click(this, checked);
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        int color = 0x90000000;
        if (checked)
            color = 0xffffffff;
        drawColoredRect(Tessellator.getInstance(), left + 1, top + 1, left + 7, bottom - 1, color);
        TESItems.fontRenderer.drawString(text, left + 8, top, textColor);
    }
}
