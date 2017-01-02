package ru.iammaxim.tesitems.World;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import ru.iammaxim.tesitems.TESItems;

import javax.annotation.Nullable;

/**
 * Created by maxim on 02.01.2017.
 */
public class WorldCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
    IWorldCapability inst = TESItems.worldCapability.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == TESItems.worldCapability;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == TESItems.worldCapability) {
            return TESItems.worldCapability.cast(inst);
        } else {
            return null;
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) TESItems.worldCapability.getStorage().writeNBT(TESItems.worldCapability, inst, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        TESItems.worldCapability.getStorage().readNBT(TESItems.worldCapability, inst, null, nbt);
    }
}
