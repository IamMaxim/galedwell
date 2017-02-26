package ru.iammaxim.tesitems.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;

/**
 * Created by maxim on 2/26/17 at 10:15 AM.
 */
public class Pickaxe extends ItemPickaxe {
    public Pickaxe(ToolMaterial material, String name) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.TOOLS);
    }
}
