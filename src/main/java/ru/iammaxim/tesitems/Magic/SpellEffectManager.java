package ru.iammaxim.tesitems.Magic;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import ru.iammaxim.tesitems.Magic.SpellEffects.SpellEffectDamage;
import ru.iammaxim.tesitems.Magic.SpellEffects.SpellEffectHeal;

/**
 * Created by Maxim on 11.07.2016.
 */
public class SpellEffectManager {
    private static BiMap<String, Class> effects = HashBiMap.create();
    private static BiMap<Class, String> effectsInversed = HashBiMap.create();

    public static Class getEffectByName(String name) {
        return effects.get(name);
    }

    public static String getNameByEffect(Class effect) {
        checkMap();
        return effectsInversed.get(effect);
    }

    private static void checkMap() {
        if (effects.size() != effectsInversed.size()) {
            effectsInversed = effects.inverse();
        }
    }

    public static void register() {
        effects.put("heal", SpellEffectHeal.class);
        effects.put("damage", SpellEffectDamage.class);
    }
}
