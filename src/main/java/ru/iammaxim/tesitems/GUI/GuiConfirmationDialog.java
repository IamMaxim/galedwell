package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;

/**
 * Created by maxim on 02.01.2017.
 */
public class GuiConfirmationDialog extends Screen {
    private String text;

    public GuiConfirmationDialog(String text, Runnable onConfirm) {
        this.text = text;
        contentLayout.setElement(new VerticalLayout()
                .add(new Text(text))
                .add(new HorizontalLayout()
                        .add(new Button("Ok").setUseInactiveBackground(false).setOnClick(
                                b -> {
                                    ScreenStack.close();
                                    onConfirm.run();
                                }))
                        .add(new Button("Cancel").setUseInactiveBackground(false).setOnClick(b -> ScreenStack.close()))
                        .center(true).setWidthOverride(ElementBase.FILL))
                .setSpacing(8).setWidthOverride(ElementBase.FILL)).setPadding(8);
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        root.onResize();
    }
}
