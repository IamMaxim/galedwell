package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryClient;
import ru.iammaxim.tesitems.Inventory.InventoryItemStack;
import ru.iammaxim.tesitems.Items.Weapon;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.*;

/**
 * Created by maxim on 7/26/16 at 5:24 PM.
 */
public class GuiInventoryItemList {
    private static final int entry_height = 15, padding_top = 20, padding_bottom = 20, padding_left = 30, width = 200;
    private final int listHeight;
    private final int screenWidth;
    private final int screenHeight;
    private final int top;
    private final int bottom;
    private final int right;
    private final int left;
    private final int slotHeight;
    private final Minecraft client;
    private int mouseX;
    private int mouseY;
    //    protected boolean captureMouse = true;
    private InventoryClient inv;
    private int scrollUpActionId;
    private int scrollDownActionId;
    private float initialMouseClickY = -2.0F;
    private float scrollFactor;
    private float scrollDistance;
    private long lastClickTime = 0L;
    private boolean hasHeader;
    private int headerHeight;
    private int scrollBarWidth = 6;
    private int lastHash = 0;
    private List<InventoryItemStack> stacks = new ArrayList<>();
    private HashMap<EntityEquipmentSlot, ItemStack> equipped = new HashMap<>();

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
        this.inv = inv;

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        Iterator<ItemStack> it = player.getArmorInventoryList().iterator();
        while (it.hasNext()) {
            ItemStack is = it.next();
            if (is != null && is.getItem() instanceof ItemArmor)
                equipped.put(((ItemArmor) is.getItem()).getEquipmentSlot(), is);
        }
        equipped.put(EntityEquipmentSlot.MAINHAND, player.getHeldItemMainhand());
        equipped.put(EntityEquipmentSlot.OFFHAND, player.getHeldItemOffhand());
    }

    protected void setHeaderInfo(boolean hasHeader, int headerHeight) {
        this.hasHeader = hasHeader;
        this.headerHeight = headerHeight;
        if (!hasHeader) this.headerHeight = 0;
    }

    public void clickSlot(EntityEquipmentSlot slot, int index) {
        if (index >= inv.size()) return;
        ItemStack is = stacks.get(index).stack;
        index = inv.getItemList().indexOf(is);
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            System.out.println("trying to drop");
            inv.drop(inv.player, index, 1);
            //Minecraft.getMinecraft().thePlayer.dropItem(is, false);
        } else {
            equip(slot, index);
        }
        }

    public void equip(EntityEquipmentSlot slot, int index) {
        ItemStack is = stacks.get(index).stack;
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
            if (slot == EntityEquipmentSlot.MAINHAND && !(is.getItem() instanceof Weapon)) return;
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

    private void drawBackground() {
    }

    public boolean isEquipped(ItemStack stack) {
        return equipped.containsValue(stack);
    }

    private void drawSlotBackground(int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
        VertexBuffer vb = tess.getBuffer();
        int min = this.left;
        int max = entryRight;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableTexture2D();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        vb.pos(min, slotTop + slotBuffer + 2, 0).tex(0, 1).color(0x80, 0x80, 0x80, 0xFF).endVertex();
        vb.pos(max, slotTop + slotBuffer + 2, 0).tex(1, 1).color(0x80, 0x80, 0x80, 0xFF).endVertex();
        vb.pos(max, slotTop - 2, 0).tex(1, 0).color(0x80, 0x80, 0x80, 0xFF).endVertex();
        vb.pos(min, slotTop - 2, 0).tex(0, 0).color(0x80, 0x80, 0x80, 0xFF).endVertex();
        vb.pos(min + 1, slotTop + slotBuffer + 1, 0).tex(0, 1).color(0x00, 0x00, 0x00, 0xFF).endVertex();
        vb.pos(max - 1, slotTop + slotBuffer + 1, 0).tex(1, 1).color(0x00, 0x00, 0x00, 0xFF).endVertex();
        vb.pos(max - 1, slotTop - 1, 0).tex(1, 0).color(0x00, 0x00, 0x00, 0xFF).endVertex();
        vb.pos(min + 1, slotTop - 1, 0).tex(0, 0).color(0x00, 0x00, 0x00, 0xFF).endVertex();
        tess.draw();
        GlStateManager.enableTexture2D();
    }

    private void drawSlot(int index, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
        ItemStack stack = stacks.get(index).stack;
        if (isEquipped(stack)) {
            drawSlotBackground(entryRight, slotTop, slotBuffer, tess);
        }
        String s = client.fontRendererObj.trimStringToWidth(String.format("(%d) %s", stack.stackSize, stack.getDisplayName()), width - 14);
        client.fontRendererObj.drawString(s, left + 4, slotTop + 2, 0xffffffff);
    }

    @Deprecated
    private void func_27260_a(int entryRight, int relativeY, Tessellator tess) {
    }

    /**
     * Draw anything special on the screen. GL_SCISSOR is enabled for anything that
     * is rendered outside of the view box. Do not mess with SCISSOR unless you support this.
     */
    private void drawHeader(int entryRight, int relativeY, Tessellator tess) {
        func_27260_a(entryRight, relativeY, tess);
    }

    /**
     * Draw anything special on the screen. GL_SCISSOR is enabled for anything that
     * is rendered outside of the view box. Do not mess with SCISSOR unless you support this.
     */
    private void drawScreen(int mouseX, int mouseY) {

    }

    // FIXME: is this correct/still needed?
    public void registerScrollButtons(List<GuiButton> buttons, int upActionID, int downActionID) {
        this.scrollUpActionId = upActionID;
        this.scrollDownActionId = downActionID;
    }

    private int getContentHeight() {
        return this.getSize() * this.slotHeight + this.headerHeight;
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
        boolean isHovering = mouseX >= this.left && mouseX <= this.left + this.width && mouseY >= this.top && mouseY <= this.bottom;
        if (!isHovering)
            return;
        int scroll = Mouse.getEventDWheel();
        if (scroll != 0) {
            this.scrollDistance += (scroll / -240.0F * this.slotHeight);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int hash;
        if ((hash = inv.hashCode()) != lastHash) {
            lastHash = hash;
            stacks.clear();
            inv.getItemList().forEach(stack -> stacks.add(new InventoryItemStack(stack)));
            Collections.sort(stacks);
        }

        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.drawBackground();

        boolean isHovering = mouseX >= this.left && mouseX <= this.left + this.width && mouseY >= this.top && mouseY <= this.bottom;
        int listLength = this.getSize();
        int scrollBarRight = this.left + this.width;
        int scrollBarLeft = scrollBarRight - scrollBarWidth;
        int entryLeft = this.left;
        int entryRight = scrollBarLeft - 1;
        int viewHeight = this.bottom - this.top;
        int border = 4;

        if (Mouse.isButtonDown(0)) {
            if (this.initialMouseClickY == -1.0F) {
                if (isHovering) {
                    int mouseListY = mouseY - this.top - this.headerHeight + (int) this.scrollDistance - border;
                    int slotIndex = mouseListY / this.slotHeight;

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
        } else if (Mouse.isButtonDown(1)) {
            if (this.initialMouseClickY == -1.0F) {
                int mouseListY = mouseY - this.top - this.headerHeight + (int) this.scrollDistance - border;
                int slotIndex = mouseListY / this.slotHeight;
                elementRightClicked(slotIndex);
                this.initialMouseClickY = mouseY;
            }
        } else {
            this.initialMouseClickY = -1.0F;
        }

        this.applyScrollLimits();

        Tessellator tess = Tessellator.getInstance();
        VertexBuffer worldr = tess.getBuffer();

        ScaledResolution res = new ScaledResolution(client);
        double scaleW = client.displayWidth / res.getScaledWidth_double();
        double scaleH = client.displayHeight / res.getScaledHeight_double();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) (left * scaleW), (int) (client.displayHeight - (bottom * scaleH)), (int) (width * scaleW), (int) (viewHeight * scaleH));
        this.drawGradientRect(this.left, this.top, this.right, this.bottom, 0xC0101010, 0xD0101010);
        int baseY = this.top + border - (int) this.scrollDistance;
        if (this.hasHeader) {
            this.drawHeader(entryRight, baseY, tess);
        }
        for (int slotIdx = 0; slotIdx < listLength; ++slotIdx) {
            int slotTop = baseY + slotIdx * this.slotHeight + this.headerHeight;
            int slotBuffer = this.slotHeight - border;
            if (slotTop <= this.bottom && slotTop + slotBuffer >= this.top) {
                /*
                if (this.highlightSelected && this.isSelected(slotIdx)) {
                    int min = this.left;
                    int max = entryRight;
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.disableTexture2D();
                    worldr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    worldr.pos(min, slotTop + slotBuffer + 2, 0).tex(0, 1).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                    worldr.pos(max, slotTop + slotBuffer + 2, 0).tex(1, 1).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                    worldr.pos(max, slotTop - 2, 0).tex(1, 0).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                    worldr.pos(min, slotTop - 2, 0).tex(0, 0).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                    worldr.pos(min + 1, slotTop + slotBuffer + 1, 0).tex(0, 1).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                    worldr.pos(max - 1, slotTop + slotBuffer + 1, 0).tex(1, 1).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                    worldr.pos(max - 1, slotTop - 1, 0).tex(1, 0).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                    worldr.pos(min + 1, slotTop - 1, 0).tex(0, 0).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                    tess.draw();
                    GlStateManager.enableTexture2D();
                }
                */
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
            GlStateManager.disableTexture2D();
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldr.pos(scrollBarLeft, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
            worldr.pos(scrollBarRight, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
            worldr.pos(scrollBarRight, this.top, 0.0D).tex(1.0D, 0.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
            worldr.pos(scrollBarLeft, this.top, 0.0D).tex(0.0D, 0.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
            tess.draw();
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldr.pos(scrollBarLeft, barTop + height, 0.0D).tex(0.0D, 1.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
            worldr.pos(scrollBarRight, barTop + height, 0.0D).tex(1.0D, 1.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
            worldr.pos(scrollBarRight, barTop, 0.0D).tex(1.0D, 0.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
            worldr.pos(scrollBarLeft, barTop, 0.0D).tex(0.0D, 0.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
            tess.draw();
            worldr.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldr.pos(scrollBarLeft, barTop + height - 1, 0.0D).tex(0.0D, 1.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
            worldr.pos(scrollBarRight - 1, barTop + height - 1, 0.0D).tex(1.0D, 1.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
            worldr.pos(scrollBarRight - 1, barTop, 0.0D).tex(1.0D, 0.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
            worldr.pos(scrollBarLeft, barTop, 0.0D).tex(0.0D, 0.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
            tess.draw();
        }
        this.drawScreen(mouseX, mouseY);
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
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
