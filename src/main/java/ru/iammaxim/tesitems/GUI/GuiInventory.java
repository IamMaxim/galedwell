package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.FrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.InventoryLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.InventoryPlayerLayout;
import ru.iammaxim.tesitems.Inventory.InventoryClient;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.io.IOException;

/**
 * Created by maxim on 2/24/17 at 8:57 PM.
 */
public class GuiInventory extends Screen implements IGuiUpdatable {
    private static final int left_padding = 10, top_padding = 8, bottom_padding = 8;
    private InventoryClient inv;
    private InventoryLayout inventoryLayout;
    private EntityPlayer player;
    private IPlayerAttributesCapability cap;
    private float playerScale = 1;

    public GuiInventory() {
        player = TESItems.getClientPlayer();
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        inv = (InventoryClient) cap.getInventory();

        root = new FrameLayout() {
            @Override
            public void draw(int mouseX, int mouseY) {
                //draw player model

                super.draw(mouseX, mouseY);
            }
        };

        root.setElement(contentLayout);
        contentLayout.setElement(inventoryLayout = new InventoryPlayerLayout(player, inv));

        //update inventory table
        updateTable();

        root.setBounds(left_padding, top_padding, left_padding + root.getWidth(), res.getScaledHeight() - top_padding - bottom_padding);
        root.doLayout();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_TAB) {
            ScreenStack.close();
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    private void drawPlayer() {

    }

    @Override
    public void update() {
        inventoryLayout.update();
    }

    @Override
    public void unupdate() {
        inventoryLayout.unupdate();
    }

    @Override
    public boolean updated() {
        return inventoryLayout.updated();
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        root.setBounds(left_padding, top_padding, left_padding + root.getWidth(), res.getScaledHeight() - top_padding - bottom_padding);
        super.onResize(mcIn, w, h);
    }

    public void updateTable() {
        inventoryLayout.updateTable();
    }
}
