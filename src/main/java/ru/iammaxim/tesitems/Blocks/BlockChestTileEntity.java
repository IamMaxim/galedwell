package ru.iammaxim.tesitems.Blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import ru.iammaxim.tesitems.Inventory.InventoryContainer;
import ru.iammaxim.tesitems.Inventory.InventoryContainerClient;
import ru.iammaxim.tesitems.Inventory.InventoryContainerServer;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 02.03.2017.
 */
public class BlockChestTileEntity extends TileEntity {
    public InventoryContainer inv;

    public BlockChestTileEntity() {
        if (TESItems.getSide() == Side.SERVER)
            inv = new InventoryContainerServer();
        else
            inv = new InventoryContainerClient();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("inv"))
            inv.loadFromNBT(compound.getCompoundTag("inv"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("inv", inv.writeToNBT());
        return tag;
    }
}
