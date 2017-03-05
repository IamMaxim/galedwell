package ru.iammaxim.tesitems.GUI.Elements.Layouts;

/**
 * Created by maxim on 3/5/17 at 8:29 AM.
 */
public class FixedHeightLayout extends FrameLayout {
    private int height = 1;

    public FixedHeightLayout setFixedHeight(int height) {
        this.height = height;
        return this;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
