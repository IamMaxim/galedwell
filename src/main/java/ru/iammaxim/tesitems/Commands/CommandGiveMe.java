package ru.iammaxim.tesitems.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import ru.iammaxim.tesitems.Inventory.Inventory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Created by maxim on 8/5/16 at 6:34 PM.
 */
public class CommandGiveMe extends CommandBase {
    @Override
    public String getCommandName() {
        return "giveme";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
//        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : (args.length == 2 ? getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys()) : Collections.emptyList());
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys()) : Collections.emptyList();
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        try {
            EntityPlayer player = (EntityPlayer) sender;
            Inventory inv = Inventory.getInventory(player);
            int count = 1;
            if (args.length == 2) count = Integer.parseInt(args[1]);
            Item item = Item.getByNameOrId(args[0]);
            for (int i = 0; i < count; i++)
                inv.addItem(new ItemStack(item));
        } catch (Exception e) {
            ((EntityPlayer) sender).addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Error occured while running command:\n" + e.toString()));
        }
    }
}
