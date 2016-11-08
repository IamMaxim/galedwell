package ru.iammaxim.tesitems.GUI.elements;

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
    public void onRescale() {
        element.onRescale();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        element.draw(mouseX, mouseY);
    }
}
