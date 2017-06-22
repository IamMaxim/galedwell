package ru.iammaxim.tesitems.NPC;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHandSide;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryNPC;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Magic.SpellEffect;
import ru.iammaxim.tesitems.Questing.Quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by maxim on 1/17/17 at 3:46 PM.
 */
public class NPC {
    //NPC's attributes and skills
    private HashMap<String, Float> attributes = new HashMap<>();
    //player spells
    private List<SpellBase> spellbook = new ArrayList<>();
    public int id = -1;
    public String name = "NPC";
    public String skinName = "default";
    public boolean isInvulnerable = false;
    public Inventory inventory;
    public EnumHandSide primaryHand = EnumHandSide.RIGHT;
    public List<Integer> factions = new ArrayList<>();
    public ArrayList<Quest> attachedQuests = new ArrayList<>();
    public float magicka = 0;
    // used by client to determine if skin need to be reloaded
    public boolean skinDirty = true;

    public NPC() {
        inventory = new InventoryNPC();
    }

    public boolean isInvulnerable() {
        return isInvulnerable;
    }

    public void setInvulnerable(boolean invulnerable) {
        isInvulnerable = invulnerable;
    }

    public List<Faction> getFactions() {
        ArrayList<Faction> factions1 = new ArrayList<>();
        factions.forEach(id -> factions1.add(FactionManager.getFaction(id)));
        return factions1;
    }

    public void addFaction(int faction) {
        factions.add(faction);
    }

    public void removeFaction(int faction) {
        factions.remove(faction);
    }

    public void setName(String name) {
        this.name = name;
    }

    public NBTTagCompound getNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return tag;
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setBoolean("isInvulnerable", isInvulnerable);
        tag.setString("name", name);
        tag.setString("skinName", skinName);
        tag.setTag("inventory", inventory.writeToNBT());
        tag.setTag("factions", saveFactions());
        tag.setTag("spellbook", saveSpellbook(true));
        tag.setFloat("magicka", magicka);
    }

    public void readFromNBT(NBTTagCompound tag) {
        name = tag.getString("name");
        skinName = tag.getString("skinName");
        isInvulnerable = tag.getBoolean("isInvulnerable");
        inventory.loadFromNBT(tag.getCompoundTag("inventory"));
        loadFactions((NBTTagList) tag.getTag("factions"));
        loadSpellbook(tag.getCompoundTag("spellbook"));
        magicka = tag.getFloat("magicka");
    }

    public NBTTagList saveFactions() {
        NBTTagList list = new NBTTagList();
        factions.forEach(f -> {
            NBTTagCompound idTag = new NBTTagCompound();
            idTag.setInteger("index", f);
            list.appendTag(idTag);
        });
        return list;
    }

    public void loadFactions(NBTTagList list) {
        factions.clear();
        for (int i = 0; i < list.tagCount(); i++) {
            factions.add(list.getCompoundTagAt(i).getInteger("index"));
        }
    }

    public void clearFactions() {
        factions.clear();
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    public float getMagicka() {
        return magicka;
    }

    public float getMaxMagicka() {
        return getAttribute("intelligence") * 10;
    }

    public void setMagicka(float magicka) {
        this.magicka = magicka;
    }

    public float getMagickaRecovery() {
        return getAttribute("willpower") / 20;
    }

    public void restoreMagicka() {
        magicka += getMagickaRecovery();
    }

    public float getAttribute(String s) {
        Float value = attributes.get(s);
        if (value == null) return 0;
        return attributes.get(s);
    }

    public void setAttribute(String name, float value) {
        attributes.put(name, value);
    }

    public void increaseAttribute(String name, float value) {
        Float f = attributes.get(name);
        if (f == null) return;
        if (f >= 100) return;
        attributes.put(name, f + value);
    }

    public HashMap<String, Float> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, Float> newAttrs) {
        attributes = newAttrs;
    }

    public List<SpellBase> getSpellbook() {
        return spellbook;
    }

    public void setSpellbook(List<SpellBase> spellbook) {
        this.spellbook = spellbook;
    }

    public void addSpell(SpellBase spell) {
        spellbook.add(spell);
    }

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
                list1.appendTag(effect.writeToNBT(writeScripts));
            }
            tag.setTag("effects", list1);
            list.appendTag(tag);
        }
        tagCompound.setTag("spellbook", list);
        return tagCompound;
    }
}
