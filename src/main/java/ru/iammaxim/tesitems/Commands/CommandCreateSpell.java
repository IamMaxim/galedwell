package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Magic.SpellEffectBase;
import ru.iammaxim.tesitems.Magic.SpellEffectManager;
import ru.iammaxim.tesitems.Networking.SpellbookMessage;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.ReflectionUtils;
import ru.iammaxim.tesitems.TESItems;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Maxim on 12.07.2016.
 */
public class CommandCreateSpell extends CommandBase {
    @Override
    public String getCommandName() {
        return "createSpell";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) return;
        if (args.length < 5) return;
        IPlayerAttributesCapability cap = ((EntityPlayer) sender).getCapability(TESItems.attributesCapability, null);
        int spellType = Integer.parseInt(args[1]);

        SpellEffectBase[] effects = new SpellEffectBase[(args.length-2)/3];
        for (int i = 0; i < effects.length; i++) {
            try {
                effects[i] = (SpellEffectBase) ReflectionUtils.createInstance(SpellEffectManager.getEffectByName(args[2 + i * 3]), Float.parseFloat(args[3 + i * 3]), Float.parseFloat(args[4 + i * 3]));
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        SpellBase spell = SpellBase.createSpell(spellType, args[0], effects);
        cap.getSpellbook().add(spell);
        TESItems.networkWrapper.sendTo(new SpellbookMessage(cap.getSpellbookNBT()), (EntityPlayerMP) sender);
    }
}
