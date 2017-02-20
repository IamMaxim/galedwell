package ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 2/19/17 at 12:20 AM.
 */
public class ValuePlayer extends ValueObject {
    public EntityPlayer player;
    public IPlayerAttributesCapability cap;

    //player functions
    private static final ValueFunction showNotification = new FunctionShowNotification(),
    damage = new FunctionPlayerDamage();

    public ValuePlayer(EntityPlayer player) {
        this.player = player;
        cap = TESItems.getCapability(player);

        setField("showNotification", showNotification);
        setField("damage", damage);
    }

    @Override
    public String toString() {
        return "player: " + player.getName();
    }

    @Override
    public String valueToString() {
        return toString();
    }

    @Override
    public NBTTagCompound writeToNBT() {
        return null;
    }
}
