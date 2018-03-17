package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutBase;
import ru.iammaxim.tesitems.GUI.Fonts.UnicodeFontRenderer;
import ru.iammaxim.tesitems.Utils.ClientThings;
import ru.iammaxim.tesitems.Utils.Utils;

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
    private long lastInputTime = 0;
    private static final long inputInterval = 60;
    private char lastChar = 0;
    private int lastKey = 0;
    private FontRenderer fontRenderer = ClientThings.fontRenderer;
    private int cursorPos = 0;
    private int line = 0;
    private int column = 0;

    @Override
    public void click(int relativeX, int relativeY) {
        super.click(relativeX, relativeY);

        line = (relativeY - padding + lineSpacing / 2) / (lineSpacing + lineHeight);
        if (line >= strs.size())
            line = strs.size() - 1;

        cursorPos = 0;
        for (int i = 0; i < line; i++) {
            cursorPos += strs.get(i).length() + 1;
        }

        String currentLine = strs.get(line);
        boolean set = false;
        for (int i = 0; i < currentLine.length(); i++) {
            if (fontRenderer.getStringWidth(currentLine.substring(0, i)) + fontRenderer.getStringWidth(String.valueOf(currentLine.charAt(i))) / 2f > relativeX - padding) {
                column = i;
                set = true;
                break;
            }
        }
        if (!set)
            column = currentLine.length();

        cursorPos += column;
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
        lastInputTime = System.currentTimeMillis();

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
                    calculateCursorPos();
                }
                return;
            }

            if (key == Keyboard.KEY_BACK) { // backspace
                if (text.length() > 0 && cursorPos > 0) {
                    text = text.substring(0, cursorPos - 1) + text.substring(cursorPos, text.length());
                    if (column > 0)
                        column--; // current line
                    else {
                        column = strs.get(line - 1).length(); // prev line
                        line--;
                    }
                    cursorPos--;
                }
            } else if (key == Keyboard.KEY_DELETE && cursorPos < text.length()) { // delete
                text = text.substring(0, cursorPos) + text.substring(cursorPos + 1, text.length());
            } else if (key == Keyboard.KEY_RETURN) { // enter
                text = text.substring(0, cursorPos) + '\n' + text.substring(cursorPos, text.length());
                line++;
                column = 0;
                cursorPos++;
            } else if (typedChar == ' ' || UnicodeFontRenderer.alphabet.contains(String.valueOf(typedChar))) {
                text = text.substring(0, cursorPos) + typedChar + text.substring(cursorPos, text.length());
                column++;
                cursorPos++;
            } else if (key == 203) { // arrow left
                if (cursorPos > 0) {
                    cursorPos--;
                    if (column > 0)
                        column--; // current line
                    else {
                        column = strs.get(line - 1).length(); // prev line
                        line--;
                    }
                }
            } else if (key == 205) { // arrow right
                if (cursorPos < text.length()) {
                    cursorPos++;
                    if (column < strs.get(line).length())
                        column++; // current line
                    else {
                        column = 0; // next line
                        line++;
                    }
                }
            } else if (key == 200) { // arrow up
                if (line <= 0)
                    return;

                cursorPos -= column;
                column = Utils.clamp(0, strs.get(line - 1).length(), column);
                cursorPos -= strs.get(line - 1).length() - column + 1;
                line--;
            } else if (key == 208) { // arrow down
                if (line >= strs.size() - 1)
                    return;

                cursorPos += strs.get(line).length() - column + 1;
                column = Utils.clamp(0, strs.get(line + 1).length(), column);
                cursorPos += column;
                line++;
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

    private void calculateCursorPos() {
        // FIXME
        int cursor_pos = cursorPos;
        for (int i = 0; i < strs.size(); i++) {
            column = cursor_pos;
            cursor_pos -= strs.get(i).length() + 1;
            if (cursor_pos <= 0) {
                line = i;
                break;
            }
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
        super.draw(mouseX, mouseY);

        //check if key is still down
        if (Keyboard.isKeyDown(lastKey)) {
            if (System.currentTimeMillis() - startTime > 400 && System.currentTimeMillis() - lastInputTime > inputInterval)
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
/*
                int cursor_line;
                int cursor_column;
                int cursor_pos = cursorPos;
                for (int i = 0; ; i++) {
                    cursor_column = cursor_pos;
                    cursor_pos -= strs.get(i).length() + 1;
                    if (cursor_pos <= 0) {
                        cursor_line = i;
                        break;
                    }
                }*/
                // draw cursor
                int x = left + padding + (column == 0 ? 0 : fontRenderer.getStringWidth(strs.get(line).substring(0, column))),
//                        y = top + (lineSpacing + lineHeight) * (column == 0 && cursorPos > 0 ? line + 1 : line) + padding;
                        y = top + (lineSpacing + lineHeight) * (line) + padding;
                drawColoredRect(tess, x, y - 2, x + 1, y + lineHeight + 2, color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
