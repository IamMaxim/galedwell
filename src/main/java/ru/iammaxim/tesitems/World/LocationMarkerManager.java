package ru.iammaxim.tesitems.World;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

/**
 * Created by maxim on 2/21/17 at 2:30 PM.
 */
public class LocationMarkerManager {
    ArrayList<LocationMarker> markers = new ArrayList<>();

    public void addMarker(LocationMarker marker) {
        markers.add(marker);
    }

    public NBTTagCompound saveToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        markers.forEach(m -> list.appendTag(m.writeToNBT()));
        tag.setTag("markers", list);
        return tag;
    }

    public void loadFromNBT(NBTTagCompound tag) {
        NBTTagList list = (NBTTagList) tag.getTag("markers");
        for (int i = 0; i < list.tagCount(); i++) {
            markers.add(LocationMarker.loadFromNBT(list.getCompoundTagAt(i)));
        }
    }
}
