package ru.iammaxim.tesitems.World;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import ru.iammaxim.tesitems.Craft.CraftRecipes;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.Questing.QuestManager;

/**
 * Created by maxim on 02.01.2017.
 */
public class WorldCapabilityStorage implements Capability.IStorage<IWorldCapability> {
    @Override
    public NBTBase writeNBT(Capability<IWorldCapability> capability, IWorldCapability instance, EnumFacing side) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("factions", FactionManager.writeToNBT());
//        tag.setTag("quests", QuestManager.writeToNBT());
        QuestManager.saveToFile();
        CraftRecipes.saveToFile();
        return tag;
    }

    @Override
    public void readNBT(Capability<IWorldCapability> capability, IWorldCapability instance, EnumFacing side, NBTBase nbt) {
        FactionManager.readFromNBT((NBTTagList) ((NBTTagCompound) nbt).getTag("factions"));
//        QuestManager.readFromNBT((NBTTagList) ((NBTTagCompound)nbt).getTag("quests"));
        QuestManager.loadFromFile();
        CraftRecipes.loadFromFile();
    }
}
