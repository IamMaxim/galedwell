package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
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
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.iammaxim.tesitems.Inventory.InventoryClient;
import ru.iammaxim.tesitems.Inventory.InventoryItemStack;
import ru.iammaxim.tesitems.Items.Weapon;
import ru.iammaxim.tesitems.TESItems;

import java.io.IOException;
import java.util.*;

/**
 * Created by maxim on 7/26/16 at 5:24 PM.
 */
public class GuiInventoryItemList {
    private static final int
            entry_height = 15,
            padding_top = 20,
            padding_bottom = 20,
            padding_left = 30,
            width = 206,
            scrollBarSize = 6,
            borderSize = 10;
    private final int listHeight;
    private final int screenWidth;
    private final int screenHeight;
    private final int top;
    private final int bottom;
    private final int right;
    private final int left;
    private final int slotHeight;
    private final Minecraft client;
    int scale;
    private static ResourceLocation
            inv_itemlist_bg = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_bg.png"),
            inv_itemlist_border_LT = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_LT.png"),
            inv_itemlist_border_CT = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_CT.png"),
            inv_itemlist_border_RT = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_RT.png"),
            inv_itemlist_border_LC = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_LC.png"),
            inv_itemlist_border_RC = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_RC.png"),
            inv_itemlist_border_LB = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_LB.png"),
            inv_itemlist_border_CB = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_CB.png"),
            inv_itemlist_border_RB = new ResourceLocation("tesitems:textures/gui/inventory/inv_itemlist_border_RB.png"),
            inv_scrollbar_bg_top = new ResourceLocation("tesitems:textures/gui/inventory/inv_scrollbar_bg_top.png"),
            inv_scrollbar_bg_center = new ResourceLocation("tesitems:textures/gui/inventory/inv_scrollbar_bg_center.png"),
            inv_scrollbar_bg_bottom = new ResourceLocation("tesitems:textures/gui/inventory/inv_scrollbar_bg_bottom.png"),
            inv_scrollbar_top = new ResourceLocation("tesitems:textures/gui/inventory/inv_scrollbar_top.png"),
            inv_scrollbar_center = new ResourceLocation("tesitems:textures/gui/inventory/inv_scrollbar_center.png"),
            inv_scrollbar_bottom = new ResourceLocation("tesitems:textures/gui/inventory/inv_scrollbar_bottom.png"),
            inv_entry_bg_selected = new ResourceLocation("tesitems:textures/gui/inventory/inv_entry_bg_selected.png"),
            inv_entry_bg_hovered = new ResourceLocation("tesitems:textures/gui/inventory/inv_entry_bg_hovered.png"),
            icon_carryweight = new ResourceLocation("tesitems:textures/gui/icons/carryweight.png"),
            inv_carryweight_bg = new ResourceLocation("tesitems:textures/gui/inventory/carryweight_bg.png");
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

    public GuiInventoryItemList(InventoryClient inv, Minecraft client) {
        ScaledResolution res = new ScaledResolution(client);
        this.client = client;
        this.listHeight = res.getScaledHeight() - padding_bottom - padding_top;
        this.top = padding_top;
        this.bottom = res.getScaledHeight() - padding_bottom;
        this.slotHeight = entry_height;
        this.left = padding_left;
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

    private void drawBackground(Tessellator tess) {
        VertexBuffer vb = tess.getBuffer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float tmp;

        //background
        client.getTextureManager().bindTexture(inv_itemlist_bg);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(0, (float) (bottom - top) / width).endVertex();
        vb.pos(right, bottom, 0).tex(1, (float) (bottom - top) / width).endVertex();
        vb.pos(right, top, 0).tex(1, 0).endVertex();
        vb.pos(left, top, 0).tex(0, 0).endVertex();
        tess.draw();

        //border

        //LT
        client.getTextureManager().bindTexture(inv_itemlist_border_LT);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left - borderSize, top, 0).tex(0, 1).endVertex();
        vb.pos(left, top, 0).tex(1, 1).endVertex();
        vb.pos(left, top - borderSize, 0).tex(1, 0).endVertex();
        vb.pos(left - borderSize, top - borderSize, 0).tex(0, 0).endVertex();
        tess.draw();

        //CT
        tmp = Math.round((right - left) / borderSize);
        client.getTextureManager().bindTexture(inv_itemlist_border_CT);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, top, 0).tex(0, 1).endVertex();
        vb.pos(right, top, 0).tex(tmp, 1).endVertex();
        vb.pos(right, top - borderSize, 0).tex(tmp, 0).endVertex();
        vb.pos(left, top - borderSize, 0).tex(0, 0).endVertex();
        tess.draw();

        //CB
        client.getTextureManager().bindTexture(inv_itemlist_border_CB);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom + borderSize, 0).tex(0, 1).endVertex();
        vb.pos(right, bottom + borderSize, 0).tex(tmp, 1).endVertex();
        vb.pos(right, bottom, 0).tex(tmp, 0).endVertex();
        vb.pos(left, bottom, 0).tex(0, 0).endVertex();
        tess.draw();

        //RT
        client.getTextureManager().bindTexture(inv_itemlist_border_RT);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(right, top, 0).tex(0, 1).endVertex();
        vb.pos(right + borderSize, top, 0).tex(1, 1).endVertex();
        vb.pos(right + borderSize, top - borderSize, 0).tex(1, 0).endVertex();
        vb.pos(right, top - borderSize, 0).tex(0, 0).endVertex();
        tess.draw();

        //LC
        tmp = Math.round((bottom - top) / borderSize);
        client.getTextureManager().bindTexture(inv_itemlist_border_LC);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left - borderSize, bottom, 0).tex(0, tmp).endVertex();
        vb.pos(left, bottom, 0).tex(1, tmp).endVertex();
        vb.pos(left, top, 0).tex(1, 0).endVertex();
        vb.pos(left - borderSize, top, 0).tex(0, 0).endVertex();
        tess.draw();

        //RC
        client.getTextureManager().bindTexture(inv_itemlist_border_RC);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(right, bottom, 0).tex(0, tmp).endVertex();
        vb.pos(right + borderSize, bottom, 0).tex(1, tmp).endVertex();
        vb.pos(right + borderSize, top, 0).tex(1, 0).endVertex();
        vb.pos(right, top, 0).tex(0, 0).endVertex();
        tess.draw();

        //LB
        client.getTextureManager().bindTexture(inv_itemlist_border_LB);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left - borderSize, bottom + borderSize, 0).tex(0, 1).endVertex();
        vb.pos(left, bottom + borderSize, 0).tex(1, 1).endVertex();
        vb.pos(left, bottom, 0).tex(1, 0).endVertex();
        vb.pos(left - borderSize, bottom, 0).tex(0, 0).endVertex();
        tess.draw();

        //RB
        client.getTextureManager().bindTexture(inv_itemlist_border_RB);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(right, bottom + borderSize, 0).tex(0, 1).endVertex();
        vb.pos(right + borderSize, bottom + borderSize, 0).tex(1, 1).endVertex();
        vb.pos(right + borderSize, bottom, 0).tex(1, 0).endVertex();
        vb.pos(right, bottom, 0).tex(0, 0).endVertex();
        tess.draw();
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
        String s = client.fontRendererObj.trimStringToWidth(String.format("(%d) %s", stack.stackSize, stack.getDisplayName()), width - 14);
        client.fontRendererObj.drawString(s, left + 4, slotTop + 2, 0xffffffff);
    }

    private int getContentHeight() {
        return this.getSize() * this.slotHeight;
    }

    private void applyScrollLimits() {
        int listHeight = this.getContentHeight() - (this.bottom - this.top - 4);

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

    public void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == this.scrollUpActionId) {
                this.scrollDistance -= (float) (this.slotHeight * 2 / 3);
                this.initialMouseClickY = -2.0F;
                this.applyScrollLimits();
            } else if (button.id == this.scrollDownActionId) {
                this.scrollDistance += (float) (this.slotHeight * 2 / 3);
                this.initialMouseClickY = -2.0F;
                this.applyScrollLimits();
            }
        }
    }

    public void handleMouseInput(int mouseX, int mouseY) throws IOException {
        boolean isHovering = mouseX >= left && mouseX <= left + width && mouseY >= top && mouseY <= bottom;
        if (!isHovering)
            return;
        int scroll = Mouse.getEventDWheel();
        if (scroll != 0) {
            this.scrollDistance += (scroll / -240.0F * this.slotHeight);
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
        drawBackground(tess);

        boolean isHovering = mouseX >= left && mouseX <= left + width && mouseY >= top && mouseY <= bottom;
        int listLength = this.getSize();
        int scrollBarRight = left + width;
        int scrollBarLeft = scrollBarRight - scrollBarSize;
        int entryLeft = this.left;
        int entryRight = scrollBarLeft - 1;
        int viewHeight = this.bottom - this.top;
        int border = 4;
        int mouseListY = mouseY - this.top + (int) this.scrollDistance - border;
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

                        this.scrollFactor /= (float) (viewHeight - var13) / (float) scrollHeight;
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
        int baseY = this.top + border - (int) this.scrollDistance;
        for (int slotIdx = 0; slotIdx < listLength; ++slotIdx) {
            int slotTop = baseY + slotIdx * this.slotHeight;
            int slotBuffer = this.slotHeight - border;
            if (slotTop <= this.bottom && slotTop + slotBuffer >= this.top) {
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
            int barTop = (int) this.scrollDistance * (viewHeight - height) / extraHeight + this.top;
            if (barTop < this.top) {
                barTop = this.top;
            }
            int barBottom = barTop + height;

            //draw scrollbar
            client.getTextureManager().bindTexture(inv_scrollbar_bg_top);
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldr.pos(scrollBarLeft, top + scrollBarSize, 0.0D).tex(0.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, top + scrollBarSize, 0.0D).tex(1.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, top, 0.0D).tex(1.0D, 0.0D).endVertex();
            worldr.pos(scrollBarLeft, top, 0.0D).tex(0.0D, 0.0D).endVertex();
            tess.draw();

            client.getTextureManager().bindTexture(inv_scrollbar_bg_center);
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldr.pos(scrollBarLeft, bottom - scrollBarSize, 0.0D).tex(0.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, bottom - scrollBarSize, 0.0D).tex(1.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, top + scrollBarSize, 0.0D).tex(1.0D, 0.0D).endVertex();
            worldr.pos(scrollBarLeft, top + scrollBarSize, 0.0D).tex(0.0D, 0.0D).endVertex();
            tess.draw();

            client.getTextureManager().bindTexture(inv_scrollbar_bg_bottom);
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldr.pos(scrollBarLeft, bottom, 0.0D).tex(0.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, bottom, 0.0D).tex(1.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, bottom - scrollBarSize, 0.0D).tex(1.0D, 0.0D).endVertex();
            worldr.pos(scrollBarLeft, bottom - scrollBarSize, 0.0D).tex(0.0D, 0.0D).endVertex();
            tess.draw();


            client.getTextureManager().bindTexture(inv_scrollbar_top);
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldr.pos(scrollBarLeft, barTop + scrollBarSize, 0.0D).tex(0.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, barTop + scrollBarSize, 0.0D).tex(1.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, barTop, 0.0D).tex(1.0D, 0.0D).endVertex();
            worldr.pos(scrollBarLeft, barTop, 0.0D).tex(0.0D, 0.0D).endVertex();
            tess.draw();

            client.getTextureManager().bindTexture(inv_scrollbar_center);
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldr.pos(scrollBarLeft, barBottom - scrollBarSize, 0.0D).tex(0.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, barBottom - scrollBarSize, 0.0D).tex(1.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, barTop + scrollBarSize, 0.0D).tex(1.0D, 0.0D).endVertex();
            worldr.pos(scrollBarLeft, barTop + scrollBarSize, 0.0D).tex(0.0D, 0.0D).endVertex();
            tess.draw();

            client.getTextureManager().bindTexture(inv_scrollbar_bottom);
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldr.pos(scrollBarLeft, barBottom, 0.0D).tex(0.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, barBottom, 0.0D).tex(1.0D, 1.0D).endVertex();
            worldr.pos(scrollBarRight, barBottom - scrollBarSize, 0.0D).tex(1.0D, 0.0D).endVertex();
            worldr.pos(scrollBarLeft, barBottom - scrollBarSize, 0.0D).tex(0.0D, 0.0D).endVertex();
            tess.draw();
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

        client.fontRendererObj.drawString(inv.carryweight + "/" + TESItems.getCapatibility(inv.player).getMaxCarryweight(), l + 24, t + 8, 0xFFFFFF);
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
        //GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
//        entity.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        entity.renderYawOffset = rotX;
//        entity.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        entity.rotationYaw = rotX;
        entity.rotationPitch = 0;
        //entity.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
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

    private void drawGradientRect(int left, int top, int right, int bottom, int color1, int color2) {
        float a1 = (float) (color1 >> 24 & 255) / 255.0F;
        float r1 = (float) (color1 >> 16 & 255) / 255.0F;
        float g1 = (float) (color1 >> 8 & 255) / 255.0F;
        float b1 = (float) (color1 & 255) / 255.0F;
        float a2 = (float) (color2 >> 24 & 255) / 255.0F;
        float r2 = (float) (color2 >> 16 & 255) / 255.0F;
        float g2 = (float) (color2 >> 8 & 255) / 255.0F;
        float b2 = (float) (color2 & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer VertexBuffer = tessellator.getBuffer();
        VertexBuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        VertexBuffer.pos(right, top, 0.0D).color(r1, g1, b1, a1).endVertex();
        VertexBuffer.pos(left, top, 0.0D).color(r1, g1, b1, a1).endVertex();
        VertexBuffer.pos(left, bottom, 0.0D).color(r2, g2, b2, a2).endVertex();
        VertexBuffer.pos(right, bottom, 0.0D).color(r2, g2, b2, a2).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
