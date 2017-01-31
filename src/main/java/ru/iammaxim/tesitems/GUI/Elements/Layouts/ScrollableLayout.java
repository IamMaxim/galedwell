package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.ResManager;

/**
 * Created by maxim on 11/8/16 at 5:18 PM.
 */
public class ScrollableLayout extends FrameLayout {
    protected int minHeight = 0;
    protected int scroll = 0;
    protected Minecraft mc;
    protected int elementHeight;
    private int scrollbarWidth = 8, scrollbarEndHeight = 16, scrollbarHandleHeight = 16;

    @Override
    public FrameLayout setElement(ElementBase element) {
        if (element instanceof VerticalLayout)
            ((VerticalLayout) element).setLimitHeight(false);
        return super.setElement(element);
    }

    protected ScaledResolution res;

    @Override
    public void doLayout() {
        //exclude scrollbar width
        element.setBounds(left + paddingLeft, top + paddingTop, right - scrollbarWidth - paddingRight, bottom - paddingBottom);
        if (element instanceof LayoutBase)
            ((LayoutBase) element).doLayout();

        elementHeight = element.getHeight();
    }

    @Override
    public void click(int relativeX, int relativeY) {
        if (relativeX > width - scrollbarWidth) {

        }
    }

    @Override
    public int getWidth() {
        //add scrollbar width
        return super.getWidth() + scrollbarWidth;
    }

    public ScrollableLayout() {
        mc = Minecraft.getMinecraft();
        res = new ScaledResolution(mc);
        setPadding(4);
    }

    public int getMinHeight() {
        return minHeight;
    }

    public ScrollableLayout setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    @Override
    public int getHeight() {
        return Math.max(super.getHeight(), minHeight);
    }

    @Override
    public void onResize() {
        res = new ScaledResolution(mc);
        element.onResize();
    }

    public void scrollToBottom() {
        scroll = element.getHeight() - (height - paddingTop - paddingBottom);
        if (scroll < 0) scroll = 0;
        ((VerticalLayout) element).setTop(top + paddingTop - scroll);
        ((LayoutBase) element).doLayout();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        //draw scrollbar
        int scrollbarTopOffset = (int) (
                ((float) ((height) - 2 * scrollbarEndHeight - scrollbarHandleHeight) * scroll)
                        / (elementHeight - (height - paddingTop - paddingBottom))
                        + scrollbarEndHeight);
        drawTexturedRect(Tessellator.getInstance(), right - scrollbarWidth, top, right, top + scrollbarEndHeight, ResManager.inv_scrollbar_bg_top);
        drawTexturedRect(Tessellator.getInstance(), right - scrollbarWidth, top + scrollbarEndHeight, right, bottom - scrollbarEndHeight, ResManager.inv_scrollbar_bg_center);
        drawTexturedRect(Tessellator.getInstance(), right - scrollbarWidth, bottom - scrollbarEndHeight, right, bottom, ResManager.inv_scrollbar_bg_bottom);
        //draw handle
        drawTexturedRect(Tessellator.getInstance(), right - scrollbarWidth, top + scrollbarTopOffset, right, top + scrollbarTopOffset + scrollbarHandleHeight, ResManager.inv_scrollbar);

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

                ((VerticalLayout) element).setTop(top + paddingTop - scroll);
                if (element instanceof LayoutBase) {
                    ((LayoutBase) element).doLayout();
                }
            }
        }

        double scaleW = mc.displayWidth / res.getScaledWidth_double();
        double scaleH = mc.displayHeight / res.getScaledHeight_double();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) ((left) * scaleW), (int) ((top) * scaleH), (int) ((width) * scaleW), (int) ((height) * scaleH));
        element.draw(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
