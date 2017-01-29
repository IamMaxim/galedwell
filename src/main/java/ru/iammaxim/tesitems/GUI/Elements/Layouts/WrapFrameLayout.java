package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.renderer.Tessellator;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.LayoutBase;

/**
 * Created by maxim on 29.01.17.
 */
public class WrapFrameLayout extends FrameLayout {
    private int innerPaddingHorizontal = 4;
    private int innerPaddingVertical = 4;
    private int frameWidth = 1;

    public WrapFrameLayout() {}

    public int getFrameColor() {
        return frameColor;
    }

    public void setFrameColor(int frameColor) {
        this.frameColor = frameColor;
    }

    private int frameColor = 0xff481f09;

    public int getInnerPaddingVertical() {
        return innerPaddingVertical;
    }

    public void setInnerPaddingVertical(int innerPaddingVertical) {
        this.innerPaddingVertical = innerPaddingVertical;
    }

    public int getInnerPaddingHorizontal() {
        return innerPaddingHorizontal;
    }

    public void setInnerPaddingHorizontal(int innerPaddingHorizontal) {
        this.innerPaddingHorizontal = innerPaddingHorizontal;
    }

    @Override
    public int getWidth() {
        return super.getWidth() + 2 * (innerPaddingHorizontal + frameWidth);
    }

    @Override
    public int getHeight() {
        return super.getHeight() + 2 * (innerPaddingVertical + frameWidth);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Tessellator tess = Tessellator.getInstance();

        drawColoredRect(tess, left + leftPadding, top + topPadding, left + leftPadding + frameWidth, bottom - bottomPadding, frameColor);
        drawColoredRect(tess, left + leftPadding, top + topPadding, right - rightPadding, top + topPadding + frameWidth, frameColor);
        drawColoredRect(tess, right - rightPadding - frameWidth, top + topPadding, right - rightPadding, bottom - bottomPadding, frameColor);
        drawColoredRect(tess, left + leftPadding, bottom - bottomPadding - frameWidth, right - rightPadding, bottom - bottomPadding, frameColor);

        super.draw(mouseX, mouseY);
    }

    @Override
    public void doLayout() {
        element.setBounds(left + leftPadding + frameWidth + innerPaddingHorizontal,
                top + topPadding + frameWidth + innerPaddingVertical,
                right - rightPadding - frameWidth - innerPaddingHorizontal,
                bottom - bottomPadding - frameWidth - innerPaddingVertical);
        if (element instanceof LayoutBase)
            ((LayoutBase) element).doLayout();
    }
}
