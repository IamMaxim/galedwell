package ru.iammaxim.tesitems.Blocks;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryContainer;

/**
 * Created by maxim on 02.03.2017.
 */
public class BlockChestTileEntity extends TileEntity {
    private InventoryContainer inv = new InventoryContainer();

    public BlockChestTileEntity() {
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inv.loadFromNBT(compound.getCompoundTag("inv"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("inv", inv.writeToNBT());
        return tag;
    }
}
