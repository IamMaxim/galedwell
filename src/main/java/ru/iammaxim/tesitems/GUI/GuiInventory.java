package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/26/16.
 */
public class GuiInventory extends GuiScreen {
    private Inventory inv;
    private GuiInventoryItemList itemList;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public GuiInventory(EntityPlayer player) {
        mc = Minecraft.getMinecraft();
        inv = TESItems.getCapatibility(player).getInventory();
        itemList = new GuiInventoryItemList(inv, mc);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        itemList.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        itemList = new GuiInventoryItemList(inv, mc);
    }
}
