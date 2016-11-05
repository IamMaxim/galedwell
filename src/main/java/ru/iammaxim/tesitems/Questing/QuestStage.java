package ru.iammaxim.tesitems.Questing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maxim on 20.07.2016.
 */
public class QuestStage {
    public String journalLine;
    public List<QuestTarget> targets = new ArrayList<>();

    @Override
    public String toString() {
        return "journalLine: " + journalLine +
                " targets: [" + targets.stream().map(QuestTarget::toString).collect(Collectors.joining(", ")) + "]";
    }
}
