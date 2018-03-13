package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.GUI.Elements.ElementBase;

import java.util.Arrays;
import java.util.List;

/**
 * Created by maxim on 06.03.2017.
 */
public class BottomPanelLayout extends FrameLayout {
    public ElementBase bottomPanel;

    public BottomPanelLayout setBottomPanel(ElementBase bottomPanel) {
        this.bottomPanel = bottomPanel;
        return this;
    }

    @Override
    public void doLayout() {
        int bottomPanelHeight = bottomPanel.getHeight();
        element.setBounds(left + paddingLeft, top + paddingTop, right - paddingRight, bottom - paddingBottom - bottomPanelHeight);
        if (element instanceof LayoutBase)
            ((LayoutBase) element).doLayout();

        bottomPanel.setBounds(left + paddingLeft, bottom - paddingBottom - bottomPanelHeight, right - paddingRight, bottom - paddingBottom);
        if (bottomPanel instanceof LayoutBase)
            ((LayoutBase) bottomPanel).doLayout();
    }

    @Override
    public int getHeight() {
        return super.getHeight() + bottomPanel.getHeight();
    }

    @Override
    public String getName() {
        return "BottomPanelLayout";
    }

    @Override
    public List<ElementBase> getChildren() {
        return Arrays.asList(element, bottomPanel);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        bottomPanel.draw(mouseX, mouseY);
    }

    @Override
    public void checkClick(int mouseX, int mouseY) {
        super.checkClick(mouseX, mouseY);
        bottomPanel.checkClick(mouseX, mouseY);
    }

    @Override
    public void checkHover(int mouseX, int mouseY) {
        super.checkHover(mouseX, mouseY);
        bottomPanel.checkHover(mouseX, mouseY);
    }

    @Override
    public void checkRightClick(int mouseX, int mouseY) {
        super.checkRightClick(mouseX, mouseY);
        bottomPanel.checkRightClick(mouseX, mouseY);
    }

    @Override
    public void keyTyped(char c, int keyCode) {
        super.keyTyped(c, keyCode);
        bottomPanel.keyTyped(c, keyCode);
    }
}
