package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

/**
 * Created by maxim on 11/10/16 at 7:13 PM.
 */
public class ResManager {
    public static final int MAIN_COLOR = 0xff481f09,
            CLICKABLE_TEXT_COLOR = 0xFF0066CC,
            DARK_TRANSPARENT_BG_COLOR = 0x80000000,
            DARK_FRAME_COLOR = 0xBB000000,
            BRIGHT_TEXT_COLOR = 0xffffffff;

    public static boolean enableBlur = true;
    public static ShaderGroup gaussianBlurShader;

    public static void loadShaders() {
        try {
            gaussianBlurShader = new ShaderGroup(Minecraft.getMinecraft().getTextureManager(),
                    Minecraft.getMinecraft().getResourceManager(),
                    Minecraft.getMinecraft().getFramebuffer(),
                    new ResourceLocation("shaders/post/gaussian_blur.json"));
            gaussianBlurShader.createBindFramebuffers(
                    Minecraft.getMinecraft().displayWidth,
                    Minecraft.getMinecraft().displayHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ResourceLocation
            button_short = new ResourceLocation("tesitems:textures/gui/button_short.png"),
            button_short_on = new ResourceLocation("tesitems:textures/gui/button_short_on.png"),
            button_long = new ResourceLocation("tesitems:textures/gui/button_long.png"),
            button_long_on = new ResourceLocation("tesitems:textures/gui/button_long_on.png"),
            button_xtralong = new ResourceLocation("tesitems:textures/gui/button_xtralong.png"),
            button_xtralong_on = new ResourceLocation("tesitems:textures/gui/button_xtralong_on.png"),
            frameBorder_LT = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_LT.png"),
            frameBorder_CT = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_CT.png"),
            frameBorder_RT = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_RT.png"),
            frameBorder_LC = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_LC.png"),
            frameBorder_RC = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_RC.png"),
            frameBorder_LB = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_LB.png"),
            frameBorder_CB = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_CB.png"),
            frameBorder_RB = new ResourceLocation("tesitems:textures/gui/inventory/frameBorder_RB.png"),
            generic_bg = new ResourceLocation("tesitems:textures/gui/generic_bg.png"),
            inv_itemlist_bg = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_bg.png"),
            inv_scrollbar_bg_top = new ResourceLocation("tesitems:textures/gui/inventory/inv_scrollbar_bg_top.png"),
            inv_scrollbar_bg_center = new ResourceLocation("tesitems:textures/gui/inventory/inv_scrollbar_bg_center.png"),
            inv_scrollbar_bg_bottom = new ResourceLocation("tesitems:textures/gui/inventory/inv_scrollbar_bg_bottom.png"),
            inv_scrollbar = new ResourceLocation("tesitems:textures/gui/inventory/inv_scrollbar.png"),
            inv_entry_bg_selected = new ResourceLocation("tesitems:textures/gui/inventory/inv_entry_bg_selected.png"),
            inv_entry_bg_hovered = new ResourceLocation("tesitems:textures/gui/inventory/inv_entry_bg_hovered.png"),
            icon_value = new ResourceLocation("tesitems:textures/gui/icons/value.png"),
            icon_carryweight = new ResourceLocation("tesitems:textures/gui/icons/carryweight.png"),
            icon_damage = new ResourceLocation("tesitems:textures/gui/icons/damage.png"),
            icon_durability = new ResourceLocation("tesitems:textures/gui/icons/durability.png"),
            inv_carryweight_bg = new ResourceLocation("tesitems:textures/gui/inventory/carryweight_bg.png"),
            decor_blue_left = new ResourceLocation("tesitems:textures/gui/decor_blue_left.png"),
            decor_blue_right = new ResourceLocation("tesitems:textures/gui/decor_blue_right.png"),
            arrow_right = new ResourceLocation("tesitems:textures/gui/arrow_right.png"),
            arrow_down = new ResourceLocation("tesitems:textures/gui/arrow_down.png");
}
