package ru.iammaxim.tesitems.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by maxim on 24.08.2016.
 */
public class mBlocks {
    private static final String filepath = "data/blocks.txt";
    public static Block hay_block_1,
            wood_block_1,
            wood_block_2,
            wood_block_3,
            wood_block_4;

    public static void register(Side side) {
        hay_block_1 = registerBlock("hay_block_1", Material.GRASS, MapColor.YELLOW, side);
        wood_block_1 = registerBlock("wood_block_1", Material.WOOD, MapColor.WOOD, side);
        wood_block_2 = registerBlock("wood_block_2", Material.WOOD, MapColor.WOOD, side);
        wood_block_3 = registerBlock("wood_block_3", Material.WOOD, MapColor.WOOD, side);
        wood_block_4 = registerBlock("wood_block_4", Material.WOOD, MapColor.WOOD, side);
    }

    private static Block registerBlock(String name, Material material, MapColor mapColor, Side side) {
        Block b = new Block(material, mapColor);
        b.setRegistryName(name);
        b.setUnlocalizedName(name);
        b.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        ItemBlock ib = new ItemBlock(b);
        ib.setRegistryName(name);
        GameRegistry.register(b);
        GameRegistry.register(ib);
        if (side == Side.CLIENT) {
            ModelLoader.setCustomModelResourceLocation(ib, 0, new ModelResourceLocation("tesitems:" + name));
        }
        return b;
    }
}
