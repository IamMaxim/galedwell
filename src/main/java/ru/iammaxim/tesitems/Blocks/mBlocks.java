package ru.iammaxim.tesitems.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by maxim on 24.08.2016.
 */
public class mBlocks {
    private static final String filepath = "data/blocks.txt";
    public static Block hay_block_1,
            wood_block_1,
            wood_block_2,
            wood_block_3,
            wood_block_4,
            ayleid_block_1;

    public static BlockChest block_chest_01;

    public static void register(Side side) {
        block_chest_01 = new BlockChest();
        hay_block_1 = registerBlock("hay_block_1", Material.GRASS, 0.8f, MapColor.YELLOW, side);
        wood_block_1 = registerBlock("wood_block_1", Material.WOOD, 1, MapColor.WOOD, side);
        wood_block_2 = registerBlock("wood_block_2", Material.WOOD, 1, MapColor.WOOD, side);
        wood_block_3 = registerBlock("wood_block_3", Material.WOOD, 1, MapColor.WOOD, side);
        wood_block_4 = registerBlock("wood_block_4", Material.WOOD, 1, MapColor.WOOD, side);
        ayleid_block_1 = registerBlock("ayleid_block_1", Material.ROCK, 5, MapColor.STONE, side);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block_chest_01), 0, new ModelResourceLocation(block_chest_01.getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(BlockChestTileEntity.class, new BlockChestTileEntityRenderer());
        System.out.println("chest's registry name: " + block_chest_01.getRegistryName());

        //        TESItems.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block_chest_01), 0, new ModelResourceLocation(block_chest_01.getRegistryName(), "inventory"));
    }

    private static Block registerBlock(String name, Material material, float hardness, MapColor mapColor, Side side) {
        Block b = new Block(material, mapColor);
        b.setRegistryName(name);
        b.setUnlocalizedName(name);
        b.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        b.setHardness(hardness);
        GameRegistry.register(b);
        ItemBlock ib = new ItemBlock(b);
        ib.setRegistryName(b.getRegistryName());
        GameRegistry.register(ib);
        if (side == Side.CLIENT)
            ModelLoader.setCustomModelResourceLocation(ib, 0, new ModelResourceLocation("tesitems:" + name));
        return b;
    }
}
