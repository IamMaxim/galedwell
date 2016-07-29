package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Networking.SpellbookMessage;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.util.Iterator;

/**
 * Created by Maxim on 17.07.2016.
 */
public class CommandRemoveSpell extends CommandBase {
    @Override
    public String getCommandName() {
        return "removeSpell";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) return;
        if (args.length != 1) return;
        IPlayerAttributesCapability cap = TESItems.getCapatibility((EntityPlayer) sender);
        Iterator<SpellBase> it = cap.getSpellbook().iterator();
        while (it.hasNext()) {
            if (it.next().getName().equals(args[0])) it.remove();
        }
        TESItems.networkWrapper.sendTo(new SpellbookMessage(cap.getSpellbookNBT()), (EntityPlayerMP) sender);
    }
}
