package ru.iammaxim.tesitems.Utils;

import net.minecraft.client.gui.FontRenderer;
import ru.iammaxim.tesitems.GUI.Fonts.UnicodeFontRenderer;
import ru.iammaxim.tesitems.TESItems;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by maxim on 3/3/17 at 7:53 PM.
 */
public class ClientThings {
    public static FontRenderer fontRenderer; //used in all mod UI
    public static FontRenderer monospaceFontRenderer; //used in script editor

    public static void loadFonts() {
        //load main font
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("main.ttf")));
            font = font.deriveFont(Font.PLAIN, 24);
            fontRenderer = new UnicodeFontRenderer(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            fontRenderer = TESItems.getMinecraft().fontRendererObj;
        }

        //load monospace font
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("monospace.ttf")));
            font = font.deriveFont(Font.PLAIN, 16);
            monospaceFontRenderer = new UnicodeFontRenderer(font);
            ((UnicodeFontRenderer) monospaceFontRenderer).setTopOffset(3);
//            ((UnicodeFontRenderer)monospaceFontRenderer).font.setPaddingTop(4);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            monospaceFontRenderer = TESItems.getMinecraft().fontRendererObj;
        }
    }
}
