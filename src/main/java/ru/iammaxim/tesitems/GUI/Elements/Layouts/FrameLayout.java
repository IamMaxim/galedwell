package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.LayoutBase;

/**
 * Created by maxim on 11/7/16 at 4:38 PM.
 */
public class FrameLayout extends LayoutBase {
    protected ElementBase element;

    public FrameLayout(ElementBase parent) {
        super(parent);
    }

    @Override
    public void keyTyped(char c, int keyCode) {
        element.keyTyped(c, keyCode);
    }

    @Override
    public void checkHover(int mouseX, int mouseY) {
        element.checkHover(mouseX, mouseY);
    }

    @Override
    public void checkClick(int mouseX, int mouseY) {
        element.checkClick(mouseX, mouseY);
    }

    public FrameLayout setElement(ElementBase element) {
        this.element = element;
        return this;
    }

    @Override
    public void doLayout() {
        element.setBounds(left + padding, top + padding, right - padding, bottom - padding);
        if (element instanceof LayoutBase)
            ((LayoutBase) element).doLayout();
    }

    @Override
    public int getWidth() {
        return element.getWidth() + 2 * padding;
    }

    @Override
    public int getHeight() {
        return element.getHeight() + 2 * padding;
    }

    @Override
    public void onResize() {
        element.onResize();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (element != null)
            element.draw(mouseX, mouseY);
        else
            System.out.println("no element");
    }
}
