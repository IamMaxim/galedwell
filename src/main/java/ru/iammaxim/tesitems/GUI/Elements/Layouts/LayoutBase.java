package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.GUI.Elements.ElementBase;

import java.util.List;

/**
 * Created by maxim on 11/7/16 at 4:17 PM.
 */
public abstract class LayoutBase extends ElementBase {
    protected int paddingLeft = 0, paddingRight = 0, paddingTop = 0, paddingBottom = 0;

    public LayoutBase() {}

    public abstract void doLayout();

    public LayoutBase setPadding(int padding) {
        this.paddingLeft = this.paddingRight = this.paddingTop = this.paddingBottom = padding;
        return this;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public LayoutBase setPaddingLeft(int padding) {
        this.paddingLeft = padding;
        return this;
    }

    public LayoutBase setPaddingRight(int padding) {
        this.paddingRight = padding;
        return this;
    }

    public LayoutBase setPaddingTop(int padding) {
        this.paddingTop = padding;
        return this;
    }

    public LayoutBase setPaddingBottom(int padding) {
        this.paddingBottom = padding;
        return this;
    }

    public abstract List<ElementBase> getChildren();
}
