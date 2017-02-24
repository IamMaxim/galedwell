package ru.iammaxim.tesitems;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.iammaxim.tesitems.Blocks.mBlocks;
import ru.iammaxim.tesitems.Commands.*;
import ru.iammaxim.tesitems.Craft.CraftRecipe;
import ru.iammaxim.tesitems.Craft.CraftRecipes;
import ru.iammaxim.tesitems.GUI.Fonts.UnicodeFontRenderer;
import ru.iammaxim.tesitems.GUI.*;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Items.*;
import ru.iammaxim.tesitems.Magic.EntityFlyingSpell;
import ru.iammaxim.tesitems.Magic.EntityRangedSpellEffect;
import ru.iammaxim.tesitems.Magic.RenderEntityFlyingSpell;
import ru.iammaxim.tesitems.Magic.RenderEntityRangedSpellEffect;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.NPC.RenderNPC;
import ru.iammaxim.tesitems.Networking.MessageCastSpell;
import ru.iammaxim.tesitems.Networking.MessageOpenGui;
import ru.iammaxim.tesitems.Networking.NetworkUtils;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Player.PlayerAttributesCapabilityDefaultImpl;
import ru.iammaxim.tesitems.Player.PlayerAttributesCapabilityProvider;
import ru.iammaxim.tesitems.Player.PlayerAttributesStorage;
import ru.iammaxim.tesitems.World.IWorldCapability;
import ru.iammaxim.tesitems.World.WorldCapabilityDefaultImpl;
import ru.iammaxim.tesitems.World.WorldCapabilityProvider;
import ru.iammaxim.tesitems.World.WorldCapabilityStorage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

@Mod(modid = TESItems.MODID, version = TESItems.VERSION)
public class TESItems {
    public static final String MODID = "tesitems";
    public static final String VERSION = "1.0";
    public static final String attributesTagName = "TESItems:playerAttributes";
    public static final String worldTagName = "TESItems:world";
    public static final String[] ATTRIBUTES = {
            "strength",
            "mining",
            "woodcutting",
            "digging",
            "blades",
            "blunt"
    };
    public static final float maxSkillLevel = 100;
    public static final Block[] miningBlocks = {
            Blocks.STONE_STAIRS,
            Blocks.STONE_BRICK_STAIRS,
            Blocks.SANDSTONE_STAIRS
    }, woodcuttingBlocks = {
            Blocks.WOODEN_SLAB,
            Blocks.DOUBLE_WOODEN_SLAB,
            Blocks.ACACIA_STAIRS,
            Blocks.BIRCH_STAIRS,
            Blocks.DARK_OAK_STAIRS,
            Blocks.JUNGLE_STAIRS,
            Blocks.OAK_STAIRS,
            Blocks.SPRUCE_STAIRS,
            Blocks.ACACIA_DOOR,
            Blocks.BIRCH_DOOR,
            Blocks.DARK_OAK_DOOR,
            Blocks.JUNGLE_DOOR,
            Blocks.OAK_DOOR
    }, diggingBlocks = {

    }, furnutureBlocks = {

    };
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

    @CapabilityInject(IPlayerAttributesCapability.class)
    public static Capability<IPlayerAttributesCapability> attributesCapability;

    @CapabilityInject(IWorldCapability.class)
    public static Capability<IWorldCapability> worldCapability;

    @Mod.Instance
    public static TESItems instance;
    //used for dialog camera orient
    /*
    @SideOnly(Side.CLIENT)
    public static EntityFakeCamera fakeCamera;
    */
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

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerAttributesCapability.class, new PlayerAttributesStorage(), PlayerAttributesCapabilityDefaultImpl::new);
        CapabilityManager.INSTANCE.register(IWorldCapability.class, new WorldCapabilityStorage(), WorldCapabilityDefaultImpl::new);
        MinecraftForge.EVENT_BUS.register(this);
        new Auth().register();
        mItems.register(event.getSide());
        mBlocks.register(event.getSide());
        mArmor.register();

        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("TESItemsChannel");
        NetworkUtils.registerMessages();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
        EntityRegistry.registerModEntity(EntityRangedSpellEffect.class, "EntityRangedSpellEffect", 0, instance, 100, 1, false);
        EntityRegistry.registerModEntity(EntityFlyingSpell.class, "EntityFlyingSpell", 1, instance, 100, 1, true);
        EntityRegistry.registerModEntity(EntityNPC.class, "EntityNPC", 2, instance, 80, 3, true);

        if (event.getSide() == Side.CLIENT) {
            RenderingRegistry.registerEntityRenderingHandler(EntityRangedSpellEffect.class, RenderEntityRangedSpellEffect::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityFlyingSpell.class, RenderEntityFlyingSpell::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityNPC.class, RenderNPC::new);
        }
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void clientInit(FMLInitializationEvent event) {
        KeyBindings.register();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        for (Field o : Items.class.getFields()) {
            try {
                Item i = (Item) o.get(null);
                i.setMaxStackSize(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for (Field o : Blocks.class.getFields()) {
            try {
                Block b = (Block) o.get(null);
                Item i = Item.getItemFromBlock(b);
                if (i != null)
                    i.setMaxStackSize(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        Item.getItemFromBlock(Blocks.PLANKS).setMaxStackSize(4);
        Items.STICK.setMaxStackSize(8);
        Item.getItemFromBlock(Blocks.TORCH).setMaxStackSize(8);
        Items.IRON_INGOT.setMaxStackSize(2);
        Items.GOLD_INGOT.setMaxStackSize(2);
        CraftRecipes.addRecipe(new CraftRecipe("log", new ItemStack[]{new ItemStack(Items.STICK, 4)}, new ItemStack(Item.getItemFromBlock(Blocks.LOG), 1)));
        CraftRecipes.addRecipe(new CraftRecipe("testLargeStacks", new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.DIRT), 100)}, new ItemStack(Item.getItemFromBlock(Blocks.COBBLESTONE), 1)));
        ItemWeightManager.init();
        ItemValueManager.init();
//        QuestManager.loadFromFile();

        if (event.getSide() == Side.CLIENT) {
            ClientThings.loadFonts();
            ResManager.loadShaders();
            ScreenStack.instance = new ScreenStack();
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        IPlayerAttributesCapability cap = getCapability(player);

        if (!cap.isAuthorized() && cap.getLoginY() != 0) {
            player.setPositionAndUpdate(cap.getLoginX(), cap.getLoginY(), cap.getLoginZ());
            return;
        }

        if (cap.getCarryWeight() > cap.getMaxCarryWeight())
            event.player.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 5, 3));
    }

    private boolean isMiningBlock(Block b) {
        for (Block b1 : miningBlocks)
            if (b == b1) return true;
        return false;
    }

    private boolean isWoodcuttingBlock(Block b) {
        for (Block b1 : woodcuttingBlocks)
            if (b == b1) return true;
        return false;
    }

    private boolean isDiggingBlock(Block b) {
        for (Block b1 : diggingBlocks)
            if (b == b1) return true;
        return false;
    }

    @SubscribeEvent
    public void getBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        Entity p = event.getEntity();
        Block b = event.getState().getBlock();
        if (p instanceof EntityPlayer) {
            float speed = event.getOriginalSpeed();

            //Check if player uses a breaking tool
            if ((((EntityPlayer) p).getHeldItemMainhand() != null && ((EntityPlayer) p).getHeldItemMainhand().getItem() == mItems.itemBreakingTool) || (((EntityPlayer) p).getHeldItemOffhand() != null && ((EntityPlayer) p).getHeldItemOffhand().getItem() == mItems.itemBreakingTool)) {
                speed *= 0.2;
            } else {
                //Player doesn't use breaking tool, get break speed by player skill
                String blockType = null;
                if (b instanceof BlockStone || b instanceof BlockStoneBrick || b instanceof BlockOre || b instanceof BlockSandStone) {
                    blockType = "mining";
                } else if (b instanceof BlockLog || b instanceof BlockPlanks || b instanceof BlockFence || b instanceof BlockFenceGate) {
                    blockType = "woodcutting";
                } else if (b instanceof BlockDirt || b instanceof BlockSand || b instanceof BlockGravel || b instanceof BlockGrass || b instanceof BlockGrassPath) {
                    blockType = "digging";
                }

                if (blockType == null) {
                    event.setNewSpeed(0);
                    return;
                }
                speed *= p.getCapability(attributesCapability, null).getAttribute(blockType) / maxSkillLevel;
            }
            event.setNewSpeed(speed);
        }
    }


    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            //clear vanilla inventory
            player.inventory.clear();
            IPlayerAttributesCapability cap = TESItems.getCapability((EntityPlayer) event.getEntity());
            if (cap.getAttribute("strength") == 0)
                cap.setAttribute("strength", 10);
            cap.createInventory(player, cap.getInventory());
            cap.getInventory().calculateCarryweight();
        }
    }

    @SubscribeEvent
    public void onClonePLayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        getCapability(event.getEntityPlayer()).setAttributes(getCapability(event.getOriginal()).getAttributes());
        getCapability(event.getEntityPlayer()).setSpellbook(getCapability(event.getOriginal()).getSpellbook());
        getCapability(event.getEntityPlayer()).setInventory(getCapability(event.getOriginal()).getInventory());
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(TESItems.attributesTagName), new PlayerAttributesCapabilityProvider());
        } else if (event.getObject() instanceof World) {
            System.out.println("attaching cap to world");
            event.addCapability(new ResourceLocation(TESItems.worldTagName), new WorldCapabilityProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        IPlayerAttributesCapability cap = event.player.getCapability(attributesCapability, null);
        for (String s : ATTRIBUTES) {
            System.out.println(s + " = " + cap.getAttribute(s));
        }
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSetAttribute());
        event.registerServerCommand(new CommandCraft());
        event.registerServerCommand(new CommandGetSkills());
        event.registerServerCommand(new CommandCreateSpell());
        event.registerServerCommand(new CommandRemoveSpell());
        event.registerServerCommand(new CommandGiveMe());
        event.registerServerCommand(new CommandManageFactions());
        event.registerServerCommand(new CommandManageQuests());
        event.registerServerCommand(new CommandStatus());
        event.registerServerCommand(new CommandSetPassword());
        event.registerServerCommand(new CommandLogin());

        //debug
        event.registerServerCommand(new CommandManageInventory());
        event.registerServerCommand(new CommandReloadFonts());
        event.registerServerCommand(new CommandCreateMap());
    }

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack held = player.getHeldItemMainhand();
        event.setCanceled(true);
        if (held == null) return;
        Item item = held.getItem();
        if (item instanceof Weapon) {
            if (!player.worldObj.isRemote) {
                IPlayerAttributesCapability cap = TESItems.getCapability(player);
                float multiplier = 0;
                if (((Weapon) item).getWeaponType() == WeaponType.BLADES) {
                    float skill = cap.getAttribute("blades");
                    multiplier = skill / maxSkillLevel;
                    cap.increaseAttribute("blades", getSkillIncreaseValue(skill));
                } else if (((Weapon) item).getWeaponType() == WeaponType.BLUNT) {
                    float skill = cap.getAttribute("blunt");
                    multiplier = skill / maxSkillLevel;
                    cap.increaseAttribute("blunt", getSkillIncreaseValue(skill));
                }
                if (multiplier >= 0)
                    event.getTarget().attackEntityFrom(DamageSource.causePlayerDamage(player), multiplier * ((ItemSword) item).getDamageVsEntity());
            }
            held.damageItem(1, player);
            if (held.getItemDamage() <= 0) {
                player.setHeldItem(EnumHand.MAIN_HAND, null);
            }
        }
    }

    public float getSkillIncreaseValue(float current) {
        float f = (1 + current / maxSkillLevel);
        return 1 / f / f / 100;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (!FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
            if (KeyBindings.castSpellKB.isKeyDown()) {
                networkWrapper.sendToServer(new MessageCastSpell(getCapability(Minecraft.getMinecraft().thePlayer).getCurrentSpell()));
            }
            if (KeyBindings.selectSpellKB.isKeyDown()) {
                networkWrapper.sendToServer(new MessageOpenGui(TESItems.guiSpellSelect));
            }
            if (KeyBindings.openInventoryKB.isKeyDown()) {
                networkWrapper.sendToServer(new MessageOpenGui(TESItems.guiInventory));
            }
            if (KeyBindings.openJournalKB.isKeyDown()) {
                networkWrapper.sendToServer(new MessageOpenGui(TESItems.guiJournal));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderNametag(RenderLivingEvent.Specials.Pre event) {
        if (event.getEntity() instanceof EntityPlayer || event.getEntity() instanceof EntityNPC)
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        event.setCanceled(true);
        Inventory inv = Inventory.getInventory(event.getEntityPlayer());
        inv.addItem(event.getItem().getEntityItem());
        event.getItem().setDead();
    }

    @SubscribeEvent
    public void onHUDDraw(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) return;
        if (TESItems.getMinecraft().currentScreen == null) {
            NotificationManager.draw();
            getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ClientThings {
        public static FontRenderer fontRenderer; //used in all mod UI
        public static FontRenderer monospaceFontRenderer; //used in script editor

        public static void loadFonts() {
            //load main font
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("main.ttf")));
                font = font.deriveFont(Font.PLAIN, 24);
                ClientThings.fontRenderer = new UnicodeFontRenderer(font);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
                ClientThings.fontRenderer = getMinecraft().fontRendererObj;
            }

            //load monospace font
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("monospace.ttf")));
                font = font.deriveFont(Font.PLAIN, 16);
                ClientThings.monospaceFontRenderer = new UnicodeFontRenderer(font);
                ((UnicodeFontRenderer) ClientThings.monospaceFontRenderer).setTopOffset(3);
//            ((UnicodeFontRenderer)monospaceFontRenderer).font.setPaddingTop(4);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
                ClientThings.monospaceFontRenderer = getMinecraft().fontRendererObj;
            }
        }
    }

    public static Side getSide() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER || Thread.currentThread().getName().startsWith("Netty Epoll Server IO"))
            return Side.SERVER;
        else return Side.CLIENT;
    }

/*    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save event) {
    }*/
}
