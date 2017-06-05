package ru.iammaxim.tesitems.NPC;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
    public String skinName = "default";
    public boolean isInvulnerable = false;
    public Inventory inventory;
    public EnumHandSide primaryHand = EnumHandSide.RIGHT;
    public List<Integer> factions = new ArrayList<>();
    public ArrayList<Quest> attachedQuests = new ArrayList<>();
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
        tag.setTag("topics", saveFactions());
    }

    public void readFromNBT(NBTTagCompound tag) {
        name = tag.getString("name");
        skinName = tag.getString("skinName");
        isInvulnerable = tag.getBoolean("isInvulnerable");
        inventory.loadFromNBT(tag.getCompoundTag("inventory"));
        loadFactions((NBTTagList) tag.getTag("topics"));
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
        for (int i = 0; i < list.tagCount(); i++) {
            factions.add(list.getCompoundTagAt(i).getInteger("index"));
        }
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }
}
