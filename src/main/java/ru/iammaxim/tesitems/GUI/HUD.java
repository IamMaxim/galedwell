package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.ItemRenderer;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 2/26/17 at 7:23 PM.
 */
public class HUD {
    public static void drawHUD() {
//draw notifications
        if (TESItems.getMinecraft().currentScreen == null) {
            NotificationManager.draw();
            TESItems.getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
        } else {
            //check if vanilla inventory is opened and close it if so
            if (TESItems.getMinecraft().currentScreen instanceof net.minecraft.client.gui.inventory.GuiInventory)
                TESItems.getMinecraft().displayGuiScreen(null);
        }

        //render hotbar
        Tessellator tess = Tessellator.getInstance();
        ScaledResolution res = new ScaledResolution(TESItems.getMinecraft());
        int left = (res.getScaledWidth() - 40) / 2,
                top = res.getScaledHeight() - 20,
                right = (res.getScaledWidth() + 40) / 2,
                bottom = res.getScaledHeight();

        if (ResManager.enableBlur) {
            if (ResManager.gaussianBlurShader == null)
                ResManager.loadShaders();

            if (ResManager.gaussianBlurShader == null) {
                System.out.println("ERROR! Shader wasn't loaded!");
                return;
            }

            ElementBase.drawBlur(res, left, top, right, bottom);
        }

        ElementBase.drawColoredRect(tess, left, top, right, bottom, ResManager.DARK_TRANSPARENT_BG_COLOR);
        ElementBase.drawColoredRect(tess, left - 1, top, left, bottom, ResManager.DARK_FRAME_COLOR);
        ElementBase.drawColoredRect(tess, left - 1, top - 1, right + 1, top, ResManager.DARK_FRAME_COLOR);
        ElementBase.drawColoredRect(tess, right, top, right + 1, bottom, ResManager.DARK_FRAME_COLOR);

        ItemStack mainhand = TESItems.getClientPlayer().getHeldItemMainhand();
        ItemStack offhand = TESItems.getClientPlayer().getHeldItemOffhand();
        if (mainhand != null)
            ItemRenderer.drawItem(mainhand, left + 22, top + 2);
        if (offhand != null)
            ItemRenderer.drawItem(offhand, left + 2, top + 2);
    }
}
