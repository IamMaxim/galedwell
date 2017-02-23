package ru.iammaxim.tesitems.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Dialogs.Dialog;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Maxim on 06.06.2016.
 */
public interface IPlayerAttributesCapability {
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
    void addQuest(QuestInstance quest);
    HashMap<Integer, QuestInstance> getQuests();
    float getCarryWeight();
    float getMaxCarryWeight();
    NPC getLatestNPC();
    void setLatestNPC(NPC npc);
    Inventory getInventory();
    void setInventory(Inventory inventory);
    void createInventory(EntityPlayer player, Inventory inv);
    void loadQuests(NBTTagCompound nbttag);
    NBTTagCompound saveQuests();
    void journalAppend(String s);
    String getJournal();
    void setJournal(String s);
    void setQuestStage(int questID, int stage);
    QuestInstance getQuest(int id);
    void setLatestDialog(Dialog dialog);
    Dialog getLatestDialog();
    ValueObject getVariableStorage();
    boolean isAuthorized();
    void authorize(EntityPlayerMP player);
    void setPassword(String pass);
    String getPassword();
    void setVariableStorage(ValueObject variableStorage);
}
