package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * Created by Maxim on 17.07.2016.
 */
public class KeyBindings {
    public static KeyBinding castSpellKB = new KeyBinding("Cast spell", Keyboard.KEY_C, "Magic");
    public static KeyBinding selectSpellKB = new KeyBinding("Open spell selection", Keyboard.KEY_O, "Magic");
    public static KeyBinding openInventoryKB = new KeyBinding("Open inventory", Keyboard.KEY_TAB, "Player");
    public static void register() {
        ClientRegistry.registerKeyBinding(castSpellKB);
        ClientRegistry.registerKeyBinding(selectSpellKB);
        ClientRegistry.registerKeyBinding(openInventoryKB);
    }
}
