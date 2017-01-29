package ru.iammaxim.tesitems.GUI.Fonts;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;

public class UnicodeFontRenderer extends FontRenderer {
    private final UnicodeFont font;
    private static final ResourceLocation fakeTexture = new ResourceLocation("tesitems:textures/fakeTexture.png");
    public static String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ'\".,-_!?@#$%*/\\|;:";
    private Minecraft mc = Minecraft.getMinecraft();

    @SuppressWarnings("unchecked")
    public UnicodeFontRenderer(Font awtFont) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);

        font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();

        //load cyrillic
        font.addGlyphs("абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ");

        font.getEffects().add(new ColorEffect(Color.WHITE));
        try {
            font.loadGlyphs();
        } catch (SlickException exception) {
            throw new RuntimeException(exception);
        }

        FONT_HEIGHT = font.getHeight(alphabet) / 2;
    }

    @Override
    public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
        return Arrays.asList(this.wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
    }

    String wrapFormattedStringToWidth(String str, int wrapWidth) {
        int len = sizeStringToWidth(str, wrapWidth);

        if (str.length() <= len) {
            return str;
        } else {
            String s = str.substring(0, len);
            char c0 = str.charAt(len);
            boolean flag = c0 == 32 || c0 == 10;
            String s1 = getFormatFromString(s) + str.substring(len + (flag ? 1 : 0));
            return s + "\n" + this.wrapFormattedStringToWidth(s1, wrapWidth);
        }
    }

    private int sizeStringToWidth(String str, int wrapWidth) {
        wrapWidth *= 2; //because Minecraft scales GUI

        int len = str.length();
        int k = 0;
        int l = -1;

        for (; k < len; ++k) {
            char c0 = str.charAt(k);

            switch (c0) {
                case '\n':
                    --k;
                    break;
                case ' ':
                    l = k;
                default:
                    break;
            }

            int width = font.getWidth(str.substring(0, k));

            if (c0 == '\n') {
                ++k;
                l = k;
                break;
            }

            if (width > wrapWidth) {
                k--;
                break;
            }
        }

        return k != len && l != -1 && l < k ? l : k;
    }

    @Override
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        mc.getTextureManager().bindTexture(fakeTexture);

        glPushMatrix();
        glScaled(0.5, 0.5, 0.5);

        boolean blend = glIsEnabled(GL_BLEND);
        boolean lighting = glIsEnabled(GL_LIGHTING);
        boolean texture = glIsEnabled(GL_TEXTURE_2D);
        if (!blend)
            glEnable(GL_BLEND);
        if (lighting)
            glDisable(GL_LIGHTING);
        if (texture)
            glDisable(GL_TEXTURE_2D);
        x *= 2;
        y *= 2;
        y -= 8;

        font.drawString(x, y, text, new org.newdawn.slick.Color(color));
        GlStateManager.color(0, 0, 0, 1);

        if (texture)
            glEnable(GL_TEXTURE_2D);
        if (lighting)
            glEnable(GL_LIGHTING);
        if (!blend)
            glDisable(GL_BLEND);
        glPopMatrix();
        return (int) x;
    }

    @Override
    public int drawString(String string, int x, int y, int color) {
        return drawString(string, x, y, color, false);
    }

    @Override
    public int getCharWidth(char c) {
        return getStringWidth(Character.toString(c));
    }

    @Override
    public int getStringWidth(String string) {
        return font.getWidth(string) / 2;
    }

    public int getStringHeight(String string) {
        return font.getHeight(string) / 2;
    }
}
