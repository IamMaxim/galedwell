package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import ru.iammaxim.tesitems.GUI.ResManager;

/**
 * Created by maxim on 30.01.17.
 */
public class Arrow extends ElementBase {
    private ResourceLocation texture;
    private Direction direction;

    public enum Direction {
        RIGHT,
        DOWN
    }

    public Arrow(Direction direction) {
        this.direction = direction;
        if (direction == Direction.RIGHT)
            texture = ResManager.arrow_right;
        else if (direction == Direction.DOWN)
            texture = ResManager.arrow_down;
    }

    @Override
    public int getWidth() {
        return 8;
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        drawTexturedRect(Tessellator.getInstance(), left, top, right, bottom, texture);
    }

    @Override
    public String getName() {
        return "Arrow (" + direction.toString() + ")";
    }
}
