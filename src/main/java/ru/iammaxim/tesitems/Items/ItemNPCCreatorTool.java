package ru.iammaxim.tesitems.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.iammaxim.tesitems.NPC.EntityNPC;

/**
 * Created by Maxim on 20.07.2016.
 */
public class ItemNPCCreatorTool extends Item {
    public ItemNPCCreatorTool() {
        setUnlocalizedName("NPCCreatorTool");
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.TOOLS);
        setRegistryName("NPCCreatorTool");
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            EntityNPC npc = new EntityNPC(worldIn);
            npc.setPosition(pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ);
            worldIn.spawnEntityInWorld(npc);
            playerIn.swingArm(hand);
        }
        return EnumActionResult.SUCCESS;
    }
}
