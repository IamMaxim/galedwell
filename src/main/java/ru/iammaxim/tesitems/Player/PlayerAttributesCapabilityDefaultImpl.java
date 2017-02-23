package ru.iammaxim.tesitems.Player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.iammaxim.tesitems.Dialogs.Dialog;
import ru.iammaxim.tesitems.Inventory.Inventory;
import ru.iammaxim.tesitems.Inventory.InventoryClient;
import ru.iammaxim.tesitems.Inventory.InventoryServer;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Magic.SpellEffect;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Networking.MessageAttributes;
import ru.iammaxim.tesitems.Networking.MessageInventory;
import ru.iammaxim.tesitems.Networking.MessageJournal;
import ru.iammaxim.tesitems.Networking.MessageSpellbook;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maxim on 06.06.2016.
 */
public class PlayerAttributesCapabilityDefaultImpl implements IPlayerAttributesCapability {
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

    public PlayerAttributesCapabilityDefaultImpl() {
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

    @Override
    public void loadQuests(NBTTagCompound nbttag) {
        quests.clear();
        NBTTagList nbtList = (NBTTagList) nbttag.getTag("quests");
        for (int i = 0; i < nbtList.tagCount(); i++) {
            QuestInstance inst = new QuestInstance();
            inst.loadFromNBT(nbtList.getCompoundTagAt(i));
            quests.put(inst.quest_id, inst);
        }
    }

    @Override
    public NBTTagCompound saveQuests() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList nbtlist = new NBTTagList();
        for (int i = 0; i < quests.size(); i++)
            nbtlist.appendTag(quests.get(i).writeToNBT());
        tag.setTag("quests", nbtlist);
        return tag;
    }

    @Override
    public void journalAppend(String s) {
        journal = journal + s;
    }

    @Override
    public void setQuestStage(int questID, int stage) {
        if (stage == -1) { //complete quest
            quests.remove(questID);
        }
        QuestInstance inst = getQuest(questID);
        inst.stage = stage;
        journalAppend(inst.quest.stages.get(stage).journalLine + "\n\n");
    }

    @Override
    public QuestInstance getQuest(int id) {
        return quests.get(id);
    }

    @Override
    public Dialog getLatestDialog() {
        return dialog;
    }

    @Override
    public ValueObject getVariableStorage() {
        return variableStorage;
    }

    @Override
    public boolean isAuthorized() {
        return isAuthorized;
    }

    @Override
    public void authorize(EntityPlayerMP player) {
        System.out.println("Authorized " + player.getName());
        isAuthorized = true;

        //send player data to client
        TESItems.networkWrapper.sendTo(new MessageAttributes(getAttributes()), player);
        TESItems.networkWrapper.sendTo(new MessageSpellbook(saveSpellbook(false)), player);
        TESItems.networkWrapper.sendTo(new MessageInventory(getInventory().writeToNBT()), player);
        TESItems.networkWrapper.sendTo(new MessageJournal(getJournal()), player);

        //send MOTD
        TextComponentBase motd = new TextComponentString(TextFormatting.YELLOW + "Добро пожаловать на тестовый сервер Галедвелл!\n" +
                TextFormatting.RESET + "Полезные команды:\n" +
                TextFormatting.BLUE + "/giveme <название предмета> <кол-во (необязательно)>\n" +
                TextFormatting.RESET + "Чтобы ломать любые блоки, используйте предмет " + TextFormatting.AQUA + "breakingTool");
        player.addChatComponentMessage(motd);
    }

    @Override
    public void setPassword(String pass) {
        this.password = pass;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setVariableStorage(ValueObject variableStorage) {
        this.variableStorage = variableStorage;
    }

    @Override
    public double getLoginX() {
        return loginX;
    }

    @Override
    public double getLoginY() {
        return loginY;
    }

    @Override
    public double getLoginZ() {
        return loginZ;
    }

    @Override
    public double setLoginX(double x) {
        return loginX = x;
    }

    @Override
    public double setLoginY(double y) {
        return loginY = y;
    }

    @Override
    public double setLoginZ(double z) {
        return loginZ = z;
    }

    @Override
    public void setLatestDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public String getJournal() {
        return journal;
    }

    @Override
    public void setJournal(String s) {
        journal = s;
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

                /*NBTTagCompound tag2 = (NBTTagCompound) list1.get(j);
                String effectName = tag2.getString("name");
                float power = tag2.getFloat("power");
                float range = tag2.getFloat("range");
                try {
                    effects[j] = (SpellEffect) ReflectionUtils.createInstance(SpellEffectManager.getEffectByName(effectName), power, range);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
                    e.printStackTrace();
                }*/
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
    public void addQuest(QuestInstance inst) {
        quests.put(inst.quest_id, inst);
    }

    @Override
    public HashMap<Integer, QuestInstance> getQuests() {
        return quests;
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
    public NPC getLatestNPC() {
        return latestNPC;
    }

    @Override
    public void setLatestNPC(NPC npc) {
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
