package ru.iammaxim.tesitems.Questing;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestInstance {
    public QuestStatus status;
    public Quest quest;
    public int stage, quest_id;

    public QuestInstance(Quest quest, QuestStatus status, int stage) {
        this.quest = quest;
        this.status = status;
        this.stage = stage;
        this.quest_id = quest.id;
    }

    public QuestStage getCurrentStage() {
        return quest.stages.get(stage);
    }

    @Override
    public String toString() {
        return "status: " + status + " quest: " + quest + " stage: " + stage;
    }
}
