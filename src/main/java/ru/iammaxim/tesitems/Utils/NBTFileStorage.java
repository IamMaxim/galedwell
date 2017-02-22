package ru.iammaxim.tesitems.Utils;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.IOException;

/**
 * Created by maxim on 2/21/17 at 4:56 PM.
 */
public class NBTFileStorage {
    private String DIRECTORY;
    private String FILENAME;

    public NBTFileStorage(String directory, String filename) {
        this.DIRECTORY = directory;
        this.FILENAME = filename;
    }

    public NBTTagCompound loadFromFile() throws IOException {
        File questList = new File(FILENAME);
        if (!questList.exists())
            return new NBTTagCompound();
        NBTTagCompound tag = CompressedStreamTools.read(questList);
        return tag;
    }

    public void saveToFile(NBTTagCompound tag) throws IOException {
        File dirCreator = new File(DIRECTORY);
        dirCreator.mkdirs();

        File questList = new File(FILENAME);
        CompressedStreamTools.write(tag, questList);
    }


}
