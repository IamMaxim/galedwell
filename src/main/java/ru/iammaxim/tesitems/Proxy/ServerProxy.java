package ru.iammaxim.tesitems.Proxy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.iammaxim.tesitems.Networking.MessageMagickaUpdate;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 3/3/17 at 7:29 PM.
 */
public class ServerProxy extends CommonProxy {
    @Override
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        super.onPlayerTick(event);
        IPlayerAttributesCapability cap = TESItems.getCapability(event.player);
        if (!cap.isAuthorized())
            return;
        cap.restoreMagicka();
        TESItems.networkWrapper.sendTo(new MessageMagickaUpdate(cap.getMagicka()), (EntityPlayerMP) event.player);
    }
}
