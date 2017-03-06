package ru.iammaxim.tesitems.NPC;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.iammaxim.tesitems.Dialogs.Dialog;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryClient;
import ru.iammaxim.tesitems.Inventory.InventoryContainer;
import ru.iammaxim.tesitems.Inventory.InventoryServer;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Magic.SpellEffect;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maxim on 06.06.2016.
 */
public class NPCAttributesCapabilityDefaultImpl implements INPCAttributesCapability {
    //player attributes and skills
    private HashMap<String, Float> attributes = new HashMap<>();
    //player spells
    private List<SpellBase> spellbook = new ArrayList<>();
    //current player's spell index
    private int currentSpell = -1;
    //latest NPC player interacted with
    private NPC latestNPC;
    //player inventory
    private Inventory inventory = new Inventory();
    //player active quests
    private HashMap<Integer, QuestInstance> quests = new HashMap<>();
    //player quest journal; quest lines are saved here
    private String journal = "";
    //latest dialog with NPC
    private Dialog dialog;
    private ValueObject variableStorage = new ValueObject();
    private String password = "";
    private boolean isAuthorized = false;
    private double loginX = 0, loginY = 0, loginZ = 0;
    private InventoryContainer latestContainer;

    public NPCAttributesCapabilityDefaultImpl() {
    }

    public void createInventory(EntityPlayer player, Inventory inv) {
        if (!player.worldObj.isRemote)
            inventory = new InventoryServer(player);
        else inventory = new InventoryClient(getClientPlayer());
        inventory.setItemList(inv.getItemList());
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public void journalAppend(String s) {
        journal = journal + s;
    }

    @Override
    public ValueObject getVariableStorage() {
        return variableStorage;
    }

    @Override
    public void setVariableStorage(ValueObject variableStorage) {
        this.variableStorage = variableStorage;
    }

    @Override
    public InventoryContainer getLatestContainer() {
        return latestContainer;
    }

    @Override
    public void setLatestContainer(InventoryContainer latestContainer) {
        this.latestContainer = latestContainer;
    }

    public String getJournal() {
        return journal;
    }

    @Override
    public float getAttribute(String s) {
        Float value = attributes.get(s);
        if (value == null) return 0;
        return attributes.get(s);
    }

    @Override
    public void setAttribute(String name, float value) {
        attributes.put(name, value);
    }

    @Override
    public void increaseAttribute(String name, float value) {
        Float f = attributes.get(name);
        if (f == null) return;
        if (f >= 100) return;
        attributes.put(name, f + value);
    }

    @Override
    public HashMap<String, Float> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(HashMap<String, Float> newAttrs) {
        attributes = newAttrs;
    }

    @Override
    public List<SpellBase> getSpellbook() {
        return spellbook;
    }

    @Override
    public void setSpellbook(List<SpellBase> spellbook) {
        this.spellbook = spellbook;
    }

    @Override
    public void addSpell(SpellBase spell) {
        spellbook.add(spell);
    }

    @Override
    public int getCurrentSpell() {
        return currentSpell;
    }

    @Override
    public void setCurrentSpell(int index) {
        currentSpell = index;
    }

    @Override
    public void loadSpellbook(NBTTagCompound nbttag) {
        spellbook.clear();
        NBTTagList list = (NBTTagList) nbttag.getTag("spellbook");
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag1 = (NBTTagCompound) list.get(i);
            String spellName = tag1.getString("name");
            int spellType = tag1.getInteger("type");
            NBTTagList list1 = (NBTTagList) tag1.getTag("effects");
            SpellEffect[] effects = new SpellEffect[list1.tagCount()];
            for (int j = 0; j < list1.tagCount(); j++) {
                effects[j] = SpellEffect.readFromNBT(list1.getCompoundTagAt(j));
            }
            SpellBase spell = SpellBase.createSpell(spellType, spellName, effects);
            spellbook.add(spell);
        }
    }

    @Override
    public NBTTagCompound saveSpellbook(boolean writeScripts) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (SpellBase spell : spellbook) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("name", spell.name);
            tag.setInteger("type", spell.getSpellType());
            NBTTagList list1 = new NBTTagList();
            for (int j = 0; j < spell.effects.length; j++) {
                SpellEffect effect = spell.effects[j];
                /*NBTTagCompound tag1 = new NBTTagCompound();
                tag1.setFloat("power", effect.getPower());
                tag1.setFloat("range", effect.getRange());
                tag1.setString("name", SpellEffectManager.getNameByEffect(effect.getClass()));*/
//                list1.appendTag(tag1);

                list1.appendTag(effect.writeToNBT(writeScripts));
            }
            tag.setTag("effects", list1);
            list.appendTag(tag);
        }
        tagCompound.setTag("spellbook", list);
        return tagCompound;
    }

    @Override
    public float getCarryWeight() {
        return inventory.carryweight;
    }

    @Override
    public float getMaxCarryWeight() {
        Float f = attributes.get("strength");
        if (f != null)
            return f;
        else
            return 0;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
