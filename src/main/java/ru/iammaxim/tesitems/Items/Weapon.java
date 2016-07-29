package ru.iammaxim.tesitems.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

/**
 * Created by Maxim on 10.06.2016.
 */
public class Weapon extends ItemSword {
    private final WeaponType type;

    public Weapon(ToolMaterial material, String name, WeaponType type) {
        super(material);
        this.type = type;
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.COMBAT);
    }

    public WeaponType getWeaponType() {
        return type;
    }
}