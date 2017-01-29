package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import ru.iammaxim.tesitems.GUI.Fonts.UnicodeFontRenderer;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by maxim on 11/7/16 at 7:11 PM.
 */
public class TextField extends ElementBase {
    private String text = "", hint = "";
    private List<String> strs = new ArrayList<>();
    private int hintColor = 0xff999999, color = 0xffffffff;
    private int lineSpacing = 4;
    private Consumer<TextField> onType;
    private int padding = 4;
    private boolean dirty = false;
    private boolean active = false;
    private long startTime = 0;
    private char lastChar = 0;
    private int lastKey = 0;
    private FontRenderer fontRenderer = TESItems.fontRenderer;

    public TextField(ElementBase parent) {
        super(parent);
    }

    @Override
    public void checkClick(int mouseX, int mouseY) {
        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom) {
            click(mouseX - left, mouseY - top);
            active = true;
        } else {
            active = false;
        }
    }

    public TextField setFontRenderer(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
        return this;
    }

    public String getText() {
        return text;
    }

    public TextField setText(String s) {
        text = s;
        dirty = true;
//        strs = Minecraft.getMinecraft().fontRendererObj.listFormattedStringToWidth(text, width);
        return this;
    }

    private void update() {
        if (width == 0) {
            return;
        }
        strs = fontRenderer.listFormattedStringToWidth(text, width - 2 * padding);
        dirty = false;
    }

    public String getHint() {
        return hint;
    }

    public TextField setHint(String hint) {
        this.hint = hint;
        return this;
    }

    @Override
    public int getWidth() {
        if (!text.isEmpty()) {
            return fontRenderer.getStringWidth(text) + 2 * padding;
        }
        else
            return fontRenderer.getStringWidth(hint) + 2 * padding;
    }

    public TextField setOnType(Consumer<TextField> onType) {
        this.onType = onType;
        return this;
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (active) {
            //Left or right control + V
            if (key == 47 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157))) {
                String toPaste = Sys.getClipboard();
                if (toPaste != null) {
                    text += toPaste;
                }
                return;
            }

            if (key == Keyboard.KEY_BACK) { //backspace
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                    lastKey = key;
                    lastChar = typedChar;
                }
            } else if (key == Keyboard.KEY_RETURN) {
                text = text + '\n';
                lastKey = key;
                lastChar = typedChar;
            } else if (typedChar == ' ' || UnicodeFontRenderer.alphabet.contains(typedChar + "")) {
                text = text + typedChar;
                lastKey = key;
                lastChar = typedChar;
            }
            update();
            ((LayoutBase) getRoot()).doLayout();
            if (onType != null)
                onType.accept(this);
        }
    }

    @Override
    public int getHeight() {
        if (!text.isEmpty()) {
            if (dirty)
                return 8 + 2 * padding;
            return strs.size() * 8 + (strs.size() - 1) * lineSpacing + 2 * padding;
        } else
            return 8 + 2 * padding;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (dirty) {
            update();
            ((LayoutBase) getRoot()).doLayout();
        }

        //check if key is still down
        if (Keyboard.isKeyDown(lastKey)) {
            if (System.currentTimeMillis() - startTime > 400)
                keyTyped(lastChar, lastKey);
        } else {
            startTime = System.currentTimeMillis();
        }

        Tessellator tess = Tessellator.getInstance();

        if (!active)
            drawColoredRect(tess, left, top, right, bottom, 0xFF333333);
        else
            drawColoredRect(tess, left, top, right, bottom, 0xFF7F7F7F);
        if (text.isEmpty()) //draw hint
            fontRenderer.drawString(hint, left + padding, top + padding, hintColor);
        else //draw text
            for (int i = 0; i < strs.size(); i++) {
                fontRenderer.drawString(strs.get(i), left + padding, top + (lineSpacing + 8) * i + padding, color);
            }
    }
}
