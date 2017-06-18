package ru.iammaxim.tesitems.Utils;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.IOException;

/**
 * Created by maxim on 2/21/17 at 4:56 PM.
 */
public class NBTFileStorage {
    public static NBTTagCompound loadFromFile(String filename) throws IOException {
        File questList = new File(filename);
        if (!questList.exists())
            return new NBTTagCompound();
        return CompressedStreamTools.read(questList);
    }

    public static void saveToFile(String filename, NBTTagCompound tag) throws IOException {
        if (filename.contains("/")) {
            File dirCreator = new File(filename.substring(0, filename.lastIndexOf("/")));
            dirCreator.mkdirs();
        }
        File questList = new File(filename);
        CompressedStreamTools.write(tag, questList);
    }


}
