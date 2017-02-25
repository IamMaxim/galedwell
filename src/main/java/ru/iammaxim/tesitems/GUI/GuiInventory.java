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

import java.io.IOException;
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
    private boolean updated = true;

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_TAB) {
            ScreenStack.close();
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }

    public void setUpdated() {
        updated = true;
    }

    private void setUnUpdated() {
        updated = false;
    }

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

        //add equipped slots
        checkEquipped();
        updateTable();

        root.setBounds(left_padding, top_padding, left_padding + root.getWidth(), res.getScaledHeight() - top_padding - bottom_padding);
        root.doLayout();
    }

    private void drawPlayer() {

    }

    public void checkEquipped() {
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
    }

    public void updateTable() {
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
                        if (!updated)
                            return;

                        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                            inv.drop(inv.player, finalI, 1);
                        } else {
                            if (is.getItem() instanceof ItemArmor) {
                                ItemArmor armor = (ItemArmor) is.getItem();

                                if (equippedIndices.get(armor.getEquipmentSlot()) != null && equippedIndices.get(armor.getEquipmentSlot()) == finalI) {
                                    inv.equip(armor.getEquipmentSlot(), -1);
                                    equippedIndices.put(armor.getEquipmentSlot(), null);
                                    setUnUpdated();
                                } else {
                                    inv.equip(armor.getEquipmentSlot(), finalI);
                                    equippedIndices.put(armor.getEquipmentSlot(), finalI);
                                    setUnUpdated();
                                }
                            } else if (is.getItem() instanceof Weapon || is.getItem() instanceof ItemTool) {
                                if (equippedIndices.get(EntityEquipmentSlot.MAINHAND) != null && equippedIndices.get(EntityEquipmentSlot.MAINHAND) == inv.getItemStackIndex(is)) {
                                    inv.equip(EntityEquipmentSlot.MAINHAND, -1);
                                    equippedIndices.put(EntityEquipmentSlot.MAINHAND, null);
                                    setUnUpdated();
                                } else {
                                    if (equippedIndices.get(EntityEquipmentSlot.OFFHAND) != null && equippedIndices.get(EntityEquipmentSlot.OFFHAND) == inv.getItemStackIndex(is)) {
                                        inv.equip(EntityEquipmentSlot.OFFHAND, -1);

                                    }

                                    inv.equip(EntityEquipmentSlot.MAINHAND, finalI);
                                    equippedIndices.put(EntityEquipmentSlot.MAINHAND, inv.getItemStackIndex(is));
                                    setUnUpdated();
                                }
                            }
                        }
                    })
                    .setOnRightClick(e -> {
                        if (!updated)
                            return;

                        if (equippedIndices.get(EntityEquipmentSlot.OFFHAND) != null && equippedIndices.get(EntityEquipmentSlot.OFFHAND) == inv.getItemStackIndex(is)) {
                            inv.equip(EntityEquipmentSlot.OFFHAND, -1);
                            equippedIndices.put(EntityEquipmentSlot.OFFHAND, null);
                            setUnUpdated();
                        } else {
                            inv.equip(EntityEquipmentSlot.OFFHAND, finalI);
                            equippedIndices.put(EntityEquipmentSlot.OFFHAND, inv.getItemStackIndex(is));
                            setUnUpdated();
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
