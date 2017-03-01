package ru.iammaxim.tesitems;

import java.util.HashMap;

/**
 * Created by maxim on 2/21/17 at 5:23 PM.
 */
public class ResourceManager {
    public static HashMap<String, Resource> resources = new HashMap<>();

    public static Resource getResource(String location) {
        //TODO: request file from server
        return resources.computeIfAbsent(location, k -> new Resource());
    }
}
