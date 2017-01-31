package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.FancyFrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;

/**
 * Created by maxim on 11/6/16 at 1:53 AM.
 */
public class GuiAlertDialog extends Screen {
    private String text;
    private GuiScreen lastScreen;

    public GuiAlertDialog(String text, GuiScreen lastScreen) {
        this(text);
        this.lastScreen = lastScreen;
    }

    public GuiAlertDialog(String text) {
        super();
        this.text = text;
        FancyFrameLayout fancyFrameLayout = new FancyFrameLayout();
        root.setElement(fancyFrameLayout);
        fancyFrameLayout.setPadding(8);
        VerticalLayout layout = new VerticalLayout();
        fancyFrameLayout.setElement(layout);
        layout.add(new Text(text));
        layout.add(new Button("Ok").setUseInactiveBackground(false).setOnClick(
                b -> mc.displayGuiScreen(lastScreen)).setWidthOverride(ElementBase.FILL));
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        root.onResize();
    }
}
