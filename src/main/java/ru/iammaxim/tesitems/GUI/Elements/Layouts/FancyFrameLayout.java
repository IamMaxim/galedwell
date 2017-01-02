package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.renderer.Tessellator;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;

import static ru.iammaxim.tesitems.GUI.ResManager.*;

/**
 * Created by maxim on 11/7/16 at 4:38 PM.
 */
public class FancyFrameLayout extends FrameLayout {
    private int frameSize = 18;

    public FancyFrameLayout(ElementBase parent) {
        super(parent);
        setPadding(8);
    }

    @Override
    public void setPadding(int padding) {
        super.setPadding(padding + frameSize);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Tessellator tess = Tessellator.getInstance();
        float tmp;

        //draw a frame
        drawTexturedRect(tess, left, top, right, bottom, generic_bg);
        drawTexturedRect(tess, left, top, left + frameSize, top + frameSize, frameBorder_LT);
        tmp = (right - left - 2 * frameSize) / 22.75f / frameSize;
        drawTexturedRect(tess, left + frameSize, top, right - frameSize, top + frameSize, tmp, 1, frameBorder_CT);
        drawTexturedRect(tess, left + frameSize, bottom - frameSize, right - frameSize, bottom, tmp, 1, frameBorder_CB);
        drawTexturedRect(tess, right - frameSize, top, right, top + frameSize, frameBorder_RT);
        tmp = (bottom - top - 2 * frameSize) / 22.75f / frameSize;
        drawTexturedRect(tess, left, top + frameSize, left + frameSize, bottom - frameSize, 1, tmp, frameBorder_LC);
        drawTexturedRect(tess, right - frameSize, top + frameSize, right, bottom - frameSize, 1, tmp, frameBorder_RC);
        drawTexturedRect(tess, left, bottom - frameSize, left + frameSize, bottom, frameBorder_LB);
        drawTexturedRect(tess, right - frameSize, bottom - frameSize, right, bottom, frameBorder_RB);

        super.draw(mouseX, mouseY);
    }
}
