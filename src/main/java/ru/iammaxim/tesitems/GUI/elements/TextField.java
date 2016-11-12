package ru.iammaxim.tesitems.GUI.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;
import ru.iammaxim.tesitems.GUI.Fonts.UnicodeFontRenderer;
import ru.iammaxim.tesitems.GUI.old.GuiTextField;
import ru.iammaxim.tesitems.GUI.old.GuiVerticalLinearLayout;
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
    private int lastStrsSize = 0;
    private Consumer<TextField> onType;
    private int padding = 4;
    private boolean dirty = false;
    private boolean active = false;

    @Override
    public void checkClick(int mouseX, int mouseY) {
        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom) {
            click(mouseX - left, mouseY - top);
            active = true;
        } else {
            active = false;
        }
    }

    public TextField(ElementBase parent) {
        super(parent);
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
        strs = TESItems.fontRenderer.listFormattedStringToWidth(text, width);
        dirty = false;
    }

    public String getHint() {
        return hint;
    }

    @Override
    public int getWidth() {
        if (!text.isEmpty())
            return TESItems.fontRenderer.getStringWidth(text) + 2 * padding;
        else
            return TESItems.fontRenderer.getStringWidth(hint) + 2 * padding;
    }

    public TextField setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public TextField setOnType(Consumer<TextField> onType) {
        this.onType = onType;
        return this;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (active) {
            if (keyCode == Keyboard.KEY_BACK) { //backspace
                if (text.length() > 0)
                    text = text.substring(0, text.length() - 1);
            } else if (keyCode == Keyboard.KEY_RETURN) {
                text = text + '\n';
            } else if (typedChar == ' ' || UnicodeFontRenderer.alphabet.contains(typedChar + "")) {
                text = text + typedChar;
            }
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
            strs = fontRenderer.listFormattedStringToWidth(text, width);
            if (lastStrsSize != strs.size()) {
                lastStrsSize = strs.size();
                if (parent instanceof LayoutBase) {
                    ((LayoutBase) getRoot()).doLayout();
                }
            }
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
            ((LayoutBase)getRoot()).doLayout();
            update();
        }

        Tessellator tess = Tessellator.getInstance();

        drawColoredRect(tess, left, top, right, bottom, 0xff000000);
        //System.out.println(left + " " + top + " " + right + " " + bottom);

        if (!active)
            drawColoredRect(tess, left, top, right, bottom, 0xFF333333);
        else
            drawColoredRect(tess, left, top, right, bottom, 0xFF7F7F7F);
        FontRenderer fontRenderer = TESItems.fontRenderer;
        if (text.isEmpty()) //draw hint
            fontRenderer.drawString(hint, left + padding, top + padding, hintColor);
        else //draw text
            for (int i = 0; i < strs.size(); i++) {
                fontRenderer.drawString(strs.get(i), left + padding, top + (lineSpacing + 8) * i + padding, color);
            }
    }
}
