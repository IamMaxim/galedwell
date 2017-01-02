package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.LayoutBase;

/**
 * Created by maxim on 11/8/16 at 5:18 PM.
 */
public class ScrollableLayout extends FrameLayout {
    private int height;
    private int scroll = 0;
    private Minecraft mc;
    private ScaledResolution res;

    public ScrollableLayout setHeight(int height) {
        this.height = height;
        return this;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public ScrollableLayout(ElementBase parent) {
        super(parent);
        mc = Minecraft.getMinecraft();
        res = new ScaledResolution(mc);
    }

    @Override
    public void onResize() {
        res = new ScaledResolution(mc);
        element.onResize();
    }

    public void scrollToBottom() {
        scroll = element.getHeight() - height;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        //draw scrollbar
        int scrollbarHeight = (int) (height * ((float)height / element.getHeight()));
        if (scrollbarHeight > height)
            scrollbarHeight = height;
        int scrollbarTopOffset = (int) ((float) (height - scrollbarHeight) * scroll / (element.getHeight() - height));
        drawColoredRect(Tessellator.getInstance(), right-2, top + scrollbarTopOffset, right, top + scrollbarTopOffset + scrollbarHeight, 0xffffffff);

        int scr = - Mouse.getDWheel() / 10;
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

        double scaleW = mc.displayWidth / res.getScaledWidth_double();
        double scaleH = mc.displayHeight / res.getScaledHeight_double();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) (left * scaleW), (int) (top * scaleH), (int) (width * scaleW), (int) (height * scaleH));
        element.draw(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
