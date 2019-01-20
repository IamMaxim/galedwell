package ru.iammaxim.tesitems.Dialogs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maxim on 8/5/16 at 8:02 PM.
 */
public class Dialog {
    public HashMap<String, DialogTopic> topics = new HashMap<>();

    public static Dialog createDialogForPlayer(NPC npc, EntityPlayer player) {
        Dialog dialog = new Dialog();
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        cap.getQuests().forEach((id, quest) -> {
            ArrayList<DialogTopic> topics = quest.getCurrentStage().topics;
            topics.forEach(t -> {
                t = t.copy(); // don't change original topic
                t.script = ""; // don't send script to client
                t.attachTo(quest.quest);
                if (t.npcName.equals(npc.name)) dialog.addTopic(t.name, t);
            });
        });
        npc.getFactions().forEach(f ->
                f.topics.forEach(t -> {
                    t = t.copy(); // don't change original topic
                    t.script = ""; // don't send script to client
                    dialog.addTopic(t.name, t);
                }));
        cap.setLatestDialog(dialog);
        return dialog;
    }

    public static Dialog loadFromNBT(NBTTagCompound tag) {
        Dialog dialog = new Dialog();
        NBTTagList list = (NBTTagList) tag.getTag("topics");
        for (int i = 0; i < list.tagCount(); i++) {
            DialogTopic topic = DialogTopic.readFromNBT(list.getCompoundTagAt(i));
            dialog.topics.put(topic.name, topic);
        }
        return dialog;
    }

    public void addTopic(String name, DialogTopic topic) {
        topics.put(name, topic);
    }

    public NBTTagCompound saveToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        topics.forEach((name, topic) -> list.appendTag(topic.writeToNBT()));
        tag.setTag("topics", list);
        return tag;
    }
}
