package ru.iammaxim.tesitems.Proxy;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import ru.iammaxim.tesitems.AuthEventListener;
import ru.iammaxim.tesitems.Blocks.BlockManager;
import ru.iammaxim.tesitems.Blocks.mBlocks;
import ru.iammaxim.tesitems.Commands.*;
import ru.iammaxim.tesitems.ConfigManager;
import ru.iammaxim.tesitems.GUI.GUIHandler;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Items.*;
import ru.iammaxim.tesitems.Magic.EntityFlyingSpell;
import ru.iammaxim.tesitems.Magic.EntityRangedSpellEffect;
import ru.iammaxim.tesitems.NPC.*;
import ru.iammaxim.tesitems.Networking.NetworkUtils;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Player.PlayerAttributesCapabilityDefaultImpl;
import ru.iammaxim.tesitems.Player.PlayerAttributesCapabilityProvider;
import ru.iammaxim.tesitems.Player.PlayerAttributesCapabilityStorage;
import ru.iammaxim.tesitems.TESItems;
import ru.iammaxim.tesitems.World.IWorldCapability;
import ru.iammaxim.tesitems.World.WorldCapabilityDefaultImpl;
import ru.iammaxim.tesitems.World.WorldCapabilityProvider;
import ru.iammaxim.tesitems.World.WorldCapabilityStorage;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by maxim on 3/3/17 at 7:01 PM.
 */
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        //load config values
        ConfigManager.loadConfig();

        CapabilityManager.INSTANCE.register(IPlayerAttributesCapability.class, new PlayerAttributesCapabilityStorage(), PlayerAttributesCapabilityDefaultImpl::new);
        CapabilityManager.INSTANCE.register(IWorldCapability.class, new WorldCapabilityStorage(), WorldCapabilityDefaultImpl::new);
        MinecraftForge.EVENT_BUS.register(this);
        new AuthEventListener().register();
        mItems.register(event.getSide());
        mBlocks.register(event.getSide());
        mArmor.register(event.getSide());

        TESItems.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("TESItemsChannel");
        NetworkUtils.registerMessages();

        NetworkRegistry.INSTANCE.registerGuiHandler(TESItems.instance, new GUIHandler());
        EntityRegistry.registerModEntity(EntityRangedSpellEffect.class, "EntityRangedSpellEffect", 0, TESItems.instance, 100, 1, false);
        EntityRegistry.registerModEntity(EntityFlyingSpell.class, "EntityFlyingSpell", 1, TESItems.instance, 100, 1, true);
        EntityRegistry.registerModEntity(EntityNPC.class, "EntityNPC", 2, TESItems.instance, 80, 3, true);
    }

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
        ItemWeightManager.init();
        ItemValueManager.init();
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);

        //reset to first slot
        if (player.inventory.currentItem != 0)
            player.inventory.currentItem = 0;

        if (!cap.isAuthorized() && cap.getLoginY() != 0) {
            player.setPositionAndUpdate(cap.getLoginX(), cap.getLoginY(), cap.getLoginZ());
            return;
        }

        if (cap.getCarryWeight() > cap.getMaxCarryWeight())
            event.player.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 5, 3));

        cap.restoreMagicka();
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        if (event.getItemInHand() != null) {
            if (!event.getPlayer().isCreative() && event.getItemInHand().stackSize == 1) {
                IPlayerAttributesCapability cap = TESItems.getCapability(event.getPlayer());
                int index = cap.getInventory().getItemStackIndex(event.getItemInHand());
                if (index != -1) // block has been taken from creative mode or with middle mouse button gives index -1
                    cap.getInventory().removeItem(index);
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        ItemStack is = player.getHeldItemMainhand();
        if (is != null && is.getItem() instanceof ItemTool && is.getItemDamage() == is.getMaxDamage()) {
            IPlayerAttributesCapability cap = TESItems.getCapability(player);
            cap.getInventory().removeItem(cap.getInventory().getItemStackIndex(is));
        }
    }

    @SubscribeEvent
    public void getBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        Entity p = event.getEntity();
        Block b = event.getState().getBlock();
        if (p instanceof EntityPlayer) {
            float speed = event.getOriginalSpeed();

            //Check if player uses a breaking tool
            if ((((EntityPlayer) p).getHeldItemMainhand() != null && ((EntityPlayer) p).getHeldItemMainhand().getItem() == mItems.itemBreakingTool) || (((EntityPlayer) p).getHeldItemOffhand() != null && ((EntityPlayer) p).getHeldItemOffhand().getItem() == mItems.itemBreakingTool)) {
                speed *= 2;
            } else {
                //Player doesn't use breaking tool, get break speed by player skill
                String blockType = null;
                if (BlockManager.requiresNothing(b))
                    blockType = "allowed";
                if (BlockManager.isMiningBlock(b)) {
                    blockType = "mining";
                } else if (BlockManager.isWoodcuttingBlock(b)) {
                    blockType = "woodcutting";
                } else if (BlockManager.isDiggingBlock(b)) {
                    blockType = "digging";
                }

                if (blockType == null) {
                    event.setNewSpeed(0);
                    return;
                }

                if (blockType.equals("allowed"))
                    return;

                speed *= p.getCapability(TESItems.playerAttributesCapability, null).getAttribute(blockType) / TESItems.maxSkillLevel;
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
            IPlayerAttributesCapability cap = TESItems.getCapability(player);
            if (cap.getAttribute("strength") == 0)
                cap.setAttribute("strength", 10);
            cap.createInventory(player, cap.getInventory());
            cap.getInventory().calculateCarryweight();
        }
    }

    @SubscribeEvent
    public void onClonePLayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        IPlayerAttributesCapability origCap = TESItems.getCapability(event.getOriginal());
        IPlayerAttributesCapability destCap = TESItems.getCapability(event.getEntityPlayer());

        destCap.setAttributes(origCap.getAttributes());
        destCap.setSpellbook(origCap.getSpellbook());
        destCap.setInventory(origCap.getInventory());
        if (origCap.isAuthorized())
            destCap.authorize((EntityPlayerMP) event.getEntityPlayer());

        String password = origCap.getPassword();
        if (!password.isEmpty())
            destCap.setPassword(password);
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof EntityPlayer)
            event.addCapability(new ResourceLocation(TESItems.attributesTagName), new PlayerAttributesCapabilityProvider());
        else if (event.getObject() instanceof World)
            event.addCapability(new ResourceLocation(TESItems.worldTagName), new WorldCapabilityProvider());
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        IPlayerAttributesCapability cap = event.player.getCapability(TESItems.playerAttributesCapability, null);
        for (String s : TESItems.ATTRIBUTES) {
            System.out.println(s + " = " + cap.getAttribute(s));
        }
    }

    @SubscribeEvent
    public void onItemUse(LivingEntityUseItemEvent.Finish event) {
        ItemStack is = event.getItem();
        if (is.stackSize == 0) {
            if (event.getEntityLiving() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                IPlayerAttributesCapability cap = TESItems.getCapability(player);
                cap.getInventory().removeItem(cap.getInventory().getItemStackIndex(is));
            }
        }
    }

    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandSetAttribute());
        event.registerServerCommand(new CommandGetSkills());
        event.registerServerCommand(new CommandCreateSpell());
        event.registerServerCommand(new CommandRemoveSpell());
        event.registerServerCommand(new CommandGiveMe());
        event.registerServerCommand(new CommandManageFactions());
        event.registerServerCommand(new CommandManageQuests());
        event.registerServerCommand(new CommandStatus());
        event.registerServerCommand(new CommandSetPassword());
        event.registerServerCommand(new CommandLogin());
        event.registerServerCommand(new CommandManageCraftingRecipes());
        event.registerServerCommand(new CommandStr());
        event.registerServerCommand(new CommandInt());
        event.registerServerCommand(new CommandWill());
        event.registerServerCommand(new CommandAgi());
        event.registerServerCommand(new CommandSpd());
        event.registerServerCommand(new CommandEnd());
        event.registerServerCommand(new CommandCha());
        event.registerServerCommand(new CommandLuck());
        event.registerServerCommand(new CommandWhisper());
        event.registerServerCommand(new CommandShout());

        //debug
        event.registerServerCommand(new CommandManageInventory());
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
                    multiplier = skill / TESItems.maxSkillLevel;
                    cap.increaseAttribute("blades", TESItems.instance.getSkillIncreaseValue(skill));
                } else if (((Weapon) item).getWeaponType() == WeaponType.BLUNT) {
                    float skill = cap.getAttribute("blunt");
                    multiplier = skill / TESItems.maxSkillLevel;
                    cap.increaseAttribute("blunt", TESItems.instance.getSkillIncreaseValue(skill));
                }
                if (multiplier >= 0)
                    event.getTarget().attackEntityFrom(DamageSource.causePlayerDamage(player), multiplier * ((ItemSword) item).getDamageVsEntity());
            }
            held.damageItem(1, player);
            if (held.getItemDamage() >= held.getMaxDamage()) {
                if (TESItems.getSide() == Side.SERVER) {
                    //remove item from inventory
                    IPlayerAttributesCapability cap = TESItems.getCapability(player);
                    Inventory inv = cap.getInventory();
                    inv.removeItem(inv.getItemStackIndex(held));
                }
                //remove item from hand
                player.setHeldItem(EnumHand.MAIN_HAND, null);
            }
        }
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        event.setCanceled(true);
        Inventory inv = Inventory.getInventory(event.getEntityPlayer());
        inv.addItem(event.getItem().getEntityItem());
        event.getItem().setDead();
    }

    public void init(FMLInitializationEvent event) {
    }

    @SubscribeEvent
    public void chatEvent(ServerChatEvent event) {
        System.out.println("chatEvent()");
        event.setCanceled(true);
        EntityPlayer player = event.getPlayer();

        // default mode is talk
        event.getPlayer().getServer().getPlayerList().getPlayerList().forEach(p -> {
            if (player.getDistanceSqToEntity(p) <= TESItems.talkDistance * TESItems.talkDistance) {
                System.out.println(player.getDistanceSqToEntity(p));
                p.addChatComponentMessage(event.getComponent());
            }
        });
    }
}
