package ru.iammaxim.tesitems.GUI;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.InventoryContainerLayout;
import ru.iammaxim.tesitems.GUI.Elements.VerticalDivider;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 2/27/17 at 4:31 PM.
 */
public class GuiContainer extends Screen {
    private InventoryContainerLayout inventory, container;

    public GuiContainer() {
        EntityPlayer player = TESItems.getClientPlayer();
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        contentLayout.setElement(
                new HorizontalLayout()
                        .add((inventory = new InventoryContainerLayout(cap.getInventory(), cap.getLatestContainer(), false)).setHeightOverride(ElementBase.FILL))
                        .add(new VerticalDivider())
                        .add((container = new InventoryContainerLayout(cap.getLatestContainer(), cap.getInventory(), true)).setHeightOverride(ElementBase.FILL))
        .setHeightOverride(ElementBase.FILL));

        updateTable();
        root.doLayout();
    }

    public void update() {
        inventory.update();
        container.update();
    }

    public void updateTable() {
        inventory.updateTable();
        container.updateTable();
    }
}
