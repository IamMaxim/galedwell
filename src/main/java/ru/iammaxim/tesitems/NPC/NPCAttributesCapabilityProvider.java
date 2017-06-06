package ru.iammaxim.tesitems.NPC;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 09.06.2016.
 */
public class NPCAttributesCapabilityProvider implements ICapabilitySerializable<NBTTagCompound> {
    INPCAttributesCapability inst = TESItems.npcAttributesCapability.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing enumFacing) {
        return capability == TESItems.npcAttributesCapability;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing enumFacing) {
        if (capability == TESItems.npcAttributesCapability) {
            return TESItems.npcAttributesCapability.cast(inst);
        } else {
            return null;
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) TESItems.npcAttributesCapability.getStorage().writeNBT(TESItems.npcAttributesCapability, inst, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbtPrimitive) {
        TESItems.npcAttributesCapability.getStorage().readNBT(TESItems.npcAttributesCapability, inst, null, nbtPrimitive);
    }
}
