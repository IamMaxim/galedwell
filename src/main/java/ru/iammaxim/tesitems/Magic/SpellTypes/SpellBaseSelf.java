package ru.iammaxim.tesitems.Magic.SpellTypes;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Magic.SpellEffectBase;

/**
 * Created by Maxim on 17.07.2016.
 */
public class SpellBaseSelf extends SpellBase {
    public SpellBaseSelf(String name, SpellEffectBase... effects) {
        super(name, effects);
    }

    @Override
    public void cast(EntityPlayer caster) {
        for (SpellEffectBase effect : effects) {
            effect.castSelf(caster);
        }
    }
}
