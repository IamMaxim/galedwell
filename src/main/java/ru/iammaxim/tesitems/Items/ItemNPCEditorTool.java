package ru.iammaxim.tesitems.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by Maxim on 20.07.2016.
 */
public class ItemNPCEditorTool extends Item {
    public ItemNPCEditorTool() {
        setUnlocalizedName("NPCEditorTool");
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.TOOLS);
        setRegistryName("NPCEditorTool");
    }
}
