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
        EntityPlayer player = (EntityPlayer) sender;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);

//        TESItems.networkWrapper.sendTo(new MessageSpellbook(cap.saveSpellbook(true)), (EntityPlayerMP) player);

        player.openGui(TESItems.instance, TESItems.guiEditSpells, ((EntityPlayerMP) player).worldObj, (int) ((EntityPlayerMP) player).posX, (int) ((EntityPlayerMP) player).posY, (int) ((EntityPlayerMP) player).posZ);

        /*if (!(sender instanceof EntityPlayer)) return;
        if (args.length < 5) return;
        INPCAttributesCapability cap = ((EntityPlayer) sender).getCapability(TESItems.playerAttributesCapability, null);
        int spellType = Integer.parseInt(args[1]);

        SpellEffect[] effects = new SpellEffect[(args.length-2)/3];
        for (int i = 0; i < effects.length; i++) {
            try {
                effects[i] = (SpellEffect) ReflectionUtils.createInstance(SpellEffectManager.getEffectByName(args[2 + i * 3]), Float.parseFloat(args[3 + i * 3]), Float.parseFloat(args[4 + i * 3]));
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        SpellBase spell = SpellBase.createSpell(spellType, args[0], effects);
        cap.getSpellbook().add(spell);
        TESItems.networkWrapper.sendTo(new MessageSpellbook(cap.saveSpellbook()), (EntityPlayerMP) sender);*/
    }
}
