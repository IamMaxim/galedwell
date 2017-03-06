package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.ItemRenderer;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;
import ru.iammaxim.tesitems.Utils.ClientThings;

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

        ScaledResolution res = new ScaledResolution(TESItems.getMinecraft());
        drawHotBar(res);
        drawBars(res);
    }

    private static void drawHotBar(ScaledResolution res) {
        Tessellator tess = Tessellator.getInstance();
        int left = (res.getScaledWidth() - 40) / 2,
                top = res.getScaledHeight() - 22,
                right = (res.getScaledWidth() + 40) / 2,
                bottom = res.getScaledHeight();

        if (Minecraft.isFancyGraphicsEnabled() && ResManager.enableBlur) {
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

        EntityPlayer player = TESItems.getClientPlayer();
        ItemStack mainhand = player.getHeldItemMainhand();
        ItemStack offhand = player.getHeldItemOffhand();
        if (mainhand != null) {
            ItemRenderer.drawItem(mainhand, left + 22, top + 1);
            if (mainhand.stackSize != 1)
                ClientThings.fontRenderer.drawString(String.valueOf(mainhand.stackSize), left + 33, top + 14, ResManager.BRIGHT_TEXT_COLOR);
        }
        if (offhand != null) {
            ItemRenderer.drawItem(offhand, left + 2, top + 1);
            if (offhand.stackSize != 1)
                ClientThings.fontRenderer.drawString(String.valueOf(offhand.stackSize), left + 13, top + 14, ResManager.BRIGHT_TEXT_COLOR);
        }
    }

    private static void drawBars(ScaledResolution res) {
        int bar_height = 6, bar_width = 100;
        int bar_spacing = 3;

        int left = 10,
                top = res.getScaledHeight() - 3 * bar_height - 2 * bar_spacing - 15,
                right = 12 + bar_width;

        EntityPlayer player = TESItems.getClientPlayer();
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        Tessellator tess = Tessellator.getInstance();
        //draw healthbar
        drawBar(tess, left, top, right, top + bar_height, player.getHealth() / player.getMaxHealth(), 0x99ff3300);
        //draw magicka bar
        //TODO: add magicka
        drawBar(tess, left, top + bar_height + bar_spacing, right, top + 2 * bar_height + bar_spacing, 1, 0x993399ff);
        //draw hunger bar
        drawBar(tess, left, top + 2 * bar_height + 2 * bar_spacing, right, top + 3 * bar_height + 2 * bar_spacing, (float) player.getFoodStats().getFoodLevel() / 20, 0x99996633);
    }

    private static void drawBar(Tessellator tess, int left, int top, int right, int bottom, float progress, int color) {
        ElementBase.drawColoredRect(tess, left, top, right, bottom, ResManager.DARK_TRANSPARENT_BG_COLOR);
        ElementBase.drawColoredRect(tess, left, top, (int) (left + (right - left) * progress), bottom, color);
    }
}
