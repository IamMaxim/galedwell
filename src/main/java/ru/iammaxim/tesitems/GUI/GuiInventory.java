package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.Image;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.GUI.Elements.VerticalDivider;
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
    private Text carryweightText;
    private Text goldText;

    public GuiInventory() {
        player = TESItems.getClientPlayer();
        cap = TESItems.getCapability(player);
        inv = (InventoryClient) cap.getInventory();

        root = (FrameLayout) new FrameLayout() {
            @Override
            public void draw(int mouseX, int mouseY) {
                //draw player model

                super.draw(mouseX, mouseY);
            }
        }.setScreen(root.getScreen());

        root.setElement(contentLayout);
        contentLayout.setElement(new BottomPanelLayout().setBottomPanel(new HorizontalLayout()
                .add(new HorizontalLayout()
                        .add(new Image(ResManager.icon_carryweight))
                        .add(new FrameLayout().setElement(carryweightText = new Text(getCarryweightText())).setPaddingTop(4))
                        .add(new VerticalDivider())
                        .add(new Image(ResManager.icon_value))
                        .add(new FrameLayout().setElement(goldText = new Text(getGoldText())).setPaddingTop(4))
                        .add(new VerticalDivider())
                        .add(new Button("Show attributes").setOnClick(b -> {
                            new GuiAttributes().show();
                        }))
                ).center(true).setPadding(4))
                .setElement(inventoryLayout = new InventoryPlayerLayout(player, inv)));

        //update inventory table
        updateTable();

        root.getScreen().addCallback("inventoryUpdated", o -> {
            updateTable();
            carryweightText.setText(getCarryweightText());
            update();
        });

        root.getScreen().addCallback("goldUpdated", o -> {
            goldText.setText(getGoldText());
        });

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

    public String getCarryweightText() {
        return (inv.carryweight) +
                "/" +
                (cap.getMaxCarryWeight());
    }

    public String getGoldText() {
        return String.valueOf(cap.getGold());
    }
}
