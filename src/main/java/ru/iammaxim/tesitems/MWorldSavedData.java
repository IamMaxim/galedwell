package ru.iammaxim.tesitems;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.Questing.QuestManager;

/**
 * Created by maxim on 31.12.2016.
 */
public class MWorldSavedData extends WorldSavedData {
    public static final String NAME = "MWorldSavedData";

    public MWorldSavedData() {
        super(NAME);
    }

    public MWorldSavedData(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        System.out.println("Reading MWorldSavedData from NBT");
        FactionManager.loadFromNBT((NBTTagList) nbt.getTag("factions"));
        QuestManager.loadFromNBT(nbt.getCompoundTag("quests"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        System.out.println("Writing MWorldSavedData to NBT");
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("factions", FactionManager.writeToNBT());
        tag.setTag("quests", QuestManager.writeToNBT());
        return tag;
    }

    public static MWorldSavedData get(World world) {
        MapStorage mapStorage = world.getMapStorage();
        MWorldSavedData mWorldSavedData = (MWorldSavedData) mapStorage.getOrLoadData(MWorldSavedData.class, NAME);
        if (mWorldSavedData == null) {
            mWorldSavedData = new MWorldSavedData();
            mapStorage.setData(NAME, mWorldSavedData);
        }
        return mWorldSavedData;
    }
}
