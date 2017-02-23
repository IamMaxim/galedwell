package ru.iammaxim.tesitems.Magic;

import java.util.HashMap;

/**
 * Created by Maxim on 11.07.2016.
 */
public class SpellEffectManager {
    private static HashMap<String, SpellEffect> effects = new HashMap<>();

    public static void add(SpellEffect effect) {
        effects.put(effect.getName(), effect);
    }

    public static SpellEffect get(String name) {
        return effects.get(name);
    }
}
