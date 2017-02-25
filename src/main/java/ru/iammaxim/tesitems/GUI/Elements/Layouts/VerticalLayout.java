package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.GUI.Elements.ElementBase;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by maxim on 11/7/16 at 4:19 PM.
 */
public class VerticalLayout extends LayoutBase implements LayoutWithList {
    protected int spacing = 4;
    protected ArrayList<ElementBase> elements = new ArrayList<>();
    private boolean limitHeight = true;
    private boolean center = false;

    public ArrayList<ElementBase> getElements() {
        return elements;
    }

    public VerticalLayout() {
    }

    public VerticalLayout setSpacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    @Override
    public VerticalLayout add(ElementBase element) {
        elements.add(element);
        element.setParent(this);
        return this;
    }

    @Override
    public void remove(ElementBase element) {
        elements.remove(element);
    }

    @Override
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
            super.checkClick(mouseX, mouseY);
            elements.forEach(e -> e.checkClick(mouseX, mouseY));
        } catch (ConcurrentModificationException e) {
        }
    }

    @Override
    public void doLayout() {
        int y;
        if (center)
            y = top + (height - getHeight()) / 2;
        else
            y = top + paddingTop;
        int y_max = bottom - paddingBottom;
        int x = left + paddingLeft;
        for (ElementBase element : elements) {
            int w;
            int w_override = element.getWidthOverride();
            if (w_override == FILL)
                w = width - paddingLeft - paddingRight;
            else
                w = Math.min(width - paddingLeft - paddingRight, element.getWidth());

            int h;
            if (limitHeight) {
                h = Math.min(element.getHeight(), y_max - y);
            } else {
                h = element.getHeight();
            }
            element.setBounds(x, y, x + w, y + h);
            if (element instanceof LayoutBase)
                ((LayoutBase) element).doLayout();
            y += h + spacing;
        }
    }

    @Override
    public List<ElementBase> getChildren() {
        return elements;
    }

    @Override
    public int getHeight() {
        int height = 0;
        for (ElementBase e : elements) {
            height += e.getHeight();
        }
        height += (elements.size() - 1) * spacing;
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
            int w = e.getWidth();
            if (w > width) width = w;
        }
        width += paddingLeft + paddingRight + marginLeft + marginRight;
        return width;
    }

    public int getTop() {
        return top;
    }

    @Override
    public void checkHover(int mouseX, int mouseY) {
        elements.forEach(e -> e.checkHover(mouseX, mouseY));
    }

    @Override
    public void onResize() {
        elements.forEach(ElementBase::onResize);
    }

    public boolean isLimitHeight() {
        return limitHeight;
    }

    public VerticalLayout setLimitHeight(boolean limitHeight) {
        this.limitHeight = limitHeight;
        return this;
    }

    @Override
    public String getName() {
        return "VerticalLayout";
    }

    public VerticalLayout center(boolean center) {
        this.center = center;
        return this;
    }
}
