package ru.iammaxim.tesitems.Questing;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestInstance {
    public QuestStatus status;
    public Quest quest;
    public int stage;

    public QuestInstance(Quest quest, QuestStatus status, int stage) {
        this.quest = quest;
        this.status = status;
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "status: " + status + " quest: " + quest + " stage: " + stage;
    }
}
