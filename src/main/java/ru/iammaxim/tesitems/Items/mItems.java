package ru.iammaxim.tesitems.Items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Maxim on 10.06.2016.
 */
public class mItems {
    public static final float
            daggerDamageModifier = 1.0f,
            swordDamageModifier = 1.5f,
            waraxeDamageModifier = 2.2f,
            greadswordDamageModifier = 2.0f,
            maceDamageModifier = 1.8f,
            axeDamageModifier = 1.7f;

    public static final float
            daedricDamage = 5,
            dragonDamage = 4.5f,
            dwarvenDamage = 4,
            ebonyDamage = 4,
            elvenDamage = 4,
            emeraldDamage = 3.5f,
            falmerDamage = 3,
            glassDamage = 4,
            imperialDamage = 3,
            ironDamage = 2.5f,
            orcishDamage = 4.5f,
            silverDamage = 2,
            stalhrimDamage = 4,
            steelDamage = 3;

    public static ItemBreakingTool itemBreakingTool;
    public static ItemNPCCreatorTool itemNPCCreatorTool;
    public static ItemNPCEditorTool itemNPCEditorTool;
    public static Weapon
            dwarvenSword,
            elvenDagger,
            elvenSword,
            ironSword,
            orcishDagger,
            steelSword;

    public static Item.ToolMaterial materialDwarven = EnumHelper.addToolMaterial("dwarven", 3, 5, 6, 3, 0);

    public static ItemTool dwarvenPickaxe;

    public static void register(Side side) {
        registerItem(itemBreakingTool = new ItemBreakingTool());
        registerItem(itemNPCCreatorTool = new ItemNPCCreatorTool());
        registerItem(itemNPCEditorTool = new ItemNPCEditorTool());
        registerItem(dwarvenSword = new Weapon(createWeaponMaterial("dwarvenSword", 100, swordDamageModifier * dwarvenDamage), "dwarvenSword", WeaponType.BLADES));
        registerItem(elvenDagger = new Weapon(createWeaponMaterial("elvenDagger", 100, daggerDamageModifier * elvenDamage), "elvenDagger", WeaponType.BLADES));
        registerItem(elvenSword = new Weapon(createWeaponMaterial("elvenSword", 100, swordDamageModifier * elvenDamage), "elvenSword", WeaponType.BLADES));
        registerItem(ironSword = new Weapon(createWeaponMaterial("ironSword", 100, swordDamageModifier * ironDamage), "ironSword", WeaponType.BLADES));
        registerItem(orcishDagger = new Weapon(createWeaponMaterial("orcishDagger", 100, daggerDamageModifier * orcishDamage), "orcishDagger", WeaponType.BLADES));
        registerItem(steelSword = new Weapon(createWeaponMaterial("steelSword", 100, swordDamageModifier * steelDamage), "steelSword", WeaponType.BLADES));

        registerItem(dwarvenPickaxe = new Pickaxe(materialDwarven, "dwarvenPickaxe"));


        if (side == Side.CLIENT) {
            ModelLoader.setCustomModelResourceLocation(itemBreakingTool, 0, new ModelResourceLocation("tesitems:breakingTool"));
            ModelLoader.setCustomModelResourceLocation(itemNPCEditorTool, 0, new ModelResourceLocation("tesitems:NPCEditorTool"));
            ModelLoader.setCustomModelResourceLocation(itemNPCCreatorTool, 0, new ModelResourceLocation("tesitems:NPCCreatorTool"));
            ModelLoader.setCustomModelResourceLocation(dwarvenSword, 0, new ModelResourceLocation("tesitems:dwarvenSword"));
            ModelLoader.setCustomModelResourceLocation(elvenDagger, 0, new ModelResourceLocation("tesitems:elvenDagger"));
            ModelLoader.setCustomModelResourceLocation(elvenSword, 0, new ModelResourceLocation("tesitems:elvenSword"));
            ModelLoader.setCustomModelResourceLocation(ironSword, 0, new ModelResourceLocation("tesitems:ironSword"));
            ModelLoader.setCustomModelResourceLocation(orcishDagger, 0, new ModelResourceLocation("tesitems:orcishDagger"));
            ModelLoader.setCustomModelResourceLocation(steelSword, 0, new ModelResourceLocation("tesitems:steelSword"));
        }
    }

    private static void registerItem(Item item) {
        GameRegistry.register(item);
    }

    private static Item.ToolMaterial createWeaponMaterial(String name, int maxUses, float damage) {
        return EnumHelper.addToolMaterial(name, 0, maxUses, 0, damage, 0);
    }
}
