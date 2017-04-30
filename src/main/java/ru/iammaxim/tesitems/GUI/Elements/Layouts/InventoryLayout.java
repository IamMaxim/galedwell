package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.IGuiUpdatable;
import ru.iammaxim.tesitems.GUI.ResManager;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Items.ItemValueManager;
import ru.iammaxim.tesitems.Items.ItemWeightManager;
import ru.iammaxim.tesitems.Items.Weapon;

import java.util.function.Consumer;

/**
 * Created by maxim on 2/27/17 at 4:31 PM.
 */
public class InventoryLayout extends FrameLayout implements IGuiUpdatable {
    protected static final int entryTextPaddingTop = 7;
    protected TableEntry header;
    protected Table table;
    protected Inventory inv;
    protected Consumer<Integer> onLeftClick;
    private boolean updated = true;

    public InventoryLayout(Inventory inventory) {
        this.inv = inventory;

        //setup header
        header = (TableEntry) new TableEntry()
                .add(new HorizontalLayout()._setwidth(18))
                .add(new HorizontalLayout().add(new Text("Name")).center(true).setPaddingTop(4).setWidthOverride(ElementBase.FILL)._setwidth(182))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_value)).center(true)._setwidth(20))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_carryweight)).center(true)._setwidth(20))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_damage)).center(true)._setwidth(20))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_durability)).center(true)._setwidth(20));
        setElement(new ScrollableLayout().setElement(new VerticalLayout().add(table = new Table(header))));

        table.setWidthOverride(ElementBase.FILL);
        table.setHeightOverride(ElementBase.FILL);
        table.setWidth(header.getWidth());
    }

    public InventoryLayout setOnEntryLeftClick(Consumer<Integer> onClick) {
        this.onLeftClick = onClick;
        return this;
    }

    @Override
    public String getName() {
        return "InventoryLayout";
    }

    public void updateTable() {
        table.clear();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack is = inv.get(i);
            TableEntry entry = getEntryFor(i, is,
                    ItemValueManager.getValue(is),
                    ItemWeightManager.getWeight(is),
                    is.getItem() instanceof Weapon ? ((Weapon) is.getItem()).getDamageVsEntity() : -1,
                    is.isItemStackDamageable() ? (int) (100 * (1 - (float) is.getItemDamage() / is.getMaxDamage())) : -1);
            if (onLeftClick != null)
                    entry.setOnEntryLeftClick(onLeftClick);

            if (needBackground(i))
                entry.setBackground(new Image(ResManager.inv_entry_bg_selected));
            table.add(entry);
        }
    }

    public boolean needBackground(int index) {
        return false;
    }

    protected TableEntry getEntryFor(int index, ItemStack is, float value, float weight, float damage, int durability) {
        return (TableEntry) new TableEntry(index)
                .add(new ItemRenderer(is).setVerticalMargin(1)) //drawing is not here
                .add(new FrameLayout().setElement(new Text(is.stackSize == 1 ? is.getDisplayName() : "(" + is.stackSize + ") " + is.getDisplayName())).setPaddingTop(entryTextPaddingTop).setPaddingLeft(2))
                .add(new HorizontalLayout().center(true).add(new Text(value == (int) value ? String.valueOf((int) value) : String.valueOf(value))).setPaddingTop(entryTextPaddingTop))
                .add(new HorizontalLayout().center(true).add(new Text(weight == (int) weight ? String.valueOf((int) weight) : String.valueOf(weight))).setPaddingTop(entryTextPaddingTop))
                .add(damage == -1 ?
                        new HorizontalLayout() :
                        new HorizontalLayout().center(true).add(new Text(damage == (int) damage ? String.valueOf((int) damage) : String.valueOf(damage))).setPaddingTop(entryTextPaddingTop))
                .add(durability == -1 ?
                        new HorizontalLayout() :
                        new HorizontalLayout().center(true).add(new Text(String.valueOf(durability))).setPaddingTop(entryTextPaddingTop));
    }

    protected EntityEquipmentSlot getSlotFromIndex(int index) {
        switch (index) {
            case 0:
                return EntityEquipmentSlot.FEET;
            case 1:
                return EntityEquipmentSlot.LEGS;
            case 2:
                return EntityEquipmentSlot.CHEST;
            case 3:
                return EntityEquipmentSlot.HEAD;
            default:
                return null;
        }
    }

    @Override
    public void update() {
        updated = true;
    }

    @Override
    public void unupdate() {
        updated = false;
    }

    @Override
    public boolean updated() {
        return updated;
    }
}
