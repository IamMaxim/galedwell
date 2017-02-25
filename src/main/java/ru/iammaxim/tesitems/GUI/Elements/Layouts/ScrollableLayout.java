package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.VerticalScrollbar;

import java.util.Arrays;
import java.util.List;

/**
 * Created by maxim on 11/8/16 at 5:18 PM.
 */
public class ScrollableLayout extends FrameLayout {
    protected int minHeight = 0;
    protected int scroll = 0;
    protected Minecraft mc;
    protected ScaledResolution res;
    private VerticalScrollbar scrollbar;
    private int elementHeight;

    @Override
    public void checkClick(int mouseX, int mouseY) {
        super.checkClick(mouseX, mouseY);
        scrollbar.checkClick(mouseX, mouseY);
    }

    public ScrollableLayout() {
        mc = Minecraft.getMinecraft();
        res = new ScaledResolution(mc);
        setPadding(4);
        scrollbar = new VerticalScrollbar().setOnScroll(e -> {
            scroll = e.getScroll();
            ((LayoutBase) element).setTop(top + paddingTop - scroll);
            if (element instanceof LayoutBase) {
                ((LayoutBase) element).doLayout();
            }
        });
        scrollbar.setParent(this);
    }

    @Override
    public FrameLayout setElement(ElementBase element) {
        if (element instanceof VerticalLayout)
            ((VerticalLayout) element).setLimitHeight(false);
        return super.setElement(element);
    }

    @Override
    public void doLayout() {
        //exclude scrollbar width
        element.setBounds(left + paddingLeft, top + paddingTop, right - scrollbar.getWidth() - paddingRight, bottom - paddingBottom);
        if (element instanceof LayoutBase)
            ((LayoutBase) element).doLayout();

        scrollbar.setElementHeight(elementHeight = element.getHeight());
        scrollbar.setBounds(right - scrollbar.getWidth(), top, right, bottom);
    }

    @Override
    public int getWidth() {
        //add scrollbar width
        return super.getWidth() + scrollbar.getWidth();
    }

    public int getMinHeight() {
        return Math.max(minHeight, scrollbar.getMinHeight());
    }

    public ScrollableLayout setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    @Override
    public int getHeight() {
        return Math.max(super.getHeight(), getMinHeight());
    }

    @Override
    public void onResize() {
        res = new ScaledResolution(mc);
        element.onResize();
    }

    public void scrollToBottom() {
        scroll = element.getHeight() - (height - paddingTop - paddingBottom);
        if (scroll < 0) scroll = 0;
        scrollbar.setScroll(scroll);
        ((LayoutBase) element).setTop(top + paddingTop - scroll);
        ((LayoutBase) element).doLayout();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        scrollbar.draw(mouseX, mouseY);

        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom) {
            int scr = -Mouse.getDWheel() / 10;
            if (scr != 0) {
                scroll += scr;

                if (scroll < 0) {
                    scroll = 0;
                }

                int i = elementHeight - height + paddingTop + paddingBottom;
                if (i < 0) i = 0;
                if (scroll > i) {
                    scroll = i;
                }

                scrollbar.setScroll(scroll);
                ((VerticalLayout) element).setTop(top + paddingTop - scroll);
                if (element instanceof LayoutBase) {
                    ((LayoutBase) element).doLayout();
                }
            }
        }

        double scaleW = mc.displayWidth / res.getScaledWidth_double();
        double scaleH = mc.displayHeight / res.getScaledHeight_double();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) ((left) * scaleW), (int) ((res.getScaledHeight() - bottom) * scaleH), (int) ((width) * scaleW), (int) ((height) * scaleH));
        element.draw(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public List<ElementBase> getChildren() {
        return Arrays.asList(element, scrollbar);
    }

    @Override
    public String getName() {
        return "ScrollableLayout";
    }
}
