package ru.iammaxim.tesitems.Dialogs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.Questing.Condition;

import java.util.ArrayList;

/**
 * Created by maxim on 11/11/16 at 7:57 PM.
 */
public class DialogTopic {
    public String npcName;
    public String name;
    public String script;
    public String dialogLine;
    public ArrayList<Condition> conditions = new ArrayList<>();

    public boolean needToAdd(EntityPlayer player) {
        for (Condition condition : conditions) {
            if (!condition.isTrue(player)) {
                return false;
            }
        }
        return true;
    }

    public NBTTagCompound saveToNBT() {
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

    public static DialogTopic loadFromNBT(NBTTagCompound tag) {
        DialogTopic topic = new DialogTopic();
        topic.npcName = tag.getString("npcName");
        topic.name = tag.getString("name");
        topic.script = tag.getString("script");
        topic.dialogLine = tag.getString("dialogLine");
        NBTTagList conditionsList = (NBTTagList) tag.getTag("conditions");
        for (int i = 0; i < conditionsList.tagCount(); i++) {
            topic.conditions.add(Condition.loadFromNBT(conditionsList.getCompoundTagAt(i)));
        }
        return topic;
    }
}
