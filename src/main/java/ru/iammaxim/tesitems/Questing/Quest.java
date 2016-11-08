package ru.iammaxim.tesitems.Questing;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maxim on 20.07.2016.
 */
public class Quest {
    public int id;
    public List<QuestStage> stages = new ArrayList<>();
    public String name;

    public Quest() {}

    public Quest(String name) {
        id = QuestManager.genID();
        this.name = name;
    }

    @Override
    public String toString() {
        return "name: " + name + ", stages: [" + stages.stream().map(QuestStage::toString).collect(Collectors.joining(", ")) + "]";
    }
}