package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.GUI.ResManager;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 2/25/17 at 1:43 AM.
 */
public class DraggableWindow extends FrameLayout {
    public String name;
    public FrameLayout topPanel;
    public int lastX = -1, lastY = -1;
    public int posX = 0, posY = 0;
    public ScaledResolution res;
    private boolean needToReact = false;

    //sets position of left top corner
    public void setPos(int x, int y) {
        this.posX = x;
        this.posY = y;
        setBounds(posX, posY, posX + getWidth(), posY + getHeight());
        checkBounds();
        doLayout();
    }

    @Override
    public int getWidth() {
        return Math.max(super.getWidth(), topPanel.getWidth());
    }

    @Override
    public int getHeight() {
        return super.getHeight() + topPanel.getHeight();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        topPanel.draw(mouseX, mouseY);
        super.draw(mouseX, mouseY);
    }

    @Override
    public void checkClick(int mouseX, int mouseY) {
        super.checkClick(mouseX, mouseY);
        topPanel.checkClick(mouseX, mouseY);
    }

    @Override
    public void checkRightClick(int mouseX, int mouseY) {
        super.checkRightClick(mouseX, mouseY);
        topPanel.checkRightClick(mouseX, mouseY);
    }

    @Override
    public ElementBase setBounds(int left, int top, int right, int bottom) {
        int topPanelHeight = topPanel.getHeight();
        super.setBounds(left, top, right, bottom);
        topPanel.setBounds(left, top, right, top + topPanelHeight);
        return this;
    }

    @Override
    public void onResize() {
        res = new ScaledResolution(Minecraft.getMinecraft());
        super.onResize();
        topPanel.onResize();
    }

    public boolean checkBounds() {
        boolean changed = false;
        int dx = posX + width - res.getScaledWidth();
        if (dx > 0) {
            posX -= dx;
            changed = true;
        }
        if (posX < 0) {
            posX = 0;
            changed = true;
        }

        int dy = posY + height - res.getScaledHeight();
        if (dy > 0) {
            posY -= dy;
            changed = true;
        }
        if (posY < 0) {
            posY = 0;
            changed = true;
        }
        return changed;
    }

    public DraggableWindow(String name) {
        this.name = name;
        res = new ScaledResolution(TESItems.getMinecraft());

        topPanel = new FrameLayout() {
            @Override
            public void draw(int mouseX, int mouseY) {
                if (needToReact && Mouse.isButtonDown(0)) {
                    if (lastX == -1) lastX = mouseX;
                    if (lastY == -1) lastY = mouseY;

                    int deltaX = mouseX - lastX;
                    int deltaY = mouseY - lastY;

                    posX += deltaX;
                    posY += deltaY;

                    checkBounds();

                    lastX = mouseX;
                    lastY = mouseY;

                    DraggableWindow.this.setBounds(posX, posY, posX + DraggableWindow.this.getWidth(), posY + DraggableWindow.this.getHeight());
                    DraggableWindow.this.doLayout();
                } else {
                    needToReact = false;
                    lastX = -1;
                    lastY = -1;
                }

                super.draw(mouseX, mouseY);
            }
        };
        topPanel.setOnClick(e -> {
            needToReact = true;
        });
        topPanel.setElement(new DarkBackgroundFrameLayout().setElement(new HorizontalLayout().add(new Text(name).setColor(ResManager.BRIGHT_TEXT_COLOR)).center(true)).setWidthOverride(FILL));
    }

    @Override
    public void doLayout() {
        topPanel.doLayout();
        element.setBounds(left + paddingLeft, top + paddingTop + topPanel.height(), right - paddingRight, bottom - paddingBottom);
        if (element instanceof LayoutBase)
            ((LayoutBase) element).doLayout();



        if (checkBounds()) {
            topPanel.doLayout();
            element.setBounds(left + paddingLeft, top + paddingTop + topPanel.height(), right - paddingRight, bottom - paddingBottom);
            if (element instanceof LayoutBase)
                ((LayoutBase) element).doLayout();
        }
    }
}
