package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.FancyFrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;

/**
 * Created by maxim on 02.01.2017.
 */
public class GuiConfirmationDialog extends Screen {
    private String text;

    public GuiConfirmationDialog(String text, Runnable onConfirm) {
        this.text = text;
        FancyFrameLayout fancyFrameLayout = new FancyFrameLayout();
        root.setElement(fancyFrameLayout);
        VerticalLayout layout = new VerticalLayout();
        fancyFrameLayout.setElement(layout);
        layout.add(new Text(text));
        layout.add(new Button("Ok").setUseInactiveBackground(false).setOnClick(
                b -> {
                    ScreenStack.close();
                    onConfirm.run();
                }));
        layout.add(new Button("Cancel").setUseInactiveBackground(false).setOnClick(b -> ScreenStack.close()));
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        root.onResize();
    }
}
