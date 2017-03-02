package ru.iammaxim.tesitems.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.iammaxim.tesitems.TESItems;

import javax.annotation.Nullable;

/**
 * Created by maxim on 3/2/17 at 5:17 PM.
 */
public class BlockChest extends Block implements ITileEntityProvider {
    public BlockChest() {
        super(Material.WOOD);
        setUnlocalizedName("block_chest_01");
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(5);
        setResistance(10);
        isBlockContainer = true;

        setRegistryName("block_chest_01");

        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(BlockChestTileEntity.class, TESItems.MODID + "block_chest_01");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new BlockChestTileEntity();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }
}
