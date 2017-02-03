package ru.iammaxim.tesitems.GUI.old;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;
import ru.iammaxim.tesitems.GUI.Fonts.UnicodeFontRenderer;
import ru.iammaxim.tesitems.TESItems;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by maxim on 11/5/16 at 11:01 PM.
 */
public class GuiTextField extends RenderableBase {
    private String text = "", hint = "";
    private List<String> strs;
    private int padding = 4, hintColor = 0xff999999, color = 0xffffffff;
    private int lineSpacing = 4;
    private int lastStrsSize = 0;
    private Consumer<GuiTextField> onType;

    public GuiTextField(RenderableBase parent) {
        this.parent = parent;
        width = parent.width;
        height = 8 + 2 * padding;
    }

    public GuiTextField(RenderableBase parent, String hint) {
        this(parent);
        setHint(hint);
    }

    public String getText() {
        return text;
    }

    public GuiTextField setText(String s) {
        text = s;
        strs = Minecraft.getMinecraft().fontRendererObj.listFormattedStringToWidth(text, width);
        return this;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int spacing) {
        this.lineSpacing = spacing;
    }

    public GuiTextField setOnType(Consumer<GuiTextField> onType) {
        this.onType = onType;
        return this;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (focused) {
            if (keyCode == Keyboard.KEY_BACK) { //backspace
                if (text.length() > 0)
                    text = text.substring(0, text.length() - 1);
            } else if (keyCode == Keyboard.KEY_RETURN) {
                text = text + '\n';
            } else if (UnicodeFontRenderer.alphabet.contains(typedChar + "")) {
                text = text + typedChar;
            }
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
            strs = fontRenderer.listFormattedStringToWidth(text, width);
            bottom = 2 * padding + top + strs.size() * (8 + lineSpacing) - lineSpacing;
            height = bottom - top;
            if (lastStrsSize != strs.size()) {
                lastStrsSize = strs.size();
                if (parent instanceof GuiVerticalLinearLayout) {
                    ((GuiVerticalLinearLayout) parent).doLayout(parent.top, parent.left);
                }
            }
            if (onType != null)
                onType.accept(this);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Tessellator tess = Tessellator.getInstance();
        if (!focused)
            drawColoredRect(tess, top, left, bottom, right, 0xFF333333);
        else
            drawColoredRect(tess, top, left, bottom, right, 0xFF7F7F7F);
        FontRenderer fontRenderer = TESItems.ClientThings.fontRenderer;
        if (text.isEmpty()) //draw hint
            fontRenderer.drawString(hint, left + padding, top + padding, hintColor);
        else //draw text
            for (int i = 0; i < strs.size(); i++) {
                fontRenderer.drawString(strs.get(i), left + padding, padding + top + (lineSpacing + 8) * i, color);
            }
    }
}
