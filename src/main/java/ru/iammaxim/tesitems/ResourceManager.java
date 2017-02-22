package ru.iammaxim.tesitems;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

/**
 * Created by maxim on 2/21/17 at 5:23 PM.
 */
public class ResourceManager {
    public static HashMap<String, ResourceLocation> resources = new HashMap<>();

    public static ResourceLocation getResource(String location) {
        return resources.computeIfAbsent(location, k -> new ResourceLocation(location));
    }
}
