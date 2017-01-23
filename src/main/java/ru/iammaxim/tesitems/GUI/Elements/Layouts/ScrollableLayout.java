package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.LayoutBase;
import ru.iammaxim.tesitems.GUI.ResManager;

/**
 * Created by maxim on 11/8/16 at 5:18 PM.
 */
public class ScrollableLayout extends FrameLayout {
    protected int height;
    protected int scroll = 0;
    protected Minecraft mc;
    protected ScaledResolution res;

    public ScrollableLayout(ElementBase parent) {
        super(parent);
        mc = Minecraft.getMinecraft();
        res = new ScaledResolution(mc);
    }

    @Override
    public int getHeight() {
        return height;
    }

    public ScrollableLayout setHeight(int height) {
        this.height = height;
        return this;
    }

    @Override
    public void onResize() {
        res = new ScaledResolution(mc);
        element.onResize();
    }

    public void scrollToBottom() {
        scroll = element.getHeight() - height;
        if (scroll < 0) scroll = 0;
        ((VerticalLayout) element).setTop(top + padding - scroll);
        ((LayoutBase) element).doLayout();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        //draw scrollbar
        int elementHeight = element.getHeight();
//        int scrollbarHeight = (int) ((height - 2 * padding) * ((float) (height - 2 * padding) / elementHeight));
//        if (scrollbarHeight > height - 2 * padding)
//            scrollbarHeight = height - 2 * padding;
//        int scrollbarTopOffset = (int) ((float) (height - 2 * padding - scrollbarHeight - 32) * scroll / (element.getHeight() - height + 2 * padding - 32) + 16);
        int scrollbarTopOffset = (int) (((float) (height - 48 - 2 * padding) * scroll) / (elementHeight - height) + 16);
//        drawColoredRect(Tessellator.getInstance(), right, top + scrollbarTopOffset, right+8, top + scrollbarTopOffset + scrollbarHeight, 0xffffffff);
        drawTexturedRect(Tessellator.getInstance(), right, top, right + 8, top + 16, ResManager.inv_scrollbar_bg_top);
        drawTexturedRect(Tessellator.getInstance(), right, top + 16, right + 8, bottom - 16, ResManager.inv_scrollbar_bg_center);
        drawTexturedRect(Tessellator.getInstance(), right, bottom - 16, right + 8, bottom, ResManager.inv_scrollbar_bg_bottom);
        drawTexturedRect(Tessellator.getInstance(), right, top + scrollbarTopOffset, right + 8, top + scrollbarTopOffset + 16, ResManager.inv_scrollbar);

        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom) {
            int scr = -Mouse.getDWheel() / 10;
            if (scr != 0) {
                scroll += scr;

                if (scroll < 0) {
                    scroll = 0;
                }

                int i = element.getHeight() - height;
                if (i < 0) i = 0;
                if (scroll > i) {
                    scroll = i;
                }

                ((VerticalLayout) element).setTop(top + padding - scroll);
                if (element instanceof LayoutBase) {
                    ((LayoutBase) element).doLayout();
                }
            }
        }

        double scaleW = mc.displayWidth / res.getScaledWidth_double();
        double scaleH = mc.displayHeight / res.getScaledHeight_double();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) (left * scaleW), (int) (top * scaleH), (int) (width * scaleW), (int) (height * scaleH));
        element.draw(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
