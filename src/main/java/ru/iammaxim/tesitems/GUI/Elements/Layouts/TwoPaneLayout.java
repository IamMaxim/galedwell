package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.LayoutBase;

/**
 * Created by maxim on 29.01.17.
 */
public class TwoPaneLayout extends LayoutBase {
    private ElementBase leftElement, rightElement;
    private float leftWeight = 1, rightWeight = 1;
    private int leftWidth = -1, rightWidth = -1;
    private int minHeight = 0;

    public int getLeftWidth() {
        return leftWidth;
    }

    public TwoPaneLayout setLeftWidth(int leftWidth) {
        this.leftWidth = leftWidth;
        return this;
    }

    public int getRightWidth() {
        return rightWidth;
    }

    public TwoPaneLayout setRightWidth(int rightWidth) {
        this.rightWidth = rightWidth;
        return this;
    }

    public float getLeftWeight() {
        return leftWeight;
    }

    public TwoPaneLayout setLeftWeight(float weight) {
        this.leftWeight = weight;
        return this;
    }

    public float getRightWeight() {
        return rightWeight;
    }

    public TwoPaneLayout setRightWeight(float weight) {
        this.rightWeight = weight;
        return this;
    }

    @Override
    public int getWidth() {
        return leftElement.getWidth() + rightElement.getWidth() + paddingLeft + paddingRight;
    }

    @Override
    public int getHeight() {
        return Math.max(minHeight, Math.max(leftElement.getHeight(), rightElement.getHeight()) + paddingTop + paddingBottom);
    }

    public ElementBase getLeftElement() {
        return leftElement;
    }

    public TwoPaneLayout setLeftElement(ElementBase leftElement) {
        this.leftElement = leftElement;
        leftElement.setParent(this);
        return this;
    }

    public ElementBase getRightElement() {
        return rightElement;
    }

    public TwoPaneLayout setRightElement(ElementBase rightElement) {
        this.rightElement = rightElement;
        rightElement.setParent(this);
        return this;
    }

    @Override
    public void doLayout() {
        int borderX;

        if (leftWidth != -1) {
            borderX = left + paddingLeft + leftWidth;
        } else if (rightWidth != -1) {
            borderX = right - paddingRight - rightWidth;
        } else {
            borderX = (int) ((float) width * leftWeight / (leftWeight + rightWeight));
        }

        leftElement.setBounds(left + paddingLeft, top + paddingTop, borderX, bottom - paddingBottom);
        if (leftElement instanceof LayoutBase)
            ((LayoutBase) leftElement).doLayout();

        rightElement.setBounds(borderX, top + paddingTop, right - paddingRight, bottom - paddingBottom);
        if (rightElement instanceof LayoutBase)
            ((LayoutBase) rightElement).doLayout();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        leftElement.draw(mouseX, mouseY);
        rightElement.draw(mouseX, mouseY);
    }

    public int getMinHeight() {
        return minHeight;
    }

    public TwoPaneLayout setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }
}
