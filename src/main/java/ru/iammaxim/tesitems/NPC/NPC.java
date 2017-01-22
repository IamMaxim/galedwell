package ru.iammaxim.tesitems.NPC;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHandSide;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryNPC;
import ru.iammaxim.tesitems.Questing.Quest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 1/17/17 at 3:46 PM.
 */
public class NPC {
    public int id = -1;
    public String name = "NPC";
    public boolean isInvulnerable = false;
    public Inventory inventory;
    public EnumHandSide primaryHand = EnumHandSide.RIGHT;
    public List<Faction> factions = new ArrayList<>();
    public ArrayList<Quest> attachedQuests = new ArrayList<>();

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
        return factions;
    }

    public void addFaction(Faction faction) {
        factions.add(faction);
    }

    public void removeFaction(Faction faction) {
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
        tag.setTag("inventory", inventory.writeToNBT());
        tag.setTag("topics", saveFactions());
    }

    public void readFromNBT(NBTTagCompound tag) {
        name = tag.getString("name");
        isInvulnerable = tag.getBoolean("isInvulnerable");
        inventory.loadFromNBT(tag.getCompoundTag("inventory"));
        loadFactions((NBTTagList) tag.getTag("topics"));
    }

    public NBTTagList saveFactions() {
        NBTTagList list = new NBTTagList();
        factions.forEach(f -> {
            NBTTagCompound idTag = new NBTTagCompound();
            idTag.setInteger("id", f.id);
            list.appendTag(idTag);
        });
        return list;
    }

    public void loadFactions(NBTTagList list) {
        for (int i = 0; i < list.tagCount(); i++) {
            factions.add(FactionManager.getFaction(list.getCompoundTagAt(i).getInteger("id")));
        }
    }
}
