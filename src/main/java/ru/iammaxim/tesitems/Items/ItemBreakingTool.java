package ru.iammaxim.tesitems.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by Maxim on 10.06.2016.
 */
public class ItemBreakingTool extends Item {
    public ItemBreakingTool() {
        super();
        setCreativeTab(CreativeTabs.TOOLS);
        setUnlocalizedName("breakingTool");
        setRegistryName("breakingTool");
    }
}
