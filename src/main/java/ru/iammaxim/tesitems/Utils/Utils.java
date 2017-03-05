package ru.iammaxim.tesitems.Utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

/**
 * Created by maxim on 2/23/17 at 9:07 PM.
 */
public class Utils {
    public static MinecraftServer getServer() {
        WorldServer worldServer = DimensionManager.getWorld(0); // default world
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "FakePlayer");
        FakePlayer fakePlayer = new FakePlayer(worldServer, gameProfile);
        return fakePlayer.mcServer;
    }

    public static void printStackTrace() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : elements) {
            System.out.println(element);
        }
    }
}
