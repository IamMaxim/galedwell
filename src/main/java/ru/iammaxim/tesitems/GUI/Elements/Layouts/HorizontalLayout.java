package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.LayoutBase;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by maxim on 11/7/16 at 4:19 PM.
 */
public class HorizontalLayout extends LayoutBase {
    private int spacing = 4;
    private ArrayList<ElementBase> elements = new ArrayList<>();
    private boolean center = false;

    public HorizontalLayout center(boolean center) {
        this.center = center;
        return this;
    }

    public HorizontalLayout() {}

    public int getSpacing() {
        return spacing;
    }

    public HorizontalLayout setSpacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    public HorizontalLayout add(ElementBase element) {
        elements.add(element);
        element.setParent(this);
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
        int _w = getWidth();
        int y = top + paddingTop;
        int x;
        if (center) x = left + (width - _w) / 2;
        else x = left + paddingLeft;
        for (ElementBase element : elements) {
            int w = element.getWidth();
            int h = height - paddingTop - paddingBottom;
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
                h = (int) Math.min(h, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() * 0.8 - paddingTop - paddingBottom);
            if (h > height) height = h;
        }
        height += paddingTop + paddingBottom;
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
        width += paddingLeft + paddingRight;
        return width;
    }

    public int getTop() {
        return top;
    }

    public HorizontalLayout setTop(int top) {
        this.top = top;
        return this;
    }

    @Override
    public void checkHover(int mouseX, int mouseY) {
        elements.forEach(e -> e.checkHover(mouseX, mouseY));
    }

    @Override
    public void onResize() {
        elements.forEach(ElementBase::onResize);
    }
}
