package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.FancyFrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.GUI.Elements.VerticalLayout;

/**
 * Created by maxim on 02.01.2017.
 */
public class GuiConfirmationDialog extends Screen {
    private String text;
    private GuiScreen lastScreen;

    public GuiConfirmationDialog(String text, GuiScreen lastScreen, Runnable onConfirm) {
        this(text, onConfirm);
        this.lastScreen = lastScreen;
    }

    public GuiConfirmationDialog(String text, Runnable onConfirm) {
        this.text = text;
        FancyFrameLayout fancyFrameLayout = new FancyFrameLayout(root);
        root.setElement(fancyFrameLayout);
        VerticalLayout layout = new VerticalLayout(fancyFrameLayout);
        fancyFrameLayout.setElement(layout);
        layout.add(new Text(layout, text));
        layout.add(new Button(layout).setText("Ok").setUseInactiveBackground(false).setOnClick(
                b -> {
                    mc.displayGuiScreen(lastScreen);
                    onConfirm.run();
                }));
        layout.add(new Button(layout).setText("Cancel").setUseInactiveBackground(false).setOnClick(b -> mc.displayGuiScreen(lastScreen)));
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        root.onRescale();
    }
}
