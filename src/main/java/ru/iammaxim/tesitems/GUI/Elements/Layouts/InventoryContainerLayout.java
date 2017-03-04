package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Networking.MessageUpdateLatestContainer;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 3/4/17 at 12:33 PM.
 */
public class InventoryContainerLayout extends InventoryLayout {
    public InventoryContainerLayout(Inventory inventory, Inventory anotherInventory, boolean isContainer) {
        super(inventory);
        setOnEntryLeftClick(i -> TESItems.networkWrapper.sendToServer(new MessageUpdateLatestContainer(isContainer ? MessageUpdateLatestContainer.CLICK_CONTAINER : MessageUpdateLatestContainer.CLICK_INVENTORY, i)));
    }
}
