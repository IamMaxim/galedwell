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
        return new DummyContainer();
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            /*case TESItems.guiSpellSelect: return new GuiSpellSelect();
            case TESItems.guiNpcDialog: return new GuiNPCDialog();
            case TESItems.guiInventory: return new GuiInventory(player);
            case TESItems.guiJournal: return new GuiJournal();
            case TESItems.guiNPCEditor: return new GuiNPCEditor(player);
            case TESItems.guiFactionList: return new GuiFactionList();
            case TESItems.guiQuestList: return new GuiQuestList();
            case TESItems.guiFactionEditor: return new GuiFactionEditor();
            case TESItems.guiQuestEditor: return new GuiQuestEditor();*/

            case TESItems.guiInventory: return new GuiInventory(player);
            case TESItems.guiSpellSelect: new GuiSpellSelect().show(); return ScreenStack.instance;
            case TESItems.guiNpcDialog: new GuiNPCDialog().show(); return ScreenStack.instance;
            case TESItems.guiJournal: new GuiJournal().show(); return ScreenStack.instance;
            case TESItems.guiNPCEditor: new GuiNPCEditor(player).show(); return ScreenStack.instance;
            case TESItems.guiFactionList: new GuiFactionList().show(); return ScreenStack.instance;
            case TESItems.guiQuestList: new GuiQuestList().show(); return ScreenStack.instance;
            case TESItems.guiFactionEditor: new GuiFactionEditor().show(); return ScreenStack.instance;
            case TESItems.guiQuestEditor: new GuiQuestEditor().show(); return ScreenStack.instance;
            case TESItems.guiEditSpells: new GuiSpellEditorList().show(); return ScreenStack.instance;
        }
        return null;
    }
}
