package ru.iammaxim.tesitems.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import ru.iammaxim.tesitems.GUI.HUD;
import ru.iammaxim.tesitems.GUI.KeyBindings;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Networking.MessageCastSpell;
import ru.iammaxim.tesitems.Networking.MessageOpenGui;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 3/3/17 at 8:24 PM.
 */
public class ClientHandler {
    @SubscribeEvent
    public void onRenderNametag(RenderLivingEvent.Specials.Pre event) {
        if (event.getEntity() instanceof EntityPlayer || event.getEntity() instanceof EntityNPC)
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (!FMLClientHandler.instance().isGUIOpen(GuiChat.class)) {
            if (KeyBindings.castSpellKB.isKeyDown()) {
                TESItems.networkWrapper.sendToServer(new MessageCastSpell(TESItems.getCapability(Minecraft.getMinecraft().thePlayer).getCurrentSpell()));
            }
            if (KeyBindings.selectSpellKB.isKeyDown()) {
                TESItems.networkWrapper.sendToServer(new MessageOpenGui(TESItems.guiSpellSelect));
            }
            if (KeyBindings.openInventoryKB.isKeyDown()) {
                TESItems.networkWrapper.sendToServer(new MessageOpenGui(TESItems.guiInventory));
            }
            if (KeyBindings.openJournalKB.isKeyDown()) {
                TESItems.networkWrapper.sendToServer(new MessageOpenGui(TESItems.guiJournal));
            }
        }
    }

    @SubscribeEvent
    public void onHUDDraw(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH ||
                event.getType() == RenderGameOverlayEvent.ElementType.FOOD ||
                event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            event.setCanceled(true);
            return;
        }

        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) return;
        event.setCanceled(true); //hotbar will be replaced
        HUD.drawHUD();
    }
}
