package ru.iammaxim.tesitems.GUI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 17.07.2016.
 */
public class GUIHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TESItems.getCapability(player).getInventory().checkInventory();
        return new DummyContainer();
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case TESItems.guiSpellSelect: return new GuiSpellSelect();
            case TESItems.guiNpcDialog: return new GuiNpcDialog();
            case TESItems.guiInventory: return new GuiInventory(player);
        }
        return null;
    }
}
