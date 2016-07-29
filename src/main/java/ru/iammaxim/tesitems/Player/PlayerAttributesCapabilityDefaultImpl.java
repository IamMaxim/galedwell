package ru.iammaxim.tesitems.Player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryClient;
import ru.iammaxim.tesitems.Inventory.InventoryServer;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Magic.SpellEffectBase;
import ru.iammaxim.tesitems.Magic.SpellEffectManager;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maxim on 06.06.2016.
 */
public class PlayerAttributesCapabilityDefaultImpl implements IPlayerAttributesCapability {
    private HashMap<String, Float> attributes = new HashMap<String, Float>();
    private List<SpellBase> spellbook = new ArrayList<>();
    private int currentSpell = -1;
    private EntityNPC latestNPC;
    private Inventory inventory = new Inventory();

    public PlayerAttributesCapabilityDefaultImpl() {
    }

    public void createInventory(EntityPlayer player, Inventory inv) {
        if (!player.worldObj.isRemote)
            inventory = new InventoryServer(player);
        else inventory = new InventoryClient(Minecraft.getMinecraft().thePlayer);
        inventory.setItemList(inv.getItemList());
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
    public void addSpell(SpellBase spell) {
        spellbook.add(spell);
    }
    @Override
    public void setCurrentSpell(int index) {
        currentSpell = index;
    }
    @Override
    public int getCurrentSpell() {
        return currentSpell;
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
            SpellEffectBase[] effects = new SpellEffectBase[list1.tagCount()];
            for (int j = 0; j < list1.tagCount(); j++) {
                NBTTagCompound tag2 = (NBTTagCompound) list1.get(j);
                String effectName = tag2.getString("name");
                float power = tag2.getFloat("power");
                float range = tag2.getFloat("range");
                try {
                    effects[j] = (SpellEffectBase) ReflectionUtils.createInstance(SpellEffectManager.getEffectByName(effectName), power, range);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            SpellBase spell = SpellBase.createSpell(spellType, spellName, effects);
            spellbook.add(spell);
        }
    }
    @Override
    public NBTTagCompound getSpellbookNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < spellbook.size(); i++) {
            SpellBase spell = spellbook.get(i);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("name", spell.getName());
            tag.setInteger("type", spell.getSpellType());
            NBTTagList list1 = new NBTTagList();
            for (int j = 0; j < spell.effects.length; j++) {
                SpellEffectBase effect = spell.effects[j];
                NBTTagCompound tag1 = new NBTTagCompound();
                tag1.setFloat("power", effect.getPower());
                tag1.setFloat("range", effect.getRange());
                tag1.setString("name", SpellEffectManager.getNameByEffect(effect.getClass()));
                list1.appendTag(tag1);
            }
            tag.setTag("effects", list1);
            list.appendTag(tag);
        }
        tagCompound.setTag("spellbook", list);
        return tagCompound;
    }
    @Override
    public void setSpellbook(List<SpellBase> spellbook) {
        this.spellbook = spellbook;
    }
    @Override
    public void addQuest(QuestInstance quest) {

    }
    @Override
    public List<QuestInstance> getQuests() {
        return null;
    }
    @Override
    public float getCarryWeight() {
        return 0;
    }
    @Override
    public EntityNPC getLatestNPC() {
        return latestNPC;
    }
    @Override
    public void setLatestNPC(EntityNPC npc) {
        latestNPC = npc;
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
