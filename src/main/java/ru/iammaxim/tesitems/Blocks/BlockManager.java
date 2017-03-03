package ru.iammaxim.tesitems.Blocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * Created by maxim on 3/3/17 at 7:12 PM.
 */
public class BlockManager {

    public static final Block[] miningBlocks = {
            Blocks.ACTIVATOR_RAIL,
            Blocks.COAL_ORE,
            Blocks.COBBLESTONE,
            Blocks.DETECTOR_RAIL,
            Blocks.DIAMOND_BLOCK,
            Blocks.DIAMOND_ORE,
            Blocks.DOUBLE_STONE_SLAB,
            Blocks.GOLDEN_RAIL,
            Blocks.GOLD_BLOCK,
            Blocks.GOLD_ORE,
            Blocks.ICE,
            Blocks.IRON_BLOCK,
            Blocks.IRON_ORE,
            Blocks.LAPIS_BLOCK,
            Blocks.LAPIS_ORE,
            Blocks.LIT_REDSTONE_ORE,
            Blocks.MOSSY_COBBLESTONE,
            Blocks.NETHERRACK,
            Blocks.PACKED_ICE,
            Blocks.RAIL,
            Blocks.REDSTONE_ORE,
            Blocks.SANDSTONE,
            Blocks.RED_SANDSTONE,
            Blocks.STONE,
            Blocks.STONE_SLAB,
            Blocks.STONE_BUTTON,
            Blocks.STONE_PRESSURE_PLATE
    };
    public static final Block[] woodcuttingBlocks = {
            Blocks.WOODEN_SLAB,
            Blocks.DOUBLE_WOODEN_SLAB,
            Blocks.ACACIA_STAIRS,
            Blocks.BIRCH_STAIRS,
            Blocks.DARK_OAK_STAIRS,
            Blocks.JUNGLE_STAIRS,
            Blocks.OAK_STAIRS,
            Blocks.SPRUCE_STAIRS,
            Blocks.ACACIA_DOOR,
            Blocks.BIRCH_DOOR,
            Blocks.DARK_OAK_DOOR,
            Blocks.JUNGLE_DOOR,
            Blocks.OAK_DOOR
    };
    public static final Block[] diggingBlocks = {

    };
    public static final Block[] furnutureBlocks = {

    };

    private static boolean isMiningBlock(Block b) {
        for (Block b1 : miningBlocks)
            if (b == b1) return true;
        return false;
    }

    private static boolean isWoodcuttingBlock(Block b) {
        for (Block b1 : woodcuttingBlocks)
            if (b == b1) return true;
        return false;
    }

    private static boolean isDiggingBlock(Block b) {
        for (Block b1 : diggingBlocks)
            if (b == b1) return true;
        return false;
    }
}
