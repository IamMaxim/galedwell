package ru.iammaxim.tesitems.Player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 09.06.2016.
 */
public class PlayerAttributesCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
    IPlayerAttributesCapability inst = TESItems.playerAttributesCapability.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing enumFacing) {
        return capability == TESItems.playerAttributesCapability;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing enumFacing) {
        if (capability == TESItems.playerAttributesCapability) {
            return TESItems.playerAttributesCapability.cast(inst);
        } else {
            return null;
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) TESItems.playerAttributesCapability.getStorage().writeNBT(TESItems.playerAttributesCapability, inst, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbtPrimitive) {
        TESItems.playerAttributesCapability.getStorage().readNBT(TESItems.playerAttributesCapability, inst, null, nbtPrimitive);
    }
}
