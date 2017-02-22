package ru.iammaxim.tesitems.World;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by maxim on 2/21/17 at 2:30 PM.
 */
public class LocationMarker {
    public float posX, posZ;
    public String name;

    public LocationMarker(float x, float z, String name) {
        this.posX = x;
        this.posZ = z;
        this.name = name;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setFloat("x", posX);
        tag.setFloat("z", posZ);
        tag.setString("name", name);
        return tag;
    }

    public static LocationMarker loadFromNBT(NBTTagCompound tag) {
        return new LocationMarker(tag.getFloat("x"), tag.getFloat("z"), tag.getString("name"));
    }
}
