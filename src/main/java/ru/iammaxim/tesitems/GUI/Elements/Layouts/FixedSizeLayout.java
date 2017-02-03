package ru.iammaxim.tesitems.GUI.Elements.Layouts;

/**
 * Created by maxim on 2/3/17 at 9:26 AM.
 */
public class FixedSizeLayout extends FrameLayout {
    private int width = 0, height = 0;

    public FixedSizeLayout setFixedWidth(int width) {
        this.width = width;
        return this;
    }

    public FixedSizeLayout setFixedHeight(int height) {
        this.height = height;
        return this;
    }

    @Override
    public int getWidth() {
        return width + marginLeft + marginRight;
    }

    @Override
    public int getHeight() {
        return height + marginTop + marginBottom;
    }
}
