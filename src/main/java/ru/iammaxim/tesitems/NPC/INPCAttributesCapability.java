package ru.iammaxim.tesitems.NPC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Dialogs.Dialog;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryContainer;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Maxim on 06.06.2016.
 */
public interface INPCAttributesCapability {
    float getAttribute(String name);
    void setAttribute(String name, float value);
    void increaseAttribute(String name, float value);
    HashMap<String, Float> getAttributes();
    void setAttributes(HashMap<String, Float> newAttrs);
    List<SpellBase> getSpellbook();
    void setSpellbook(List<SpellBase> spellbook);
    void addSpell(SpellBase spell);
    void loadSpellbook(NBTTagCompound nbttag);
    int getCurrentSpell();
    void setCurrentSpell(int index);
    NBTTagCompound saveSpellbook(boolean sendScripts);
    float getCarryWeight();
    float getMaxCarryWeight();
    Inventory getInventory();
    void setInventory(Inventory inventory);
    void createInventory(EntityPlayer player, Inventory inv);
    ValueObject getVariableStorage();
    void setVariableStorage(ValueObject variableStorage);
    InventoryContainer getLatestContainer();
    void setLatestContainer(InventoryContainer latestContainer);
}
