package ru.iammaxim.tesitems.Questing.QuestTargets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Questing.QuestTarget;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestTargetGather extends QuestTarget {
    private ItemStack is;

    public QuestTargetGather(ItemStack is) {
        this.is = is;
    }

    public QuestTargetGather(NBTTagCompound tag) {
        is = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("stack"));
    }

    @Override
    public String getType() {
        return "gather";
    }

    @Override
    public boolean check(EntityPlayer player) {
        Inventory inv = Inventory.getInventory(player);
        int index = inv.getItemStackIndex(is);
        return index != -1;
    }

    @Override
    public NBTTagCompound getNBT() {
        return null;
    }

    @Override
    public void loadFromNBT() {

    }
}
