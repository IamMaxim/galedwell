package ru.iammaxim.tesitems.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.iammaxim.tesitems.Networking.MessageLatestContainer;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import javax.annotation.Nullable;

/**
 * Created by maxim on 3/2/17 at 5:17 PM.
 */
public class BlockChest extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockChest() {
        super(Material.WOOD);
        setUnlocalizedName("block_chest_01");
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(1);
        setResistance(1);
        isBlockContainer = true;
        setRegistryName("block_chest_01");
        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this).setRegistryName(getRegistryName()));
        GameRegistry.registerTileEntity(BlockChestTileEntity.class, getRegistryName().toString());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;

        BlockChestTileEntity te = (BlockChestTileEntity) worldIn.getTileEntity(pos);

        if (te == null) {
            System.out.println("Something goes wrong. No BlockChestTileEntity here");
            return true;
        }

        IPlayerAttributesCapability cap = TESItems.getCapability(playerIn);
        cap.setLatestContainer(te.inv);
        TESItems.networkWrapper.sendTo(new MessageLatestContainer(te.inv), (EntityPlayerMP) playerIn);
        playerIn.openGui(TESItems.instance, TESItems.guiContainer, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new BlockChestTileEntity();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        BlockChestTileEntity te = (BlockChestTileEntity) world.getTileEntity(pos);

        if (te != null)
            te.inv.dropAllItems(world, pos);

        world.removeTileEntity(pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }
}
