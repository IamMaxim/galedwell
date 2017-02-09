package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.renderer.Tessellator;
import ru.iammaxim.tesitems.GUI.ResManager;

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

    private int frameColor = ResManager.MAIN_COLOR;

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

        drawColoredRect(tess, left + paddingLeft, top + paddingTop, left + paddingLeft + frameWidth, bottom - paddingBottom, frameColor);
        drawColoredRect(tess, left + paddingLeft, top + paddingTop, right - paddingRight, top + paddingTop + frameWidth, frameColor);
        drawColoredRect(tess, right - paddingRight - frameWidth, top + paddingTop, right - paddingRight, bottom - paddingBottom, frameColor);
        drawColoredRect(tess, left + paddingLeft, bottom - paddingBottom - frameWidth, right - paddingRight, bottom - paddingBottom, frameColor);

        super.draw(mouseX, mouseY);
    }

    @Override
    public void doLayout() {
        element.setBounds(left + paddingLeft + frameWidth + innerPaddingHorizontal,
                top + paddingTop + frameWidth + innerPaddingVertical,
                right - paddingRight - frameWidth - innerPaddingHorizontal,
                bottom - paddingBottom - frameWidth - innerPaddingVertical);
        if (element instanceof LayoutBase)
            ((LayoutBase) element).doLayout();
    }

    @Override
    public String getName() {
        return "WrapFrameLayout";
    }
}
