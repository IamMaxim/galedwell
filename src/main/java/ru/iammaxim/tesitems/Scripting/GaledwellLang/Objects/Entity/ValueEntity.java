package ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;

/**
 * Created by maxim on 2/21/17 at 1:03 PM.
 */
public class ValueEntity extends ValueObject {
    public Entity entity;

    public ValueEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public String valueToString() {
        return "entity: " + entity.getName();
    }

    @Override
    public NBTTagCompound writeToNBT() {
        return null;
    }
}
