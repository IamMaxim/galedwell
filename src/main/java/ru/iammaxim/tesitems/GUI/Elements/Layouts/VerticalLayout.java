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
public class VerticalLayout extends LayoutBase {
    private int spacing = 4;
    private ArrayList<ElementBase> elements = new ArrayList<>();

    public ArrayList<ElementBase> getElements() {
        return elements;
    }

    public VerticalLayout(ElementBase parent) {
        super(parent);
    }

    public VerticalLayout add(ElementBase element) {
        elements.add(element);
        return this;
    }

    public void remove(ElementBase element) {
        elements.remove(element);
    }

    public void clear() {
        elements.clear();
    }

    @Override
    public void keyTyped(char c, int keyCode) {
        elements.forEach(e -> e.keyTyped(c, keyCode));
    }

    @Override
    public void checkClick(int mouseX, int mouseY) {
        try {
            elements.forEach(e -> e.checkClick(mouseX, mouseY));
        } catch (ConcurrentModificationException e) {}
    }

    @Override
    public void doLayout() {
        int y = top + padding;
        int x = left + padding;
        for (ElementBase element : elements) {
            int w = width - 2 * padding;
            int h = element.getHeight();
            element.setBounds(x, y, x + w, y + h);
            if (element instanceof LayoutBase)
                ((LayoutBase) element).doLayout();
            y += h + spacing;
        }
    }

    @Override
    public int getHeight() {
        int height = 0;
        for (ElementBase e : elements) {
            height += e.getHeight();
        }
        height += (elements.size() - 1) * spacing;
        height += 2 * padding + 2 * marginV;
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
            int w = e.getWidth();
            if (parent != null)
                w = (int) Math.min(w, new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() * 0.8 - 2 * padding);
            if (w > width) width = w;
        }
        width += 2 * padding + 2 * marginH;
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