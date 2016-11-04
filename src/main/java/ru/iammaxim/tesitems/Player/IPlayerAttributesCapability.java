package ru.iammaxim.tesitems.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Questing.QuestInstance;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Maxim on 06.06.2016.
 */
public interface IPlayerAttributesCapability {
    public float getAttribute(String name);
    public void setAttribute(String name, float value);
    public void increaseAttribute(String name, float value);
    public HashMap<String, Float> getAttributes();
    public void setAttributes(HashMap<String,Float> newAttrs);
    public List<SpellBase> getSpellbook();
    public void addSpell(SpellBase spell);
    public void loadSpellbook(NBTTagCompound nbttag);
    public void setCurrentSpell(int index);
    public int getCurrentSpell();
    public NBTTagCompound getSpellbookNBT();
    public void setSpellbook(List<SpellBase> spellbook);
    public void addQuest(QuestInstance quest);
    public List<QuestInstance> getQuests();
    public float getCarryWeight();
    public float getMaxCarryweight();
    public EntityNPC getLatestNPC();
    public void setLatestNPC(EntityNPC npc);
    public Inventory getInventory();
    public void setInventory(Inventory inventory);
    public void createInventory(EntityPlayer player, Inventory inv);
}
