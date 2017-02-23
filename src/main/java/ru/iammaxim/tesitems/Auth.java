package ru.iammaxim.tesitems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;

public class Auth {
    private static boolean registered = false;

    public void register() {
        if (registered)
            return;
        registered = true;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerMoveEvent(PlayerEvent.LivingUpdateEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();

        if (player.worldObj.isRemote)
            return;

        IPlayerAttributesCapability cap = TESItems.getCapability(player);

        if (!cap.isAuthorized()) {

        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void serverChatEvent(ServerChatEvent event) {
        EntityPlayer player = event.getPlayer();

        if (player.getEntityWorld().isRemote)
            return;

        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (!cap.isAuthorized()) {
            event.setCanceled(true);
            player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Login required."));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void commandEvent(CommandEvent event) {
        EntityPlayer player = (EntityPlayer) event.getSender();

        if (player.worldObj.isRemote)
            return;

        if (event.getCommand().getCommandName().equals("login"))
            return; //let the player log in

        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (!cap.isAuthorized()) {
            event.setCanceled(true);
            player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Login required."));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerInteractEvent(PlayerInteractEvent event) {
        EntityPlayer player = event.getEntityPlayer();

        if (player.worldObj.isRemote)
            return;

        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (!cap.isAuthorized()) {
            if (event.isCancelable())
                event.setCanceled(true);
            player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Login required."));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void entityInteractEvent(PlayerInteractEvent.EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();

        if (player.worldObj.isRemote)
            return;

        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (!cap.isAuthorized()) {
            event.setCanceled(true);
            player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Login required."));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void minecartInteractEvent(MinecartInteractEvent event) {
        EntityPlayer player = event.getPlayer();

        if (player.worldObj.isRemote)
            return;

        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (!cap.isAuthorized()) {
            event.setCanceled(true);
            player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Login required."));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void itemTossEvent(ItemTossEvent event) {
        EntityPlayer player = event.getPlayer();

        if (player.worldObj.isRemote)
            return;

        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (!cap.isAuthorized()) {
            // add the item back to the inventory
            ItemStack stack = event.getEntityItem().getEntityItem();
            cap.getInventory().addItem(stack);
            event.setCanceled(true);
            player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Login required."));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void entityItemPickupEvent(EntityItemPickupEvent event) {
        EntityPlayer player = event.getEntityPlayer();

        if (player.worldObj.isRemote)
            return;

        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (!cap.isAuthorized()) {
            event.setCanceled(true);
            player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Login required."));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void livingHurtEvent(LivingHurtEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();

        if (player.worldObj.isRemote)
            return;

        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (!cap.isAuthorized()) {
            event.setCanceled(true);
            player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Login required."));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void attackEntityEvent(AttackEntityEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (!cap.isAuthorized()) {
            event.setCanceled(true);
            player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Login required."));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void playerLoggedInEvent(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;

        if (player.worldObj.isRemote)
            return;

        IPlayerAttributesCapability cap = TESItems.getCapability(player);

        cap.setLoginX(player.posX);
        cap.setLoginY(player.posY);
        cap.setLoginZ(player.posZ);

        if (cap.getPassword().isEmpty()) {
            player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "You are not registered.\n" + TextFormatting.RED + "It's recommended to you to " + TextFormatting.YELLOW + "/setPassword <password> <repeatPassword>" + TextFormatting.RED + ", so no one can login from your account."));
            cap.authorize((EntityPlayerMP) player);
        } else {
            player.addChatComponentMessage(new TextComponentString(TextFormatting.YELLOW + "/login <password>" + TextFormatting.RED + " required."));
        }
    }

/*    @SubscribeEvent
    public void playerLoggedOutEvent(PlayerLoggedOutEvent event) {
        ModuleAuth.deauthenticate(event.player.getPersistentID());
    }*/

    // autologin

/*    @SubscribeEvent
    public void onClientHandshake(ClientHandshakeEstablished e)
    {
        if (!ModuleAuth.isEnabled())
            return;
        if (ModuleAuth.isRegistered(e.getPlayer().getPersistentID())  && !ModuleAuth.isAuthenticated(e.getPlayer()))
        {
            NetworkUtils.netHandler.sendTo(new Packet6AuthLogin(0, ""), e.getPlayer());
        }
    }*/

/*    @SubscribeEvent
    public void onAuthLogin(PlayerAuthLoginEvent.Success e)
    {
        if (e.source == Source.COMMAND && ModuleAuth.allowAutoLogin)
        {
            UUID token = UUID.randomUUID();
            NetworkUtils.netHandler.sendTo(new Packet6AuthLogin(2, token.toString()), e.getPlayer());
            PasswordManager.addSession(e.getPlayer().getPersistentID(), token);
        }
        APIRegistry.scripts.runEventScripts(ModuleAuth.SCRIPT_KEY_SUCCESS, e.getPlayer());
    }*/

//    @SubscribeEvent
//    public void onAuthLoginFail(PlayerAuthLoginEvent.Failure e)
//    {
//        APIRegistry.scripts.runEventScripts(ModuleAuth.SCRIPT_KEY_FAILURE, e.getPlayer());
//    }
}
