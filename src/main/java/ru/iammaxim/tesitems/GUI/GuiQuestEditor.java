package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.Networking.MessageQuest;
import ru.iammaxim.tesitems.Player.AdminTemporaryStorage;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestStage;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 11/7/16 at 4:14 PM.
 */
public class GuiQuestEditor extends Screen {
    private VerticalLayout layout, stages;
    private Quest quest;

    public GuiQuestEditor() {
        this.quest = AdminTemporaryStorage.lastEditedQuest.copy();

        ScrollableLayout scrollableLayout = new ScrollableLayout();
        contentLayout.setElement(scrollableLayout);
        layout = new VerticalLayout();
        scrollableLayout.setElement(layout);

        layout.add(new HeaderLayout("Quest editor"));
        layout.add(new Text("Quest ID: " + quest.id));
        TextField questName = new TextField().setHint("Quest name").setText(quest.name);
        layout.add(questName);
        layout.add(new HorizontalDivider());

        layout.add(new HeaderLayout("Quest stages"));
        layout.add(new WrapFrameLayout().setElement(
                (stages = new VerticalLayout()).setWidthOverride(ElementBase.FILL))
                .setWidthOverride(ElementBase.FILL));
        layout.add(new Button("Add stage").center(true).setWidthOverride(ElementBase.FILL).setOnClick(e -> {
            stages.add(getStageElement(new QuestStage()));
        }));

        layout.add(new HorizontalDivider());
        layout.add(new HorizontalLayout().add(new Button("Save").setOnClick(e -> {
            TESItems.networkWrapper.sendToServer(new MessageQuest(quest));
        })).add(new Button("Back").setOnClick(e -> {
            ScreenStack.addScreen(new GuiQuestList());
        })).center(true).setWidthOverride(ElementBase.FILL));
    }

    private ElementBase getStageElement(QuestStage stage) {
        Text name = new Text("ID " + stage.id).setColor(ResManager.CLICKABLE_TEXT_COLOR);
        ElementBase layout = new DoubleStateFrameLayout().setFirstState(name)
                .setSecondState(new WrapFrameLayout().setElement(new VerticalLayout().add(new TextField().setHint("ID").setOnType(e -> {
                    try {
                        int id = Integer.parseInt(e.getText());
                        name.setText("ID " + id);
                        stage.id = id;
                    } catch (Exception ex) {
                    }
                })).setWidthOverride(ElementBase.FILL)));

        return layout;
    }

    private class StagesEditor extends Screen {

    }
}
