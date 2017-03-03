package ru.iammaxim.tesitems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Proxy.CommonProxy;
import ru.iammaxim.tesitems.World.IWorldCapability;

import java.io.IOException;

@Mod(modid = TESItems.MODID, name = TESItems.MODNAME, version = TESItems.VERSION)
public class TESItems {
    public static final String MODID = "tesitems";
    public static final String MODNAME = "Galedwell";
    public static final String VERSION = "PREVIEW";
    public static final String attributesTagName = "tesitems:playerAttributes";
    public static final String worldTagName = "tesitems:world";
    public static final String[] ATTRIBUTES = {
            "strength",
            "mining",
            "woodcutting",
            "digging",
            "blades",
            "blunt"
    };
    public static final float maxSkillLevel = 100;
    public static final int guiSpellSelect = 0,
            guiNpcDialog = 1,
            guiInventory = 2,
            guiNPCEditor = 3,
            guiQuestEditor = 4,
            guiJournal = 5,
            guiFactionList = 6,
            guiFactionEditor = 7,
            guiQuestList = 8,
            guiEditSpells = 9;
    @Mod.Instance
    public static TESItems instance;
    @SidedProxy(clientSide = "ru.iammaxim.tesitems.Proxy.ClientProxy", serverSide = "ru.iammaxim.tesitems.Proxy.ServerProxy")
    public static CommonProxy proxy;
    @CapabilityInject(IPlayerAttributesCapability.class)
    public static Capability<IPlayerAttributesCapability> attributesCapability;

    @CapabilityInject(IWorldCapability.class)
    public static Capability<IWorldCapability> worldCapability;

    public static SimpleNetworkWrapper networkWrapper;

    public static IPlayerAttributesCapability getCapability(EntityPlayer player) {
        return player.getCapability(TESItems.attributesCapability, null);
    }

    @SideOnly(Side.CLIENT)
    public static EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @SideOnly(Side.CLIENT)
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static Side getSide() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER || Thread.currentThread().getName().startsWith("Netty Epoll Server IO"))
            return Side.SERVER;
        else return Side.CLIENT;
    }

    public float getSkillIncreaseValue(float current) {
        float f = (1 + current / maxSkillLevel);
        return 1 / f / f / 100;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
