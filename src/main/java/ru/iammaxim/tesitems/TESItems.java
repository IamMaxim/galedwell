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
import java.util.Random;

@Mod(modid = TESItems.MODID, name = TESItems.MODNAME, version = TESItems.VERSION)
public class TESItems {
    // Some mod information
    public static final String MODID = "tesitems";
    public static final String MODNAME = "Galedwell";
    public static final String VERSION = "PREVIEW";
    public static final String attributesTagName = "tesitems:playerAttributes";
    public static final String worldTagName = "tesitems:world";

    public static final Random RANDOM = new Random();


    public static final String[] ATTRIBUTES = {
            // Cooking
            "baker",
            "cook",
            "confectioner",
            "butcher",
            "miller",
            "winemaker",
            "brewer",
            "mead_brewer",

            // Ground workers
            "plowman",
            "gardener",

            // Gathering
            "fisherman",
            "herbalist",
            "hunter",
            "picker",

            // Craft
            "smelter",
            "tanner",
            "armorer",
            "gunsmith",
            "weaver",
            "tailor",
            "shoemaker",
            "jeweler",
            "potter",
            "miner",
            "alchemist",
            "healer",

            // Battle
            "warrior",
            "archer",
            "crossbowman",

            // Misc
            "book_keeper",

            // Attributes
            "carryweight"
    };

    /**
     * Maximum possible skill level.
     * After reaching this value, skill stops to increase automatically.
     *
     * However, you can manually increase level beyond this border using
     * some other methods, for example, /setAttribute command.
     */
    public static final float maxSkillLevel = 4;

    /**
     * IDs of various GUI screens
     */
    public static final int
            guiSpellSelect = 0,
            guiNpcDialog = 1,
            guiInventory = 2,
            guiNPCEditor = 3,
            guiQuestEditor = 4,
            guiJournal = 5,
            guiFactionList = 6,
            guiFactionEditor = 7,
            guiQuestList = 8,
            guiEditSpells = 9,
            guiContainer = 10,
            guiWorkbench = 11,
            guiCraftingRecipeTypesList = 12;

    /**
     * Distances for different chat modes
     */
    public static final int whisperDistance = 2,
            talkDistance = 10,
            actionDistance = 20,
            shoutDistance = 40;

    /**
     * Instance of this mod
     */
    @Mod.Instance
    public static TESItems instance;
    @SidedProxy(
            clientSide = "ru.iammaxim.tesitems.Proxy.ClientProxy",
            serverSide = "ru.iammaxim.tesitems.Proxy.ServerProxy"
    )
    public static CommonProxy proxy;

    /**
     * Declaration of player capability for this mod
     */
    @CapabilityInject(IPlayerAttributesCapability.class)
    public static Capability<IPlayerAttributesCapability> playerAttributesCapability;

    /**
     * Declaration of world capability for this mod
     */
    @CapabilityInject(IWorldCapability.class)
    public static Capability<IWorldCapability> worldCapability;

    /**
     * Simple wrapper for Netty. We don't need to use full power of Netty, so we use this
     */
    public static SimpleNetworkWrapper networkWrapper;

    public static IPlayerAttributesCapability getCapability(EntityPlayer player) {
        return player.getCapability(TESItems.playerAttributesCapability, null);
    }

    /**
     * @return a player entity of current player.
     * Works only on client side
     */
    @SideOnly(Side.CLIENT)
    public static EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    /**
     * @return a Minecraft instance.
     * Works only on client side
     */
    @SideOnly(Side.CLIENT)
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    /**
     * @return a side (server or client) of caller
     */
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
