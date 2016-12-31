package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by maxim on 11/7/16 at 4:19 PM.
 */
public class HorizontalLayout extends LayoutBase {
    private int spacing = 4;
    private ArrayList<ElementBase> elements = new ArrayList<>();

    public HorizontalLayout(ElementBase parent) {
        super(parent);
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public HorizontalLayout add(ElementBase element) {
        elements.add(element);
        return this;
    }

    @Override
    public void keyTyped(char c, int keyCode) {
        elements.forEach(e -> e.keyTyped(c, keyCode));
    }

    @Override
    public void checkClick(int mouseX, int mouseY) {
        try {
            elements.forEach(e -> e.checkClick(mouseX, mouseY));
        } catch (ConcurrentModificationException e) {
        }
    }

    @Override
    public void doLayout() {
        int y = top + padding;
        int x = left + padding;
        for (ElementBase element : elements) {
            int w = element.getWidth();
            int h = height - 2 * padding;
            element.setBounds(x, y, x + w, y + h);
            if (element instanceof LayoutBase)
                ((LayoutBase) element).doLayout();
            x += w + spacing;
        }
    }

    @Override
    public int getHeight() {
        int height = 0;
        for (ElementBase e : elements) {
            int h = e.getHeight();
            if (parent != null)
                h = (int) Math.min(h, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() * 0.8 - 2 * padding);
            if (h > height) height = h;
        }
        height += 2 * padding;
        return height;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        elements.forEach(e -> e.draw(mouseX, mouseY));
    }

    @Override
    public int getWidth() {
        int width = 0;
        for (ElementBase e : elements) {
            width += e.getWidth();
        }
        width += (elements.size() - 1) * spacing;
        width += 2 * padding;
        return width;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public void checkHover(int mouseX, int mouseY) {
        elements.forEach(e -> e.checkHover(mouseX, mouseY));
    }

    @Override
    public void onRescale() {
        elements.forEach(ElementBase::onRescale);
    }
}
