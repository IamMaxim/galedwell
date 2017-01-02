package ru.iammaxim.tesitems.Dialogs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maxim on 8/5/16 at 8:02 PM.
 */
public class Dialog {
    public HashMap<String, DialogTopic> topics = new HashMap<>();

    public static Dialog createDialogForPlayer(EntityNPC npc, EntityPlayer player) {
        System.out.println("creating dialog");
        Dialog dialog = new Dialog();
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        cap.getQuests().forEach((id, quest) -> {
            ArrayList<DialogTopic> topics = quest.getCurrentStage().topics;
            topics.forEach(t -> {
                if (t.npcName.equals(npc.getName())) dialog.addTopic(t.name, t);
            });
        });
        npc.getFactions().forEach(faction -> {
            System.out.println("processing faction " + faction.name);
            faction.topics.forEach(t -> {
                System.out.println("adding topic " + t.name);
                dialog.addTopic(t.name, t);
            });
        });
        return dialog;
    }

    public void addTopic(String name, DialogTopic topic) {
        topics.put(name, topic);
    }

    public NBTTagCompound saveToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        topics.forEach((name,topic) -> list.appendTag(topic.writeToNBT()));
        tag.setTag("topics", list);
        System.out.println("saved to " + tag.toString());
        return tag;
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
}
