package ru.iammaxim.tesitems.Questing;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 20.07.2016.
 */
public class Quest {
    public int id;
    public List<QuestStage> stages = new ArrayList<>();
    public String name;
    public List<ItemStack> itemsReward = new ArrayList<>();
    public int goldReward = 0;

    public Quest() {}

    public Quest(String name) {
        id = QuestManager.genID();
        this.name = name;
    }
}
