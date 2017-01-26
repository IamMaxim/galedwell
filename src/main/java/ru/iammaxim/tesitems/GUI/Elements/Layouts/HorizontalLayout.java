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
        int _w = getWidth();
        int y = top + topPadding;
        int x;
        if (center) x = left + (width - _w) / 2;
        else x = left + leftPadding;
        for (ElementBase element : elements) {
            int w = element.getWidth();
            int h = height - topPadding - bottomPadding;
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
                h = (int) Math.min(h, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() * 0.8 - topPadding - bottomPadding);
            if (h > height) height = h;
        }
        height += topPadding + bottomPadding;
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
        width += leftPadding + rightPadding;
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
    public void onResize() {
        elements.forEach(ElementBase::onResize);
    }
}
