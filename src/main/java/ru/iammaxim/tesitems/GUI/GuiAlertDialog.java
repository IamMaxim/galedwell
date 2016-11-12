package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import ru.iammaxim.tesitems.GUI.Screen;
import ru.iammaxim.tesitems.GUI.elements.*;

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
        FancyFrameLayout fancyFrameLayout = new FancyFrameLayout(root);
        root.setElement(fancyFrameLayout);
        VerticalLayout layout = new VerticalLayout(fancyFrameLayout);
        fancyFrameLayout.setElement(layout);
        layout.add(new Text(layout, text));
        layout.add(new Button(layout).setText("Ok").setUseInactiveBackground(false).center(true).setOnClick(b -> mc.displayGuiScreen(lastScreen)));
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        root.onRescale();
    }
}
