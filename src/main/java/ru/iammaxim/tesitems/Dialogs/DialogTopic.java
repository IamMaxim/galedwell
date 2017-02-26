package ru.iammaxim.tesitems.Dialogs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import ru.iammaxim.tesitems.Questing.Condition;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.GaledwellLang;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;

/**
 * Created by maxim on 11/11/16 at 7:57 PM.
 */
public class DialogTopic {
    public Quest attachedTo;
    public String npcName = "";
    public String name = "";
    public String script = "";
    public ValueObject object;
    public String dialogLine = "";
    public ArrayList<Condition> conditions = new ArrayList<>();

    public static DialogTopic readFromNBT(NBTTagCompound tag) {
        DialogTopic topic = new DialogTopic();
        topic.npcName = tag.getString("npcName");
        topic.name = tag.getString("name");
        topic.script = tag.getString("script");

        topic.object = new ValueObject();
        if (TESItems.getSide() == Side.SERVER)
            try {
                GaledwellLang.loadSrcInto(topic.script, topic.object);
            } catch (Exception e) {
                e.printStackTrace();
            }

        topic.dialogLine = tag.getString("dialogLine");
        NBTTagList conditionsList = (NBTTagList) tag.getTag("conditions");
        for (int i = 0; i < conditionsList.tagCount(); i++) {
            topic.conditions.add(Condition.loadFromNBT(conditionsList.getCompoundTagAt(i)));
        }
        return topic;
    }

    public boolean needToAdd(EntityPlayer player) {
        for (Condition condition : conditions) {
            if (!condition.isTrue(player)) {
                return false;
            }
        }
        return true;
    }

    public void attachTo(Quest quest) {
        attachedTo = quest;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("npcName", npcName);
        tag.setString("name", name);
        tag.setString("dialogLine", dialogLine);
        tag.setString("script", script);
        NBTTagList conditionsList = new NBTTagList();
        conditions.forEach(c -> conditionsList.appendTag(c.saveToNBT()));
        tag.setTag("conditions", conditionsList);
        return tag;
    }

    public DialogTopic copy() {
        DialogTopic dest = new DialogTopic();
        dest.name = name;
        dest.script = script;
        dest.attachedTo = attachedTo;
        dest.dialogLine = dialogLine;
        dest.npcName = npcName;
        dest.object = object;
        conditions.forEach(c -> dest.conditions.add(c.copy()));
        return dest;
    }
}
