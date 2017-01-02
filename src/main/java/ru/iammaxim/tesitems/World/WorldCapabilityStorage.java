package ru.iammaxim.tesitems.World;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.Questing.QuestManager;

/**
 * Created by maxim on 02.01.2017.
 */
public class WorldCapabilityStorage implements Capability.IStorage<IWorldCapability> {
    @Override
    public NBTBase writeNBT(Capability<IWorldCapability> capability, IWorldCapability instance, EnumFacing side) {
        System.out.println("writeNBT()");
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("factions", FactionManager.writeToNBT());
        tag.setTag("quests", QuestManager.writeToNBT());
        return tag;
    }

    @Override
    public void readNBT(Capability<IWorldCapability> capability, IWorldCapability instance, EnumFacing side, NBTBase nbt) {
        System.out.println("readNBT()");
        FactionManager.readFromNBT((NBTTagList) ((NBTTagCompound)nbt).getTag("factions"));
        QuestManager.readFromNBT((NBTTagList) ((NBTTagCompound)nbt).getTag("quests"));
    }
}
