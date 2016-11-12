package ru.iammaxim.tesitems.Questing;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.Dialogs.DialogTopic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestStage {
    public String journalLine;
    public List<QuestTarget> targets = new ArrayList<>();
    public ArrayList<DialogTopic> topics = new ArrayList<>();

    public NBTTagCompound saveToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("journalLine", journalLine);
        NBTTagList targetsList = new NBTTagList();
        targets.forEach(t -> targetsList.appendTag(t.saveToNBT()));
        tag.setTag("targets", targetsList);
        NBTTagList topicsList = new NBTTagList();
        topics.forEach(t -> topicsList.appendTag(t.saveToNBT()));
        tag.setTag("topics", topicsList);
        return tag;
    }

    public static QuestStage loadFromNBT(NBTTagCompound tag) {
        QuestStage stage = new QuestStage();
        stage.journalLine = tag.getString("journalLine");
        NBTTagList targetsList = (NBTTagList) tag.getTag("targets");
        for (int i = 0; i < targetsList.tagCount(); i++) {
            stage.targets.add(QuestTarget.getTargetFromNBT(targetsList.getCompoundTagAt(i)));
        }
        NBTTagList topicsList = (NBTTagList) tag.getTag("topics");
        for (int i = 0; i < targetsList.tagCount(); i++) {
            stage.topics.add(DialogTopic.loadFromNBT(topicsList.getCompoundTagAt(i)));
        }
        return stage;
    }

    @Override
    public String toString() {
        return "journalLine: " + journalLine +
                " targets: [" + targets.stream().map(QuestTarget::toString).collect(Collectors.joining(", ")) + "]";
    }
}
