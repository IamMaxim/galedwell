package ru.iammaxim.tesitems.GUI;

import com.google.common.collect.HashBiMap;
import net.minecraft.client.Minecraft;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.HorizontalDivider;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.DoubleStateFrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.Player.AdminTemporaryStorage;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestManager;

/**
 * Created by Maxim on 20.07.2016.
 */
public class GuiQuestList extends Screen {
    VerticalLayout quests;
    HashBiMap<ElementBase, Quest> questMap = HashBiMap.create();

    public GuiQuestList() {
        contentLayout.setElement(
                new ScrollableLayout().setElement(
                        new VerticalLayout()
                                .add(new HeaderLayout("Quests"))
                                .add(new HorizontalDivider())
                                .add(quests = new VerticalLayout())
                                .add(new HorizontalDivider())
                                .add(new Button("New quest").center(true).setOnClick(e -> {
                                    AdminTemporaryStorage.lastEditedQuest = new Quest();
                                    ScreenStack.addScreen(new GuiQuestEditor());
                                }))));

        QuestManager.questList.forEach((id, quest) -> addQuest(quest));
    }

    public void addQuest(Quest quest) {
        ElementBase element = new DoubleStateFrameLayout()
                .setFirstState(new Text(quest.name).setColor(0xFF0066CC).setOnClick(e -> {

                }));
        quests.add(element);
        questMap.put(element, quest);
    }
}
