package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.iammaxim.tesitems.GUI.old.RenderableBase;
import ru.iammaxim.tesitems.Inventory.InventoryClient;
import ru.iammaxim.tesitems.Inventory.InventoryItemStack;
import ru.iammaxim.tesitems.Items.ItemValueManager;
import ru.iammaxim.tesitems.Items.ItemWeightManager;
import ru.iammaxim.tesitems.Items.Weapon;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static ru.iammaxim.tesitems.GUI.ResManager.*;

/**
 * Created by maxim on 7/26/16 at 5:24 PM.
 */
public class GuiInventoryItemList {
    private static final int
            entryHeight = 26,
            paddingTop = 20,
            paddingBottom = 20,
            paddingLeft = 30,
            width = 390,
            scrollBarSize = 16,
            borderSize = 18,
            nameWidth = 200,
            valueWidth = 40,
            weightWidth = 40,
            damageWidth = 40,
            durabilityWidth = 40,
            topIconsHeight = 24,
            textColor = 0xff481f09;
    private final int screenWidth;
    private final int screenHeight;
    private final int top;
    private final int bottom;
    private final int right;
    private final int left;
    private final int slotHeight;
    private final Minecraft client;
    int scale;
    private int mouseX;
    private int mouseY;
    private InventoryClient inv;
    private int scrollUpActionId;
    private int scrollDownActionId;
    private float initialMouseClickY = -2.0F, lastMouseX = -1, lastMouseY = -1;
    private float scrollFactor;
    private float scrollDistance;
    private long lastClickTime = 0L;
    private int lastHash = 0, lastItemCount = 0;
    private List<InventoryItemStack> stacks = new ArrayList<>();
    private HashMap<EntityEquipmentSlot, ItemStack> equipped = new HashMap<>();
    private float characterRotation = 0, characterRotationFactor = 1;
    private int dividersColor = 0x60481f09;

    public GuiInventoryItemList(InventoryClient inv, Minecraft client) {
        ScaledResolution res = new ScaledResolution(client);
        this.client = client;
        this.top = paddingTop;
        this.bottom = res.getScaledHeight() - paddingBottom;
        this.slotHeight = entryHeight;
        this.left = paddingLeft;
        this.right = width + this.left;
        this.screenWidth = res.getScaledWidth();
        this.screenHeight = res.getScaledHeight();
        scale = (int) (screenHeight * 0.3f);
        this.inv = inv;

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        for (ItemStack is : player.getArmorInventoryList()) {
            if (is != null && is.getItem() instanceof ItemArmor)
                equipped.put(((ItemArmor) is.getItem()).getEquipmentSlot(), is);
        }
        equipped.put(EntityEquipmentSlot.MAINHAND, player.getHeldItemMainhand());
        equipped.put(EntityEquipmentSlot.OFFHAND, player.getHeldItemOffhand());
    }

    public void clickSlot(EntityEquipmentSlot slot, int index) {
        if (index >= inv.size()) return;
        ItemStack is = stacks.get(index).stack;
        index = inv.getItemList().indexOf(is);
        if (index == -1) {
            System.out.println("index == -1. Something goes wrong");
            return;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            inv.drop(inv.player, index, 1);
            //mark list dirty
            lastHash = 0;
        } else {
            equip(slot, index);
        }
    }

    public void equip(EntityEquipmentSlot slot, int index) {
        ItemStack is = inv.get(index);
        if (is.getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor) is.getItem();
            if (equipped.get(armor.getEquipmentSlot()) == is) {
                inv.equip(armor.getEquipmentSlot(), -1);
                equipped.put(armor.getEquipmentSlot(), null);
            } else {
                inv.equip(armor.getEquipmentSlot(), index);
                equipped.put(armor.getEquipmentSlot(), is);
            }
        } else {
            if (slot == EntityEquipmentSlot.MAINHAND && !(is.getItem() instanceof Weapon || is.getItem() instanceof ItemTool))
                return;
            if (equipped.get(slot) == is) {
                inv.equip(slot, -1);
                equipped.put(slot, null);
            } else {
                inv.equip(slot, index);
                equipped.put(slot, is);
            }
        }
    }

    private int getSize() {
        return stacks.size();
    }

    private void elementLeftClicked(int index) {
        clickSlot(EntityEquipmentSlot.MAINHAND, index);
    }

    private void elementRightClicked(int index) {
        clickSlot(EntityEquipmentSlot.OFFHAND, index);
    }

    public void drawVerticalDividers() {
        Tessellator tess = Tessellator.getInstance();
        int l = left + nameWidth + 1;
        RenderableBase.drawColoredRect(tess, top, l, bottom, l + 1, dividersColor);
        l += valueWidth + 4;
        RenderableBase.drawColoredRect(tess, top, l, bottom, l + 1, dividersColor);
        l += weightWidth + 4;
        RenderableBase.drawColoredRect(tess, top, l, bottom, l + 1, dividersColor);
        l += damageWidth + 4;
        RenderableBase.drawColoredRect(tess, top, l, bottom, l + 1, dividersColor);
    }

    private void drawBackground() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float tmp;

        //background
        drawTexturedRect(left, top, right, bottom, 0, scrollDistance/width, 1, (float) (bottom - top) / width + scrollDistance/width, inv_itemlist_bg);

        //border
        //LT
        drawTexturedRect(left - borderSize, top - borderSize, left, top, frameBorder_LT);
        //CT
        tmp = (right - left) / 22.75f / borderSize;
        drawTexturedRect(left, top - borderSize, right, top, tmp, 1, frameBorder_CT);
        //CB
        drawTexturedRect(left, bottom, right, bottom + borderSize, tmp, 1, frameBorder_CB);
        //RT
        drawTexturedRect(right, top - borderSize, right + borderSize, top, frameBorder_RT);
        //LC
        tmp = (bottom - top) / 22.75f / borderSize;
        drawTexturedRect(left - borderSize, top, left, bottom, 1, tmp, frameBorder_LC);
        //RC
        drawTexturedRect(right, top, right + borderSize, bottom, 1, tmp, frameBorder_RC);
        //LB
        drawTexturedRect(left - borderSize, bottom, left, bottom + borderSize, frameBorder_LB);
        //RB
        drawTexturedRect(right, bottom, right + borderSize, bottom + borderSize, frameBorder_RB);
    }

    public void drawTopIcons() {
        int t = top + 8;
        TESItems.fontRenderer.drawString("Name", left + (nameWidth - TESItems.fontRenderer.getStringWidth("Name"))/2, t, textColor);
        t = top + 4;
        int l = left + 8 + nameWidth;
        int tmp;
        GlStateManager.color(1, 1, 1, 1);
        tmp = l + (valueWidth - 24) / 2;
        drawTexturedRect(tmp, t, tmp + 16, t + 16, icon_value);
        l += valueWidth + 4;
        tmp = l + (weightWidth - 24) / 2;
        drawTexturedRect(tmp, t, tmp + 16, t + 16, icon_carryweight);
        l += weightWidth + 4;
        tmp = l + (damageWidth - 24) / 2;
        drawTexturedRect(tmp, t, tmp + 16, t + 16, icon_damage);
        l += damageWidth + 4;
        tmp = l + (durabilityWidth - 24) / 2;
        drawTexturedRect(tmp, t, tmp + 16, t + 16, icon_durability);
        RenderableBase.drawColoredRect(Tessellator.getInstance(), top + topIconsHeight - 1, left, top + topIconsHeight, right, dividersColor);
    }

    public boolean isEquipped(ItemStack stack) {
        return equipped.containsValue(stack);
    }

    private void drawSelectedSlotBackground(int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
        VertexBuffer vb = tess.getBuffer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(inv_entry_bg_selected);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, slotTop + slotBuffer + 2, 0).tex(0, 1).endVertex();
        vb.pos(entryRight, slotTop + slotBuffer + 2, 0).tex(1, 1).endVertex();
        vb.pos(entryRight, slotTop - 2, 0).tex(1, 0).endVertex();
        vb.pos(left, slotTop - 2, 0).tex(0, 0).endVertex();
        tess.draw();
    }

    private void drawHoveredSlotBackground(int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
        VertexBuffer vb = tess.getBuffer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(inv_entry_bg_hovered);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, slotTop + slotBuffer + 2, 0).tex(0, 1).endVertex();
        vb.pos(entryRight, slotTop + slotBuffer + 2, 0).tex(1, 1).endVertex();
        vb.pos(entryRight, slotTop - 2, 0).tex(1, 0).endVertex();
        vb.pos(left, slotTop - 2, 0).tex(0, 0).endVertex();
        tess.draw();
    }

    private void drawSlot(int index, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
        ItemStack stack = stacks.get(index).stack;
        if (isEquipped(stack)) {
            drawSelectedSlotBackground(entryRight, slotTop, slotBuffer, tess);
        }
        String s;
        TESItems.fontRenderer.drawString(
                TESItems.fontRenderer.trimStringToWidth("(" + stack.stackSize + ") " + stack.getDisplayName(), nameWidth),
                left + 4, slotTop + 8, textColor);
        s = TESItems.fontRenderer.trimStringToWidth(ItemValueManager.getValue(stack) + "", valueWidth);
        TESItems.fontRenderer.drawString(s, left + 4 + nameWidth + (valueWidth - TESItems.fontRenderer.getStringWidth(s))/2, slotTop + 8, textColor);
        s = TESItems.fontRenderer.trimStringToWidth(ItemWeightManager.getWeightString(stack), weightWidth);
        TESItems.fontRenderer.drawString(s, left + 8 + nameWidth + valueWidth + (weightWidth - TESItems.fontRenderer.getStringWidth(s))/2, slotTop + 8, textColor);
        if (stack.getItem() instanceof Weapon) {
            Weapon weapon = (Weapon) stack.getItem();
            s = TESItems.fontRenderer.trimStringToWidth(weapon.getDamageVsEntity() + "", damageWidth);
            TESItems.fontRenderer.drawString(s, left + 12 + nameWidth + valueWidth + weightWidth + (damageWidth - TESItems.fontRenderer.getStringWidth(s))/2, slotTop + 8, textColor);
        }
        if (stack.isItemStackDamageable()) {
            s = TESItems.fontRenderer.trimStringToWidth((int)(100 * (1 - (float) stack.getItemDamage() / stack.getMaxDamage())) + "", durabilityWidth);
            TESItems.fontRenderer.drawString(s, left + 16 + nameWidth + valueWidth + weightWidth + damageWidth + (durabilityWidth - TESItems.fontRenderer.getStringWidth(s))/2, slotTop + 8, textColor);
        }
    }

    private int getContentHeight() {
        return getSize() * slotHeight;
    }

    private void applyScrollLimits() {
        int listHeight = getContentHeight() - (bottom - top - topIconsHeight - 4);

        if (listHeight < 0) {
            listHeight /= 2;
        }

        if (this.scrollDistance < 0.0F) {
            this.scrollDistance = 0.0F;
        }

        if (this.scrollDistance > (float) listHeight) {
            this.scrollDistance = (float) listHeight;
        }
    }

    private void updateInventory() {
        int hash = inv.hashCode(), itemCount = inv.size();
        if ((hash) != lastHash || (itemCount) != lastItemCount) {
            lastItemCount = itemCount;
            lastHash = hash;
            stacks.clear();
            inv.getItemList().forEach(stack -> stacks.add(new InventoryItemStack(stack)));
            Collections.sort(stacks);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        updateInventory();

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        Tessellator tess = Tessellator.getInstance();
        VertexBuffer worldr = tess.getBuffer();

        //draw character
        int posX = right + (screenWidth - right) / 2, posY = screenHeight / 2 + scale * 2 - (int) (screenHeight * 0.3f);
        drawEntityOnScreen(posX, posY, scale, characterRotation, client.thePlayer);
        drawCarryweight(tess);
        drawBackground();
        drawTopIcons();
        drawVerticalDividers();

        boolean isHovering = mouseX >= left && mouseX <= left + width && mouseY >= top && mouseY <= bottom;
        int listLength = this.getSize();
        int scrollBarRight = left + width;
        int scrollBarLeft = scrollBarRight - scrollBarSize / 2;
        int entryLeft = this.left;
        int entryRight = scrollBarLeft - 1;
        int viewHeight = this.bottom - this.top - topIconsHeight;
        int border = 4;
        int mouseListY = mouseY - this.top - topIconsHeight + (int) this.scrollDistance - border;
        int slotIndex = mouseListY / this.slotHeight;

        if (Mouse.isButtonDown(0)) {
            if (this.initialMouseClickY == -1.0F) {
                if (isHovering) {
                    if (mouseX >= entryLeft && mouseX <= entryRight && slotIndex >= 0 && mouseListY >= 0 && slotIndex < listLength) {
                        this.elementLeftClicked(slotIndex);
                        this.lastClickTime = System.currentTimeMillis();
                    }

                    if (mouseX >= scrollBarLeft && mouseX <= scrollBarRight) {
                        this.scrollFactor = -1.0F;
                        int scrollHeight = this.getContentHeight() - viewHeight - border;
                        if (scrollHeight < 1) scrollHeight = 1;

                        int var13 = (int) ((float) (viewHeight * viewHeight) / (float) this.getContentHeight());

                        if (var13 < 32) var13 = 32;
                        if (var13 > viewHeight - border * 2)
                            var13 = viewHeight - border * 2;

                        this.scrollFactor /= (float) (viewHeight - scrollBarSize * 3.5) / (float) scrollHeight;
                    } else {
                        this.scrollFactor = 1.0F;
                    }

                    this.initialMouseClickY = mouseY;
                } else {
                    this.initialMouseClickY = -2.0F;
                }
            } else if (this.initialMouseClickY >= 0.0F) {
                this.scrollDistance -= ((float) mouseY - this.initialMouseClickY) * this.scrollFactor;
                this.initialMouseClickY = (float) mouseY;
            }
            if (lastMouseX == -1)
                lastMouseX = mouseX;
            if (mouseX > right + borderSize)
                characterRotation += (lastMouseX - mouseX) * characterRotationFactor;
            lastMouseX = mouseX;

            if (lastMouseY == -1)
                lastMouseY = mouseY;
            if (mouseX > right + borderSize) {
                scale += (mouseY - lastMouseY);
                clampScale();
            }
            lastMouseY = mouseY;
        } else if (Mouse.isButtonDown(1)) {
            if (this.initialMouseClickY == -1.0F) {
                if (mouseX >= entryLeft && mouseX <= entryRight && slotIndex >= 0 && mouseListY >= 0 && slotIndex < listLength) {
                    elementRightClicked(slotIndex);
                }
                this.initialMouseClickY = mouseY;
            }
        } else {
            this.initialMouseClickY = -1.0F;
            lastMouseX = -1;
            lastMouseY = -1;
        }

        this.applyScrollLimits();

        ScaledResolution res = new ScaledResolution(client);
        double scaleW = client.displayWidth / res.getScaledWidth_double();
        double scaleH = client.displayHeight / res.getScaledHeight_double();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) (left * scaleW), (int) (client.displayHeight - (bottom * scaleH)), (int) (width * scaleW), (int) (viewHeight * scaleH));
        int baseY = this.top + topIconsHeight + border - (int) this.scrollDistance;
        for (int slotIdx = 0; slotIdx < listLength; ++slotIdx) {
            int slotTop = baseY + slotIdx * this.slotHeight;
            int slotBuffer = this.slotHeight - border;
            if (slotTop <= this.bottom && slotTop + slotBuffer >= this.top + topIconsHeight) {
                //draw hover bg if mouse if hovering slot
                if (isHovering) {
                    if (mouseX >= entryLeft && mouseX <= entryRight && slotIndex >= 0 && mouseListY >= 0 && slotIndex < listLength && slotIdx == slotIndex) {
                        drawHoveredSlotBackground(entryRight, slotTop, slotBuffer, tess);
                    }
                }
                //draw slot itself
                this.drawSlot(slotIdx, entryRight, slotTop, slotBuffer, tess);
            }
        }
        GlStateManager.disableDepth();
        int extraHeight = (this.getContentHeight() + border) - viewHeight;
        if (extraHeight > 0) {
            int height = (viewHeight * viewHeight) / this.getContentHeight();
            if (height < 32) height = 32;
            if (height > viewHeight - border * 2)
                height = viewHeight - border * 2;
            int barTop = (int) (scrollDistance * (viewHeight - 3 * scrollBarSize) / extraHeight + top + topIconsHeight + scrollBarSize);
            if (barTop < this.top) {
                barTop = this.top;
            }
            int barBottom = barTop + scrollBarSize;

            //draw scrollbar
            GlStateManager.color(1, 1, 1, 1);
            drawTexturedRect(scrollBarLeft, top + topIconsHeight, scrollBarRight, top + topIconsHeight + scrollBarSize, inv_scrollbar_bg_top);
            drawTexturedRect(scrollBarLeft, top + topIconsHeight + scrollBarSize, scrollBarRight, bottom - scrollBarSize, inv_scrollbar_bg_center);
            drawTexturedRect(scrollBarLeft, bottom - scrollBarSize, scrollBarRight, bottom, inv_scrollbar_bg_bottom);
            drawTexturedRect(scrollBarLeft, barTop, scrollBarRight, barBottom, inv_scrollbar);
        }
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    private void drawCarryweight(Tessellator tess) {
        int l = (screenWidth + right) / 2 - 50,
                r = l + 100,
                t = screenHeight - 24,
                b = screenHeight;
        VertexBuffer vb = tess.getBuffer();
        client.getTextureManager().bindTexture(inv_carryweight_bg);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(l, b, 0.0D).tex(0.0D, 1.0D).endVertex();
        vb.pos(r, b, 0.0D).tex(1.0D, 1.0D).endVertex();
        vb.pos(r, t, 0.0D).tex(1.0D, 0.0D).endVertex();
        vb.pos(l, t, 0.0D).tex(0.0D, 0.0D).endVertex();
        tess.draw();

        client.getTextureManager().bindTexture(icon_carryweight);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(l + 4, b - 4, 0.0D).tex(0.0D, 1.0D).endVertex();
        vb.pos(l + 20, b - 4, 0.0D).tex(1.0D, 1.0D).endVertex();
        vb.pos(l + 20, t + 4, 0.0D).tex(1.0D, 0.0D).endVertex();
        vb.pos(l + 4, t + 4, 0.0D).tex(0.0D, 0.0D).endVertex();
        tess.draw();

        TESItems.fontRenderer.drawString((int) (inv.carryweight) + "/" + (int) (TESItems.getCapability(inv.player).getMaxCarryWeight()), l + 24, t + 8, textColor);
    }

    private void clampScale() {
        if (scale < screenHeight * 0.3f)
            scale = (int) (screenHeight * 0.3f);
        if (scale > screenHeight)
            scale = screenHeight;
    }

    public void drawEntityOnScreen(int posX, int posY, int scale, float rotX, EntityLivingBase entity) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, -500.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = entity.renderYawOffset;
        float f1 = entity.rotationYaw;
        float f2 = entity.rotationPitch;
        float f3 = entity.prevRotationYawHead;
        float f4 = entity.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        entity.renderYawOffset = rotX;
        entity.rotationYaw = rotX;
        entity.rotationPitch = 0;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        entity.renderYawOffset = f;
        entity.rotationYaw = f1;
        entity.rotationPitch = f2;
        entity.prevRotationYawHead = f3;
        entity.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public void drawTexturedRect(int left, int top, int right, int bottom, ResourceLocation texture) {
        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();
        client.getTextureManager().bindTexture(texture);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(0, 1).endVertex();
        vb.pos(right, bottom, 0).tex(1, 1).endVertex();
        vb.pos(right, top, 0).tex(1, 0).endVertex();
        vb.pos(left, top, 0).tex(0, 0).endVertex();
        tess.draw();
    }

    public void drawTexturedRect(int left, int top, int right, int bottom, float UVx, float UVy, ResourceLocation texture) {
        GlStateManager.enableTexture2D();
        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();
        client.getTextureManager().bindTexture(texture);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(0, UVy).endVertex();
        vb.pos(right, bottom, 0).tex(UVx, UVy).endVertex();
        vb.pos(right, top, 0).tex(UVx, 0).endVertex();
        vb.pos(left, top, 0).tex(0, 0).endVertex();
        tess.draw();
    }

    public void drawTexturedRect(int left, int top, int right, int bottom, float UVxStart, float UVyStart, float UVxEnd, float UVyEnd, ResourceLocation texture) {
        GlStateManager.enableTexture2D();
        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();
        client.getTextureManager().bindTexture(texture);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(UVxStart, UVyEnd).endVertex();
        vb.pos(right, bottom, 0).tex(UVxEnd, UVyEnd).endVertex();
        vb.pos(right, top, 0).tex(UVxEnd, UVyStart).endVertex();
        vb.pos(left, top, 0).tex(UVxStart, UVyStart).endVertex();
        tess.draw();
    }
}
