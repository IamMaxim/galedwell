package ru.iammaxim.tesitems;

import net.minecraft.util.ResourceLocation;
import ru.iammaxim.tesitems.GUI.ResManager;

/**
 * Created by maxim on 3/1/17 at 8:51 PM.
 */
public class Resource {
    private static final ResourceLocation defaultRes = ResManager.fakeTexture;
    private ResourceLocation resourceLocation = defaultRes;

    public Resource(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public Resource() {}

    public ResourceLocation getRes() {
        return resourceLocation;
    }

    public void setRes(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }
}
