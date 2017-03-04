package ru.iammaxim.tesitems.Items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Maxim on 12.06.2016.
 */
public class mArmor {

    public static ItemArmor.ArmorMaterial clothes_01_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_02_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_03_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_04_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_05_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_06_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_07_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_08_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_09_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_10_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_11_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_12_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_13_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_14_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial clothes_15_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:Cloth", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial robe_01_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:robe_01", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial robe_02_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:robe_02", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial robe_03_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:robe_03", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial robe_04_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:robe_04", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial robe_05_ArmorMaterial = EnumHelper.addArmorMaterial("Cloth", "tesitems:robe_05", 1000, new int[]{0, 0, 0, 0}, 100, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial furArmorMaterial = EnumHelper.addArmorMaterial("Fur", "tesitems:furArmor", 50, new int[]{2, 5, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial daedricArmorMaterial = EnumHelper.addArmorMaterial("Daedric", "tesitems:daedricArmor", 37, new int[]{4, 10, 4, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial ebonyArmorMaterial = EnumHelper.addArmorMaterial("Ebony", "tesitems:ebonyArmor", 37, new int[]{4, 9, 3, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial dragonArmorMaterial = EnumHelper.addArmorMaterial("Dragon", "tesitems:dragonArmor", 37, new int[]{4, 8, 3, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial dwarvenArmorMaterial = EnumHelper.addArmorMaterial("Dwarven", "tesitems:dwarvenArmor", 37, new int[]{3, 8, 3, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial orcishArmorMaterial = EnumHelper.addArmorMaterial("Orcish", "tesitems:orcishArmor", 37, new int[]{2, 7, 3, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial glassArmorMaterial = EnumHelper.addArmorMaterial("Glass", "tesitems:glassArmor", 37, new int[]{2, 7, 3, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial steelArmorMaterial = EnumHelper.addArmorMaterial("Steel", "tesitems:steelArmor", 37, new int[]{2, 6, 3, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial elvenArmorMaterial = EnumHelper.addArmorMaterial("Elven", "tesitems:elvenArmor", 37, new int[]{2, 6, 3, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial ironArmorMaterial = EnumHelper.addArmorMaterial("Iron", "tesitems:ironArmor", 37, new int[]{2, 5, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial ironArmorMaterial01 = EnumHelper.addArmorMaterial("Iron", "tesitems:ironArmor01", 37, new int[]{2, 5, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial ironArmorMaterial02 = EnumHelper.addArmorMaterial("Iron", "tesitems:ironArmor02", 37, new int[]{2, 5, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial ironArmorMaterial03 = EnumHelper.addArmorMaterial("Iron", "tesitems:ironArmor03", 37, new int[]{2, 5, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial ironArmorMaterial04 = EnumHelper.addArmorMaterial("Iron", "tesitems:ironArmor04", 37, new int[]{2, 5, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial mithrilArmorMaterial = EnumHelper.addArmorMaterial("Mithril", "tesitems:mithrilArmor", 37, new int[]{2, 5, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial chainArmorMaterial = EnumHelper.addArmorMaterial("Chain", "tesitems:chainArmor", 37, new int[]{2, 4, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
    public static ItemArmor.ArmorMaterial hideArmorMaterial = EnumHelper.addArmorMaterial("Hide", "tesitems:hideArmor", 37, new int[]{1, 1, 1, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial leatherArmorMaterial = EnumHelper.addArmorMaterial("Leather", "tesitems:leatherArmor", 37, new int[]{2, 4, 2, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
    public static ItemArmor.ArmorMaterial rawhideArmorMaterial = EnumHelper.addArmorMaterial("Rawhide", "tesitems:rawhideArmor", 37, new int[]{3, 4, 2, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);

    public static ItemArmor
            furHelmet,
            furChestplate,
            furLeggings,
            furBoots,
            chainHelmet,
            chainChestplate,
            chainBoots,
            iron_01_Helmet,
            iron_01_Chestplate,
            iron_01_Leggings,
            iron_01_Boots,
            iron_02_Helmet,
            iron_02_Chestplate,
            iron_02_Leggings,
            iron_02_Boots,
            iron_03_Helmet,
            iron_03_Chestplate,
            iron_03_Leggings,
            iron_03_Boots,
            iron_04_Helmet,
            iron_04_Chestplate,
            iron_04_Leggings,
            iron_04_Boots,
            iron_Helmet,
            iron_Chestplate,
            iron_Leggings,
            iron_Boots,
            leatherHelmet,
            leatherChestplate,
            leatherLeggings,
            leatherBoots,
            mithrilHelmet,
            mithrilChestplate,
            mithrilBoots,
            rawhideHelmet,
            rawhideChestplate,
            rawhideLeggings,
            rawhideBoots,
            robe_01_Helmet,
            robe_01_Chestplate,
            robe_01_Leggings,
            robe_01_Boots,
            robe_02_Helmet,
            robe_02_Chestplate,
            robe_02_Leggings,
            robe_02_Boots,
            robe_03_Helmet,
            robe_03_Chestplate,
            robe_03_Leggings,
            robe_03_Boots,
            robe_04_Helmet,
            robe_04_Chestplate,
            robe_04_Leggings,
            robe_04_Boots,
            robe_05_Helmet,
            robe_05_Chestplate,
            robe_05_Leggings,
            robe_05_Boots,
            clothes_01_Helmet,
            clothes_01_Chestplate,
            clothes_01_Leggings,
            clothes_01_Boots,
            clothes_02_Helmet,
            clothes_02_Chestplate,
            clothes_02_Leggings,
            clothes_02_Boots,
            clothes_03_Helmet,
            clothes_03_Chestplate,
            clothes_03_Leggings,
            clothes_03_Boots,
            clothes_04_Helmet,
            clothes_04_Chestplate,
            clothes_04_Leggings,
            clothes_04_Boots,
            clothes_05_Helmet,
            clothes_05_Chestplate,
            clothes_05_Leggings,
            clothes_05_Boots,
            clothes_06_Helmet,
            clothes_06_Chestplate,
            clothes_06_Leggings,
            clothes_06_Boots,
            clothes_07_Helmet,
            clothes_07_Chestplate,
            clothes_07_Leggings,
            clothes_07_Boots,
            clothes_08_Helmet,
            clothes_08_Chestplate,
            clothes_08_Leggings,
            clothes_08_Boots,
            clothes_09_Helmet,
            clothes_09_Chestplate,
            clothes_09_Leggings,
            clothes_09_Boots,
            clothes_10_Helmet,
            clothes_10_Chestplate,
            clothes_10_Leggings,
            clothes_10_Boots,
            clothes_11_Helmet,
            clothes_11_Chestplate,
            clothes_11_Leggings,
            clothes_11_Boots,
            clothes_12_Helmet,
            clothes_12_Chestplate,
            clothes_12_Leggings,
            clothes_12_Boots,
            clothes_13_Helmet,
            clothes_13_Chestplate,
            clothes_13_Leggings,
            clothes_13_Boots,
            clothes_14_Helmet,
            clothes_14_Chestplate,
            clothes_14_Leggings,
            clothes_14_Boots,
            clothes_15_Helmet,
            clothes_15_Chestplate,
            clothes_15_Leggings,
            clothes_15_Boots;


    public static void register(Side side) {
        furHelmet = registerItem(furArmorMaterial, "furHelmet", EntityEquipmentSlot.HEAD, side);
        furChestplate = registerItem(furArmorMaterial, "furChestplate", EntityEquipmentSlot.CHEST, side);
        furLeggings = registerItem(furArmorMaterial, "furLeggings", EntityEquipmentSlot.LEGS, side);
        furBoots = registerItem(furArmorMaterial, "furBoots", EntityEquipmentSlot.FEET, side);
        chainHelmet = registerItem(chainArmorMaterial, "chainHelmet", EntityEquipmentSlot.HEAD, side);
        chainChestplate = registerItem(chainArmorMaterial, "chainChestplate", EntityEquipmentSlot.CHEST, side);
        chainBoots = registerItem(chainArmorMaterial, "chainBoots", EntityEquipmentSlot.FEET, side);
        iron_01_Helmet = registerItem(ironArmorMaterial01, "iron_01_Helmet", EntityEquipmentSlot.HEAD, side);
        iron_01_Chestplate = registerItem(ironArmorMaterial01, "iron_01_Chestplate", EntityEquipmentSlot.CHEST, side);
        iron_01_Leggings = registerItem(ironArmorMaterial01, "iron_01_Leggings", EntityEquipmentSlot.LEGS, side);
        iron_01_Boots = registerItem(ironArmorMaterial01, "iron_01_Boots", EntityEquipmentSlot.FEET, side);
        iron_02_Helmet = registerItem(ironArmorMaterial02, "iron_02_Helmet", EntityEquipmentSlot.HEAD, side);
        iron_02_Chestplate = registerItem(ironArmorMaterial02, "iron_02_Chestplate", EntityEquipmentSlot.CHEST, side);
        iron_02_Leggings = registerItem(ironArmorMaterial02, "iron_02_Leggings", EntityEquipmentSlot.LEGS, side);
        iron_02_Boots = registerItem(ironArmorMaterial02, "iron_02_Boots", EntityEquipmentSlot.FEET, side);
        iron_03_Helmet = registerItem(ironArmorMaterial03, "iron_03_Helmet", EntityEquipmentSlot.HEAD, side);
        iron_03_Chestplate = registerItem(ironArmorMaterial03, "iron_03_Chestplate", EntityEquipmentSlot.CHEST, side);
        iron_03_Leggings = registerItem(ironArmorMaterial03, "iron_03_Leggings", EntityEquipmentSlot.LEGS, side);
        iron_03_Boots = registerItem(ironArmorMaterial03, "iron_03_Boots", EntityEquipmentSlot.FEET, side);
        iron_04_Helmet = registerItem(ironArmorMaterial04, "iron_04_Helmet", EntityEquipmentSlot.HEAD, side);
        iron_04_Chestplate = registerItem(ironArmorMaterial04, "iron_04_Chestplate", EntityEquipmentSlot.CHEST, side);
        iron_04_Leggings = registerItem(ironArmorMaterial04, "iron_04_Leggings", EntityEquipmentSlot.LEGS, side);
        iron_04_Boots = registerItem(ironArmorMaterial04, "iron_04_Boots", EntityEquipmentSlot.FEET, side);
        iron_Helmet = registerItem(ironArmorMaterial, "iron_Helmet", EntityEquipmentSlot.HEAD, side);
        iron_Chestplate = registerItem(ironArmorMaterial, "iron_Chestplate", EntityEquipmentSlot.CHEST, side);
        iron_Leggings = registerItem(ironArmorMaterial, "iron_Leggings", EntityEquipmentSlot.LEGS, side);
        iron_Boots = registerItem(ironArmorMaterial, "iron_Boots", EntityEquipmentSlot.FEET, side);
        leatherHelmet = registerItem(leatherArmorMaterial, "leatherHelmet", EntityEquipmentSlot.HEAD, side);
        leatherChestplate = registerItem(leatherArmorMaterial, "leatherChestplate", EntityEquipmentSlot.CHEST, side);
        leatherLeggings = registerItem(leatherArmorMaterial, "leatherLeggings", EntityEquipmentSlot.LEGS, side);
        leatherBoots = registerItem(leatherArmorMaterial, "leatherBoots", EntityEquipmentSlot.FEET, side);
        mithrilHelmet = registerItem(mithrilArmorMaterial, "mithrilHelmet", EntityEquipmentSlot.HEAD, side);
        mithrilChestplate = registerItem(mithrilArmorMaterial, "mithrilChestplate", EntityEquipmentSlot.CHEST, side);
        mithrilBoots = registerItem(mithrilArmorMaterial, "mithrilBoots", EntityEquipmentSlot.FEET, side);
        rawhideHelmet = registerItem(rawhideArmorMaterial, "rawhideHelmet", EntityEquipmentSlot.HEAD, side);
        rawhideChestplate = registerItem(rawhideArmorMaterial, "rawhideChestplate", EntityEquipmentSlot.CHEST, side);
        rawhideLeggings = registerItem(rawhideArmorMaterial, "rawhideLeggings", EntityEquipmentSlot.LEGS, side);
        rawhideBoots = registerItem(rawhideArmorMaterial, "rawhideBoots", EntityEquipmentSlot.FEET, side);
        robe_01_Helmet = registerItem(robe_01_ArmorMaterial, "robe_01_Helmet", EntityEquipmentSlot.HEAD, side);
        robe_01_Chestplate = registerItem(robe_01_ArmorMaterial, "robe_01_Chestplate", EntityEquipmentSlot.FEET, side);
        robe_01_Leggings = registerItem(robe_01_ArmorMaterial, "robe_01_Leggings", EntityEquipmentSlot.LEGS, side);
        robe_01_Boots = registerItem(robe_01_ArmorMaterial, "robe_01_Boots", EntityEquipmentSlot.FEET, side);
        robe_02_Helmet = registerItem(robe_02_ArmorMaterial, "robe_02_Helmet", EntityEquipmentSlot.HEAD, side);
        robe_02_Chestplate = registerItem(robe_02_ArmorMaterial, "robe_02_Chestplate", EntityEquipmentSlot.CHEST, side);
        robe_02_Leggings = registerItem(robe_02_ArmorMaterial, "robe_02_Leggings", EntityEquipmentSlot.LEGS, side);
        robe_02_Boots = registerItem(robe_02_ArmorMaterial, "robe_02_Boots", EntityEquipmentSlot.FEET, side);
        robe_03_Helmet = registerItem(robe_03_ArmorMaterial, "robe_03_Helmet", EntityEquipmentSlot.HEAD, side);
        robe_03_Chestplate = registerItem(robe_03_ArmorMaterial, "robe_03_Chestplate", EntityEquipmentSlot.CHEST, side);
        robe_03_Leggings = registerItem(robe_03_ArmorMaterial, "robe_03_Leggings", EntityEquipmentSlot.LEGS, side);
        robe_03_Boots = registerItem(robe_03_ArmorMaterial, "robe_03_Boots", EntityEquipmentSlot.FEET, side);
        robe_04_Helmet = registerItem(robe_04_ArmorMaterial, "robe_04_Helmet", EntityEquipmentSlot.HEAD, side);
        robe_04_Chestplate = registerItem(robe_04_ArmorMaterial, "robe_04_Chestplate", EntityEquipmentSlot.CHEST, side);
        robe_04_Leggings = registerItem(robe_04_ArmorMaterial, "robe_04_Leggings", EntityEquipmentSlot.LEGS, side);
        robe_04_Boots = registerItem(robe_04_ArmorMaterial, "robe_04_Boots", EntityEquipmentSlot.FEET, side);
        robe_05_Helmet = registerItem(robe_05_ArmorMaterial, "robe_05_Helmet", EntityEquipmentSlot.HEAD, side);
        robe_05_Chestplate = registerItem(robe_05_ArmorMaterial, "robe_05_Chestplate", EntityEquipmentSlot.CHEST, side);
        robe_05_Leggings = registerItem(robe_05_ArmorMaterial, "robe_05_Leggings", EntityEquipmentSlot.LEGS, side);
        robe_05_Boots = registerItem(robe_05_ArmorMaterial, "robe_05_Boots", EntityEquipmentSlot.FEET, side);
        clothes_01_Helmet = registerItem(clothes_01_ArmorMaterial, "clothes_01_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_01_Chestplate = registerItem(clothes_01_ArmorMaterial, "clothes_01_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_01_Leggings = registerItem(clothes_01_ArmorMaterial, "clothes_01_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_01_Boots = registerItem(clothes_01_ArmorMaterial, "clothes_01_Boots", EntityEquipmentSlot.FEET, side);
        clothes_02_Helmet = registerItem(clothes_02_ArmorMaterial, "clothes_02_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_02_Chestplate = registerItem(clothes_02_ArmorMaterial, "clothes_02_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_02_Leggings = registerItem(clothes_02_ArmorMaterial, "clothes_02_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_02_Boots = registerItem(clothes_02_ArmorMaterial, "clothes_02_Boots", EntityEquipmentSlot.FEET, side);
        clothes_03_Helmet = registerItem(clothes_03_ArmorMaterial, "clothes_03_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_03_Chestplate = registerItem(clothes_03_ArmorMaterial, "clothes_03_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_03_Leggings = registerItem(clothes_03_ArmorMaterial, "clothes_03_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_03_Boots = registerItem(clothes_03_ArmorMaterial, "clothes_03_Boots", EntityEquipmentSlot.FEET, side);
        clothes_04_Helmet = registerItem(clothes_04_ArmorMaterial, "clothes_04_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_04_Chestplate = registerItem(clothes_04_ArmorMaterial, "clothes_04_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_04_Leggings = registerItem(clothes_04_ArmorMaterial, "clothes_04_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_04_Boots = registerItem(clothes_04_ArmorMaterial, "clothes_04_Boots", EntityEquipmentSlot.FEET, side);
        clothes_05_Helmet = registerItem(clothes_05_ArmorMaterial, "clothes_05_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_05_Chestplate = registerItem(clothes_05_ArmorMaterial, "clothes_05_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_05_Leggings = registerItem(clothes_05_ArmorMaterial, "clothes_05_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_05_Boots = registerItem(clothes_05_ArmorMaterial, "clothes_05_Boots", EntityEquipmentSlot.FEET, side);
        clothes_06_Helmet = registerItem(clothes_06_ArmorMaterial, "clothes_06_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_06_Chestplate = registerItem(clothes_06_ArmorMaterial, "clothes_06_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_06_Leggings = registerItem(clothes_06_ArmorMaterial, "clothes_06_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_06_Boots = registerItem(clothes_06_ArmorMaterial, "clothes_06_Boots", EntityEquipmentSlot.FEET, side);
        clothes_07_Helmet = registerItem(clothes_07_ArmorMaterial, "clothes_07_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_07_Chestplate = registerItem(clothes_07_ArmorMaterial, "clothes_07_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_07_Leggings = registerItem(clothes_07_ArmorMaterial, "clothes_07_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_07_Boots = registerItem(clothes_07_ArmorMaterial, "clothes_07_Boots", EntityEquipmentSlot.FEET, side);
        clothes_08_Helmet = registerItem(clothes_08_ArmorMaterial, "clothes_08_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_08_Chestplate = registerItem(clothes_08_ArmorMaterial, "clothes_08_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_08_Leggings = registerItem(clothes_08_ArmorMaterial, "clothes_08_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_08_Boots = registerItem(clothes_08_ArmorMaterial, "clothes_08_Boots", EntityEquipmentSlot.FEET, side);
        clothes_09_Helmet = registerItem(clothes_09_ArmorMaterial, "clothes_09_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_09_Chestplate = registerItem(clothes_09_ArmorMaterial, "clothes_09_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_09_Leggings = registerItem(clothes_09_ArmorMaterial, "clothes_09_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_09_Boots = registerItem(clothes_09_ArmorMaterial, "clothes_09_Boots", EntityEquipmentSlot.FEET, side);
        clothes_10_Helmet = registerItem(clothes_10_ArmorMaterial, "clothes_10_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_10_Chestplate = registerItem(clothes_10_ArmorMaterial, "clothes_10_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_10_Leggings = registerItem(clothes_10_ArmorMaterial, "clothes_10_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_10_Boots = registerItem(clothes_10_ArmorMaterial, "clothes_10_Boots", EntityEquipmentSlot.FEET, side);
        clothes_11_Helmet = registerItem(clothes_11_ArmorMaterial, "clothes_11_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_11_Chestplate = registerItem(clothes_11_ArmorMaterial, "clothes_11_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_11_Leggings = registerItem(clothes_11_ArmorMaterial, "clothes_11_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_11_Boots = registerItem(clothes_11_ArmorMaterial, "clothes_11_Boots", EntityEquipmentSlot.FEET, side);
        clothes_12_Helmet = registerItem(clothes_12_ArmorMaterial, "clothes_12_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_12_Chestplate = registerItem(clothes_12_ArmorMaterial, "clothes_12_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_12_Leggings = registerItem(clothes_12_ArmorMaterial, "clothes_12_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_12_Boots = registerItem(clothes_12_ArmorMaterial, "clothes_12_Boots", EntityEquipmentSlot.FEET, side);
        clothes_13_Helmet = registerItem(clothes_13_ArmorMaterial, "clothes_13_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_13_Chestplate = registerItem(clothes_13_ArmorMaterial, "clothes_13_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_13_Leggings = registerItem(clothes_13_ArmorMaterial, "clothes_13_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_13_Boots = registerItem(clothes_13_ArmorMaterial, "clothes_13_Boots", EntityEquipmentSlot.FEET, side);
        clothes_14_Helmet = registerItem(clothes_14_ArmorMaterial, "clothes_14_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_14_Chestplate = registerItem(clothes_14_ArmorMaterial, "clothes_14_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_14_Leggings = registerItem(clothes_14_ArmorMaterial, "clothes_14_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_14_Boots = registerItem(clothes_14_ArmorMaterial, "clothes_14_Boots", EntityEquipmentSlot.FEET, side);
        clothes_15_Helmet = registerItem(clothes_15_ArmorMaterial, "clothes_15_Helmet", EntityEquipmentSlot.HEAD, side);
        clothes_15_Chestplate = registerItem(clothes_15_ArmorMaterial, "clothes_15_Chestplate", EntityEquipmentSlot.CHEST, side);
        clothes_15_Leggings = registerItem(clothes_15_ArmorMaterial, "clothes_15_Leggings", EntityEquipmentSlot.LEGS, side);
        clothes_15_Boots = registerItem(clothes_15_ArmorMaterial, "clothes_15_Boots", EntityEquipmentSlot.FEET, side);
    }

    private static ItemArmor registerItem(ItemArmor.ArmorMaterial material, String name, EntityEquipmentSlot slot, Side side) {
        ItemArmor armor = new ItemArmor(material, 0, slot);
        armor.setRegistryName(name);
        armor.setUnlocalizedName(name);
        GameRegistry.register(armor);
        armor.setCreativeTab(CreativeTabs.COMBAT);
        if (side == Side.CLIENT)
            ModelLoader.setCustomModelResourceLocation(armor, 0, new ModelResourceLocation(armor.getRegistryName().toString()));
        return armor;
    }
}
