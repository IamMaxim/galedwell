package ru.iammaxim.tesitems.Magic.SpellTypes;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Magic.SpellEffect;

/**
 * Created by Maxim on 17.07.2016.
 */
public class SpellBaseSelf extends SpellBase {
    public SpellBaseSelf(String name, SpellEffect... effects) {
        super(name, effects);
        setSpellType(SELF);
    }

    public SpellBaseSelf() {
        setSpellType(SELF);
    }

    @Override
    public void cast(EntityPlayer caster) {
        for (SpellEffect effect : effects) {
            effect.castSelf(caster);
        }
    }
}
