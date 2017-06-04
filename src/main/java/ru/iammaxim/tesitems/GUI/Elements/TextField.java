package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutBase;
import ru.iammaxim.tesitems.GUI.Fonts.UnicodeFontRenderer;
import ru.iammaxim.tesitems.Utils.ClientThings;

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
    private int lineHeight = 8;
    private Consumer<TextField> onType;
    private int padding = 4;
    private boolean dirty = false;
    private boolean active = false;
    private long startTime = 0;
    private char lastChar = 0;
    private int lastKey = 0;
    private FontRenderer fontRenderer = ClientThings.fontRenderer;
    private int cursorPos = 0;

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
        if (fontRenderer instanceof UnicodeFontRenderer) {
            int h = ((UnicodeFontRenderer) fontRenderer).font.getLineHeight();
            lineHeight = h / 3;
        }
        return this;
    }

    public String getText() {
        return text;
    }

    public TextField setText(String s) {
        text = s;
        dirty = true;
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
        String s = text.isEmpty() ? hint : text;
        String[] strs = s.split("\n");
        int max = 0;
        for (String str : strs) {
            int w = fontRenderer.getStringWidth(str + " ");
            if (w > max)
                max = w;
        }
        return max + 2 * padding;
    }

    public TextField setOnType(Consumer<TextField> onType) {
        this.onType = onType;
        return this;
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (cursorPos < 0) cursorPos = 0;
        else if (cursorPos > text.length()) cursorPos = text.length();

        if (active) {
//            System.out.println("typed key: " + typedChar + " " + key);
            //Left or right control + V
            if (key == 47 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157))) {
                String toPaste = Sys.getClipboard();
                if (toPaste != null) {
                    text = text.substring(0, cursorPos) + toPaste + text.substring(cursorPos, text.length());
                    cursorPos += toPaste.length();
                }
                return;
            }

            if (key == Keyboard.KEY_BACK) { // backspace
                if (text.length() > 0 && cursorPos > 0) {
                    text = text.substring(0, cursorPos - 1) + text.substring(cursorPos, text.length());
                    cursorPos--;
                }
            } else if (key == Keyboard.KEY_DELETE && cursorPos < text.length()) { // delete
                text = text.substring(0, cursorPos) + text.substring(cursorPos + 1, text.length());
            } else if (key == Keyboard.KEY_RETURN) {
                text = text.substring(0, cursorPos) + '\n' + text.substring(cursorPos, text.length());
                cursorPos++;
            } else if (typedChar == ' ' || UnicodeFontRenderer.alphabet.contains(String.valueOf(typedChar))) {
                text = text.substring(0, cursorPos) + typedChar + text.substring(cursorPos, text.length());
                cursorPos++;
            } else if (key == 203 && cursorPos > 0) { // arrow left
                cursorPos--;
            } else if (key == 205 && cursorPos < text.length()) { // arrow right
                cursorPos++;
            } else if (key == 15) // tab
                for (int i = 0; i < 4; i++) keyTyped(' ', 57);

            if (lastKey != key)
                startTime = System.currentTimeMillis();
            lastKey = key;
            lastChar = typedChar;

            ((LayoutBase) getRoot()).doLayout();
            update();
            ((LayoutBase) getRoot()).doLayout();
            if (onType != null)
                onType.accept(this);
        }
    }

    @Override
    public String getName() {
        return "TextField";
    }

    @Override
    public int getHeight() {
        if (!text.isEmpty()) {
            if (dirty)
                return lineHeight + 2 * padding;

/*            int newLineCount = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '\n')
                    newLineCount++;
            }*/

            return strs.size() * lineHeight + (strs.size() - 1) * lineSpacing + 2 * padding;
        } else
            return lineHeight + 2 * padding;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (dirty) {
            ((LayoutBase) getRoot()).doLayout();
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
            for (int i = 0; i < strs.size(); i++)
                fontRenderer.drawString(strs.get(i), left + padding, top + (lineSpacing + lineHeight) * i + padding, color);

        try {
            if (active) {
                //draw cursor
                int cursor_line = 0;
                int cursor_column = 0;
                int cursor_pos = cursorPos;
                for (int i = 0; cursor_pos > 0; i++) {
                    cursor_column = cursor_pos;
                    cursor_pos -= strs.get(i).length() + 1;
                    if (cursor_pos <= 0) {
                        cursor_line = i;
                        break;
                    }
                }
                int x = left + padding + (cursor_pos == 0 ? 0 : fontRenderer.getStringWidth(strs.get(cursor_line).substring(0, cursor_column))),
                        y = top + (lineSpacing + lineHeight) * (cursor_pos == 0 && cursorPos > 0 ? cursor_line + 1 : cursor_line) + padding;
                drawColoredRect(tess, x, y - 2, x + 1, y + lineHeight + 2, color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
