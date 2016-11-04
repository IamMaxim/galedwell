package ru.iammaxim.tesitems;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
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
import ru.iammaxim.tesitems.GUI.GUIHandler;
import ru.iammaxim.tesitems.GUI.KeyBindings;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Items.Weapon;
import ru.iammaxim.tesitems.Items.WeaponType;
import ru.iammaxim.tesitems.Items.mArmor;
import ru.iammaxim.tesitems.Items.mItems;
import ru.iammaxim.tesitems.Magic.*;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.NPC.RenderNPC;
import ru.iammaxim.tesitems.Networking.*;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Player.PlayerAttributesCapabilityDefaultImpl;
import ru.iammaxim.tesitems.Player.PlayerAttributesCapabilityProvider;
import ru.iammaxim.tesitems.Player.PlayerAttributesStorage;

import java.lang.reflect.Field;

@Mod(modid = TESItems.MODID, version = TESItems.VERSION)
public class TESItems {
    public static final String MODID = "tesitems";
    public static final String VERSION = "1.0";
    public static final String attributesTagName = "TESItems:playerAttributes";
    public static final String[] ATTRIBUTES = {
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
    public static final int guiSpellSelect = 0, guiNpcDialog = 1, guiInventory = 2;
    @CapabilityInject(IPlayerAttributesCapability.class)
    public static Capability<IPlayerAttributesCapability> attributesCapability;
    @Mod.Instance
    public static TESItems instance;

    //used for dialog camera orient
    /*
    @SideOnly(Side.CLIENT)
    public static EntityFakeCamera fakeCamera;
    */
    public static SimpleNetworkWrapper networkWrapper;

    public static IPlayerAttributesCapability getCapatibility(EntityPlayer player) {
        return player.getCapability(TESItems.attributesCapability, null);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerAttributesCapability.class, new PlayerAttributesStorage(), PlayerAttributesCapabilityDefaultImpl.class);
        MinecraftForge.EVENT_BUS.register(this);
        SpellEffectManager.register();
        mItems.register(event.getSide());
        mBlocks.register(event.getSide());
        mArmor.register();
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("TESItemsChannel");
        networkWrapper.registerMessage(AttributesMessageHandler.class, AttributesMessage.class, 0, Side.CLIENT);
        networkWrapper.registerMessage(OpenGuiMessageHandler.class, OpenGuiMessage.class, 1, Side.SERVER);
        networkWrapper.registerMessage(CastSpellMessageHandler.class, CastSpellMessage.class, 2, Side.SERVER);
        networkWrapper.registerMessage(SpellbookMessageHandler.class, SpellbookMessage.class, 3, Side.CLIENT);
        networkWrapper.registerMessage(InventoryUpdateMessageClientHandler.class, InventoryUpdateMessage.class, 4, Side.CLIENT);
        networkWrapper.registerMessage(InventoryUpdateMessageServerHandler.class, InventoryUpdateMessage.class, 5, Side.SERVER);
        networkWrapper.registerMessage(InventoryMessageHandler.class, InventoryMessage.class, 6, Side.CLIENT);
        networkWrapper.registerMessage(EquipMessageServerHandler.class, EquipMessage.class, 7, Side.SERVER);
        networkWrapper.registerMessage(EquipMessageClientHandler.class, EquipMessage.class, 8, Side.CLIENT);
        networkWrapper.registerMessage(ItemDropMessageHandlerServer.class, ItemDropMessage.class, 9, Side.SERVER);

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
        EntityRegistry.registerModEntity(EntityRangedSpellEffect.class, "EntityRangedSpellEffect", 0, instance, 100, 1, false);
        EntityRegistry.registerModEntity(EntityFlyingSpell.class, "EntityFlyingSpell", 1, instance, 100, 1, true);
        EntityRegistry.registerModEntity(EntityNPC.class, "EntityNPC", 2, instance, 80, 3, true);
        RenderingRegistry.registerEntityRenderingHandler(EntityRangedSpellEffect.class, RenderEntityRangedSpellEffect::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFlyingSpell.class, RenderEntityFlyingSpell::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityNPC.class, RenderNPC::new);
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
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
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
            ((EntityPlayer) event.getEntity()).inventory.clear();
            IPlayerAttributesCapability cap = event.getEntity().getCapability(TESItems.attributesCapability, null);
            cap.createInventory((EntityPlayer) event.getEntity(), cap.getInventory());
            if (!event.getWorld().isRemote) {
                networkWrapper.sendTo(new AttributesMessage(cap.getAttributes()), (EntityPlayerMP) event.getEntity());
                networkWrapper.sendTo(new SpellbookMessage(cap.getSpellbookNBT()), (EntityPlayerMP) event.getEntity());
                networkWrapper.sendTo(new InventoryMessage(cap.getInventory().writeToNBT()), (EntityPlayerMP) event.getEntity());
            }
        }
    }

    @SubscribeEvent
    public void onClonePLayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        getCapatibility(event.getEntityPlayer()).setAttributes(getCapatibility(event.getOriginal()).getAttributes());
        getCapatibility(event.getEntityPlayer()).setSpellbook(getCapatibility(event.getOriginal()).getSpellbook());
        getCapatibility(event.getEntityPlayer()).setInventory(getCapatibility(event.getOriginal()).getInventory());
    }

    @SubscribeEvent
    public void onEntityLoad(AttachCapabilitiesEvent.Entity event) {
        if (event.getEntity() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(TESItems.attributesTagName), new PlayerAttributesCapabilityProvider());
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

        //debug
        event.registerServerCommand(new CommandManageInventory());
    }

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {
        event.setCanceled(true);
        if (event.getEntityPlayer().worldObj.isRemote || event.getEntityPlayer().getHeldItemMainhand() == null) return;
        Item item = event.getEntityPlayer().getHeldItemMainhand().getItem();
        if (item instanceof Weapon) {
            IPlayerAttributesCapability cap = event.getEntityPlayer().getCapability(attributesCapability, null);
//            System.out.println("Resetting attack " + ((Weapon) item).time + " " + ((Weapon) item).lastAttack + " " + ((Weapon) item).attackTime);
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
                event.getTarget().attackEntityFrom(DamageSource.causePlayerDamage(event.getEntityPlayer()), multiplier * ((ItemSword) item).getDamageVsEntity());
            event.getEntityPlayer().getHeldItemMainhand().damageItem(1, event.getEntityPlayer());
        }
    }

/*    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Inventory inventory = Inventory.getInventory(event.getEntityPlayer());
        inventory.checkSlot(inventory.getItemList().indexOf(event.getItemStack()));
    }*/

/*    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Inventory inventory = Inventory.getInventory(event.getEntityPlayer());
        inventory.checkSlot(inventory.getItemList().indexOf(event.getItemStack());
    }*/

    public float getSkillIncreaseValue(float current) {
        float f = (1 + current / maxSkillLevel);
        return 1 / f / f / 100;
    }

    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (!FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
            if (KeyBindings.castSpellKB.isKeyDown()) {
                networkWrapper.sendToServer(new CastSpellMessage(getCapatibility(Minecraft.getMinecraft().thePlayer).getCurrentSpell()));
            }
            if (KeyBindings.selectSpellKB.isKeyDown()) {
                networkWrapper.sendToServer(new OpenGuiMessage(TESItems.guiSpellSelect));
            }
            if (KeyBindings.openInventoryKB.isKeyDown()) {
                networkWrapper.sendToServer(new OpenGuiMessage(TESItems.guiInventory));
            }
        }
    }

    @SubscribeEvent
    public void onRenderNametag(RenderLivingEvent.Specials.Pre event) {
        if (event.getEntity() instanceof EntityPlayer || event.getEntity() instanceof EntityNPC) event.setCanceled(true);
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        System.out.println("on item pickup");
        event.setCanceled(true);
        Inventory inv = Inventory.getInventory(event.getEntityPlayer());
        inv.addItem(event.getItem().getEntityItem());
        //event.getItem().getEntityItem().stackSize = 0;
        event.getItem().setDead();
    }
}
