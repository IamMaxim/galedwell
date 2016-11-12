package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.elements.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.elements.Text;
import ru.iammaxim.tesitems.GUI.elements.TextField;
import ru.iammaxim.tesitems.GUI.elements.VerticalLayout;
import ru.iammaxim.tesitems.Questing.Quest;

/**
 * Created by maxim on 11/7/16 at 4:14 PM.
 */
public class GuiQuestEditor extends Screen {
    private VerticalLayout layout;
    private Quest quest;

    public GuiQuestEditor(Quest quest) {
        this.quest = quest;

        ScrollableLayout scrollableLayout = new ScrollableLayout(contentLayout);
        contentLayout.setElement(scrollableLayout);
        layout = new VerticalLayout(scrollableLayout);
        scrollableLayout.setElement(layout);

        layout.add(new Text(layout, "Quest ID:" + quest.id).setColor(0x60ffffff));
        TextField questName = new TextField(layout).setHint("Quest name").setText(quest.name);
        layout.add(questName);
    }
}
