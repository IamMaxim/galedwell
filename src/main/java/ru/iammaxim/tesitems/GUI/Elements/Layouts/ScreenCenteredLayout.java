package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Created by maxim on 11/7/16 at 5:56 PM.
 */
public class ScreenCenteredLayout extends FrameLayout {

    public ScreenCenteredLayout() {}

    @Override
    public void doLayout() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        int width = Math.min(getWidth(), res.getScaledWidth() - paddingLeft - paddingRight);
        int height = Math.min(getHeight(), res.getScaledHeight() - paddingTop - paddingBottom);
        setBounds(
                (res.getScaledWidth() - width)/2,
                (res.getScaledHeight() - height)/2,
                (res.getScaledWidth() + width)/2,
                (res.getScaledHeight() + height)/2);
        super.doLayout();
    }
}
