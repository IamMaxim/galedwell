package ru.iammaxim.tesitems.Utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayer;
import ru.iammaxim.tesitems.Networking.MessageShowNotification;
import ru.iammaxim.tesitems.TESItems;

import java.util.List;
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

    public static void showNotification(EntityPlayer player, String message) {
        TESItems.networkWrapper.sendTo(new MessageShowNotification(message), (EntityPlayerMP) player);
    }

    public static void sendChatMessage(List<EntityPlayerMP> players, EntityPlayerMP player, String message, float distance) {
        for (EntityPlayer p : players)
            if (p.getDistanceSqToEntity(player) < distance * distance)
                p.addChatComponentMessage(new TextComponentString(message));
    }
}
