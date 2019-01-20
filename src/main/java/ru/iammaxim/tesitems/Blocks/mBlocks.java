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
            ayleid_block_1,
            block_workbench,
            block_chair_01,

    mill_side,
            beige_clay,
            big_bricks,
            brick_wall,
            brige_stone,
            dark_gray_stone,
            gravel_block,
            gray_clay,
            hard_marble,
            hay,
            light_brige_stone,
            log_wall_1,
            log_wall_2,
            marble,
            red_clay,
            red_wall,
            simple_box,
            stone_wall,
            test_3,
            tine_stone_wall,
            wood_1,
            wood_2;

    public static BlockChest block_chest_01;

    public static void register(Side side) {
        block_chest_01 = new BlockChest();
        block_workbench = new BlockWorkbench();
        block_chair_01 = new BlockChair01();

        hay_block_1 = registerBlock("hay_block_1", Material.GRASS, 0.8f, MapColor.YELLOW, side);
        wood_block_1 = registerBlock("wood_block_1", Material.WOOD, 1, MapColor.WOOD, side);
        wood_block_2 = registerBlock("wood_block_2", Material.WOOD, 1, MapColor.WOOD, side);
        wood_block_3 = registerBlock("wood_block_3", Material.WOOD, 1, MapColor.WOOD, side);
        wood_block_4 = registerBlock("wood_block_4", Material.WOOD, 1, MapColor.WOOD, side);
        ayleid_block_1 = registerBlock("ayleid_block_1", Material.ROCK, 5, MapColor.STONE, side);

        // TODO: change this
        mill_side = registerBlock("mill_side", Material.ROCK, 5, MapColor.STONE, side);
        beige_clay = registerBlock("beige_clay", Material.ROCK, 5, MapColor.STONE, side);
        big_bricks = registerBlock("big_bricks", Material.ROCK, 5, MapColor.STONE, side);
        brick_wall = registerBlock("brick_wall", Material.ROCK, 5, MapColor.STONE, side);
        brige_stone = registerBlock("brige_stone", Material.ROCK, 5, MapColor.STONE, side);
        dark_gray_stone = registerBlock("dark_gray_stone", Material.ROCK, 5, MapColor.STONE, side);
        gravel_block = registerBlock("gravel_block", Material.ROCK, 5, MapColor.STONE, side);
        gray_clay = registerBlock("gray_clay", Material.ROCK, 5, MapColor.STONE, side);
        hard_marble = registerBlock("hard_marble", Material.ROCK, 5, MapColor.STONE, side);
        hay = registerBlock("hay", Material.ROCK, 5, MapColor.STONE, side);
        light_brige_stone = registerBlock("light_brige_stone", Material.ROCK, 5, MapColor.STONE, side);
        log_wall_1 = registerBlock("log_wall_1", Material.ROCK, 5, MapColor.STONE, side);
        log_wall_2 = registerBlock("log_wall_2", Material.ROCK, 5, MapColor.STONE, side);
        marble = registerBlock("marble", Material.ROCK, 5, MapColor.STONE, side);
        red_clay = registerBlock("red_clay", Material.ROCK, 5, MapColor.STONE, side);
        red_wall = registerBlock("red_wall", Material.ROCK, 5, MapColor.STONE, side);
        simple_box = registerBlock("simple_box", Material.ROCK, 5, MapColor.STONE, side);
        stone_wall = registerBlock("stone_wall", Material.ROCK, 5, MapColor.STONE, side);
        test_3 = registerBlock("test_3", Material.ROCK, 5, MapColor.STONE, side);
        tine_stone_wall = registerBlock("tine_stone_wall", Material.ROCK, 5, MapColor.STONE, side);
        wood_1 = registerBlock("wood_1", Material.ROCK, 5, MapColor.STONE, side);
        wood_2 = registerBlock("wood_2", Material.ROCK, 5, MapColor.STONE, side);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        registerModelResLocation(block_chest_01);
        ClientRegistry.bindTileEntitySpecialRenderer(BlockChestTileEntity.class, new BlockChestTileEntityRenderer());

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block_workbench), 0, new ModelResourceLocation("tesitems:block_workbench"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block_chair_01), 0, new ModelResourceLocation("tesitems:block_chair_01"));
//        registerModelResLocation(block_workbench);
//        ClientRegistry.bindTileEntitySpecialRenderer(BlockWorkbenchTileEntity.class, new BlockWorkbenchTileEntityRenderer("block/block_workbench.obj"));
    }

    public static void registerModelResLocation(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
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
