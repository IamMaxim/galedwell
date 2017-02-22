package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ru.iammaxim.tesitems.Networking.MessageSpellbook;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

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
        IPlayerAttributesCapability cap = TESItems.getCapability((EntityPlayer) sender);
        cap.getSpellbook().removeIf(spellBase -> spellBase.getName().equals(args[0]));
        TESItems.networkWrapper.sendTo(new MessageSpellbook(cap.saveSpellbook(false)), (EntityPlayerMP) sender);
    }
}
