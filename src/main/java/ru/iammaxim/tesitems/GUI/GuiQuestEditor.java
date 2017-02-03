package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.GUI.Elements.TextField;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.Player.AdminTemporaryStorage;
import ru.iammaxim.tesitems.Questing.Quest;

/**
 * Created by maxim on 11/7/16 at 4:14 PM.
 */
public class GuiQuestEditor extends Screen {
    private VerticalLayout layout;
    private Quest quest;

    public GuiQuestEditor() {
        this.quest = AdminTemporaryStorage.lastEditedQuest;

        ScrollableLayout scrollableLayout = new ScrollableLayout();
        contentLayout.setElement(scrollableLayout);
        layout = new VerticalLayout();
        scrollableLayout.setElement(layout);

        layout.add(new Text("Quest ID: " + quest.id).setColor(0x60ffffff));
        TextField questName = new TextField().setHint("Quest name").setText(quest.name);
        layout.add(questName);
    }
}
