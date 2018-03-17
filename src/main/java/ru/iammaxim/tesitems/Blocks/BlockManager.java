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
            Blocks.PLANKS,
            Blocks.BOOKSHELF,
            Blocks.LOG,
            Blocks.LOG2,
            Blocks.CHEST,
            Blocks.PUMPKIN,
            Blocks.LIT_PUMPKIN,
            Blocks.MELON_BLOCK,
            Blocks.LADDER,
            Blocks.WOODEN_BUTTON,
            Blocks.WOODEN_PRESSURE_PLATE
    };

    public static final Block[] diggingBlocks = {
            Blocks.CLAY,
            Blocks.DIRT,
            Blocks.FARMLAND,
            Blocks.GRASS,
            Blocks.GRAVEL,
            Blocks.MYCELIUM,
            Blocks.SAND,
            Blocks.SNOW,
            Blocks.SNOW_LAYER,
            Blocks.SOUL_SAND,
            Blocks.GRASS_PATH
    };

    public static final Block[] furnutureBlocks = {

    };

    public static final Block[] allowed = {
            Blocks.LEAVES,
            Blocks.LEAVES2,
            Blocks.GRASS,
            Blocks.TALLGRASS,
            Blocks.DEADBUSH,
            Blocks.CACTUS,
            Blocks.SAPLING,
            Blocks.REEDS,
            Blocks.WHEAT,
            Blocks.VINE
    };

    public static boolean isMiningBlock(Block b) {
        for (Block b1 : miningBlocks)
            if (b == b1) return true;
        return false;
    }

    public static boolean isWoodcuttingBlock(Block b) {
        for (Block b1 : woodcuttingBlocks)
            if (b == b1) return true;
        return false;
    }

    public static boolean isDiggingBlock(Block b) {
        for (Block b1 : diggingBlocks)
            if (b == b1) return true;
        return false;
    }

    public static boolean requiresNothing(Block b) {
        for (Block b1 : allowed)
            if (b == b1) return true;
        return false;
    }
}
