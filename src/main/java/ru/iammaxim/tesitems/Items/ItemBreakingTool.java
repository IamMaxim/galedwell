package ru.iammaxim.tesitems.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemTool;

import java.util.HashSet;

/**
 * Created by Maxim on 10.06.2016.
 */
public class ItemBreakingTool extends ItemTool {
    public ItemBreakingTool() {
        super(ToolMaterial.DIAMOND, new HashSet<>());
        setCreativeTab(CreativeTabs.TOOLS);
        setUnlocalizedName("breakingTool");
        setRegistryName("breakingTool");
    }
}
