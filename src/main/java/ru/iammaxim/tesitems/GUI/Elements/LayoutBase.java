package ru.iammaxim.tesitems.GUI.Elements;

/**
 * Created by maxim on 11/7/16 at 4:17 PM.
 */
public abstract class LayoutBase extends ElementBase {
    protected int leftPadding = 0, rightPadding = 0, topPadding = 0, bottomPadding = 0;

    public LayoutBase() {}

    public abstract void doLayout();

    public LayoutBase setPadding(int padding) {
        this.leftPadding = this.rightPadding = this.topPadding = this.bottomPadding = padding;
        return this;
    }

    public int getLeftPadding() {
        return leftPadding;
    }

    public int getRightPadding() {
        return rightPadding;
    }

    public int getTopPadding() {
        return topPadding;
    }

    public int getBottomPadding() {
        return bottomPadding;
    }

    public void setLeftPadding(int padding) {
        this.leftPadding = padding;
    }
    public void setRightPadding(int padding) {
        this.rightPadding = padding;
    }

    public void setTopPadding(int padding) {
        this.topPadding = padding;
    }

    public void setBottomPadding(int padding) {
        this.bottomPadding = padding;
    }
}
