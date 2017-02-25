package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

/**
 * Created by maxim on 2/24/17 at 10:15 PM.
 */
public class Image extends ElementBase {
    public ResourceLocation texture;
    private int w = 16, h = 16;

    public Image(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        drawTexturedRect(Tessellator.getInstance(), left, top, right, bottom, texture);
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    public Image setWidth(int width) {
        this.w = width;
        return this;
    }

    public Image setHeight(int height) {
        this.h = height;
        return this;
    }

    @Override
    public String getName() {
        return "Image";
    }
}
