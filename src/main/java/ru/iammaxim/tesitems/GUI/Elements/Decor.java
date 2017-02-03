package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.renderer.Tessellator;
import ru.iammaxim.tesitems.GUI.ResManager;

/**
 * Created by maxim on 1/2/17 at 5:29 PM.
 */
public class Decor extends ElementBase {
    private Side side;

    public enum Side {
        LEFT, RIGHT
    }

    public Decor(Side side) {
        this.side = side;
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (side == Side.LEFT)
            drawTexturedRect(Tessellator.getInstance(), left, top, right, bottom, ResManager.decor_blue_left);
        else
            drawTexturedRect(Tessellator.getInstance(), left, top, right, bottom, ResManager.decor_blue_right);
    }

    @Override
    public String getName() {
        return "Decor (" + side.toString() + ")";
    }
}
