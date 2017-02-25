package ru.iammaxim.tesitems.GUI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import org.lwjgl.input.Keyboard;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.FrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.Inventory.InventoryClient;
import ru.iammaxim.tesitems.Items.ItemValueManager;
import ru.iammaxim.tesitems.Items.ItemWeightManager;
import ru.iammaxim.tesitems.Items.Weapon;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.util.HashMap;

/**
 * Created by maxim on 2/24/17 at 8:57 PM.
 */
public class GuiInventory extends Screen {
    private static final int left_padding = 10, top_padding = 8, bottom_padding = 8, entryTextPaddingTop = 7;
    private InventoryClient inv;
    private TableEntry header;
    private Table table;
    private EntityPlayer player;
    private IPlayerAttributesCapability cap;
    private float playerScale = 1;
    private HashMap<EntityEquipmentSlot, Integer> equippedIndices = new HashMap<>();

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

        //add equipped slots
        ItemStack[] armorInv = player.inventory.armorInventory;
        for (int i = 0; i < armorInv.length; i++) {
            ItemStack is = armorInv[i];
            if (is != null)
                equippedIndices.put(getSlotFromIndex(i), inv.getItemStackIndex(is));
        }

        if (player.getHeldItemMainhand() != null)
            equippedIndices.put(EntityEquipmentSlot.MAINHAND, inv.getItemStackIndex(player.getHeldItemMainhand()));
        if (player.getHeldItemOffhand() != null)
            equippedIndices.put(EntityEquipmentSlot.OFFHAND, inv.getItemStackIndex(player.getHeldItemOffhand()));

        header = (TableEntry) new TableEntry()
                .add(new HorizontalLayout()._setwidth(18))
                .add(new HorizontalLayout().add(new Text("Name")).center(true).setPaddingTop(4).setWidthOverride(ElementBase.FILL)._setwidth(182))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_value)).center(true)._setwidth(20))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_carryweight)).center(true)._setwidth(20))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_damage)).center(true)._setwidth(20))
                .add(new HorizontalLayout().add(new Image(ResManager.icon_durability)).center(true)._setwidth(20));

        root.setElement(contentLayout);
        contentLayout.setElement(new ScrollableLayout().setElement(new VerticalLayout().add(table = new Table(header))));
        table.setWidthOverride(ElementBase.FILL);
        table.setHeightOverride(ElementBase.FILL);
        table.setWidth(280);

/*
        table.add(getEntryFor(ResManager.icon_durability , "name1", 1, 1, 2, 3));
        table.add(getEntryFor(ResManager.icon_damage, "name2", 0, 1, -1, -1));
        for (int i = 0; i < 30; i++) {
            table.add(getEntryFor(ResManager.icon_value, "dummy", i, 0, 0, 0));
        }*/

        updateTable();

        root.setBounds(left_padding, top_padding, left_padding + root.getWidth(), res.getScaledHeight() - top_padding - bottom_padding);
        root.doLayout();
    }

    private void drawPlayer() {

    }

    private void updateTable() {
        table.clear();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack is = inv.get(i);
            int finalI = i;
            TableEntry entry = (TableEntry) getEntryFor(is,
                    ItemValueManager.getValue(is),
                    ItemWeightManager.getWeight(is),
                    is.getItem() instanceof Weapon ? ((Weapon) is.getItem()).getDamageVsEntity() : -1,
                    is.isItemStackDamageable() ? (int) (100 * (1 - (float) is.getItemDamage() / is.getMaxDamage())) : -1)
                    .setOnClick(e -> {
                        System.out.println("Left click " + is);

                        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                            inv.drop(inv.player, finalI, 1);
                        } else {
                            if (is.getItem() instanceof ItemArmor) {
                                ItemArmor armor = (ItemArmor) is.getItem();

                                if (equippedIndices == null)
                                    System.out.println("equippedIndices == null");

                                if (armor == null)
                                    System.out.println("armor == null");

                                if (armor.getEquipmentSlot() == null)
                                    System.out.println("equipmentSlot == null");

                                if (equippedIndices
                                        .
                                                get
                                                        (
                                                armor
                                                        .
                                                                getEquipmentSlot()
                                        )
                                        ==
                                        finalI) {
                                    inv.equip(armor.getEquipmentSlot(), -1);
                                    equippedIndices.put(armor.getEquipmentSlot(), null);
                                } else {
                                    inv.equip(armor.getEquipmentSlot(), finalI);
                                    equippedIndices.put(armor.getEquipmentSlot(), finalI);
                                }
                            } else if (is.getItem() instanceof Weapon || is.getItem() instanceof ItemTool) {
                                if (equippedIndices.get(EntityEquipmentSlot.MAINHAND) == inv.getItemStackIndex(is)) {
                                    inv.equip(EntityEquipmentSlot.MAINHAND, -1);
                                    equippedIndices.put(EntityEquipmentSlot.MAINHAND, null);
                                } else {
                                    inv.equip(EntityEquipmentSlot.MAINHAND, finalI);
                                    equippedIndices.put(EntityEquipmentSlot.MAINHAND, inv.getItemStackIndex(is));
                                }
                            }
                        }
                    })
                    .setOnRightClick(e -> {
                        System.out.println("Right click");

                        if (equippedIndices.get(EntityEquipmentSlot.OFFHAND) == inv.getItemStackIndex(is)) {
                            inv.equip(EntityEquipmentSlot.OFFHAND, -1);
                            equippedIndices.put(EntityEquipmentSlot.OFFHAND, null);
                        } else {
                            inv.equip(EntityEquipmentSlot.OFFHAND, finalI);
                            equippedIndices.put(EntityEquipmentSlot.OFFHAND, inv.getItemStackIndex(is));
                        }
                    });

            if (equippedIndices.values().contains(inv.getItemStackIndex(is)))
                entry.setBackground(new Image(ResManager.inv_entry_bg_selected));
            table.add(entry);
        }
    }

    private TableEntry getEntryFor(ItemStack is, float value, float weight, float damage, int durability) {
        return (TableEntry) new TableEntry()
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

    private EntityEquipmentSlot getSlotFromIndex(int index) {
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
}
