package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.Dialogs.DialogTopic;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.Networking.MessageFaction;
import ru.iammaxim.tesitems.Networking.MessageFactionRemove;
import ru.iammaxim.tesitems.Player.AdminTemporaryStorage;
import ru.iammaxim.tesitems.TESItems;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by maxim on 01.01.2017.
 */
public class GuiFactionEditor extends Screen {
    public VerticalLayout topics;
    public HashMap<ElementBase, DialogTopic> elements = new HashMap<>();

    public GuiFactionEditor() {
        Faction finalFaction = cloneFaction(AdminTemporaryStorage.lastEditedFaction);
        contentLayout.setElement(new ScrollableLayout()
                /*.setHeight((int) (res.getScaledHeight() * 0.8))*/
                .setElement(new VerticalLayout()
                        .add(new HeaderLayout("Faction editor"))
                        .add(new TextField().setHint("Name").setText(finalFaction.name).setOnType(tf -> finalFaction.name = tf.getText()).setWidthOverride(ElementBase.FILL))
                        .add(new Text("id: " + finalFaction.id))
                        .add(new HorizontalDivider())
                        .add(new HeaderLayout("Topics"))
                        .add(new WrapFrameLayout().setElement(topics = new VerticalLayout()).setWidthOverride(ElementBase.FILL))
                        .add(new Button("Add topic").setOnClick(b -> {
                            DialogTopic topic = new DialogTopic();
                            topic.name = "New topic";
                            ElementBase element = getTopicElement(topics, topic);
                            elements.put(element, topic);
                            topics.add(element);
                        }).setWidthOverride(ElementBase.FILL))
                        .add(new HorizontalDivider())
                        .add(new HorizontalLayout().center(true)
                                .add(new Button().setText("Save").setOnClick(b -> {
                                    mc.displayGuiScreen(new GuiAlertDialog("Faction saved", this));

                                    //check for empty topics
                                    checkForEmptyTopics();

                                    finalFaction.topics.clear();
                                    elements.forEach((e, t) -> finalFaction.topics.add(t));

                                    System.out.println("gonna save " + finalFaction.writeToNBT().toString());

                                    TESItems.networkWrapper.sendToServer(new MessageFaction(finalFaction));
                                }))
                                .add(new Button().setText("Remove").setOnClick(b ->
                                        mc.displayGuiScreen(new GuiConfirmationDialog("Are you sure you want to remove faction " + finalFaction.name + "?", this,
                                                () -> {
                                                    //check if this faction exists or newly created
                                                    if (finalFaction.id != -1)
                                                        TESItems.networkWrapper.sendToServer(new MessageFactionRemove(finalFaction.id));
                                                    mc.displayGuiScreen(new GuiFactionList());
                                                }))))
                                .add(new Button().setText("Back").setOnClick(b -> mc.displayGuiScreen(new GuiFactionList())))
                        )
                ));

        finalFaction.topics.forEach(t -> {
            ElementBase e = getTopicElement(topics, t).setWidthOverride(ElementBase.FILL);
            elements.put(e, t);
            topics.add(e);
        });

        root.doLayout();
    }

    private void checkForEmptyTopics() {
        Iterator<ElementBase> it = elements.keySet().iterator();
        while (it.hasNext()) {
            ElementBase e = it.next();
            DialogTopic t = elements.get(e);
            if (t.name.isEmpty() || t.dialogLine.isEmpty()) {
                elements.remove(e);
                topics.remove(e);
                root.doLayout();
                //do it again, because iterator stops working after removing element
                checkForEmptyTopics();
            }
        }
    }

    private Faction cloneFaction(Faction orig) {
        Faction dest = new Faction(orig.name);
        dest.id = orig.id;
        orig.topics.forEach(t -> {
            DialogTopic destTopic = new DialogTopic();
            destTopic.name = t.name;
            destTopic.script = t.script;
            destTopic.dialogLine = t.dialogLine;
            destTopic.npcName = t.npcName;
            t.conditions.forEach(c -> destTopic.conditions.add(c.copy()));
            dest.topics.add(destTopic);
        });
        return dest;
    }

    private ElementBase getTopicElement(ElementBase containerLayout, DialogTopic topic) {
        DoubleStateFrameLayout layout = new DoubleStateFrameLayout();

        Text closed = new Text(topic.name) {
            @Override
            public void click(int relativeX, int relativeY) {
                //select opened state
                layout.selectSecond();
                ((LayoutBase) getRoot()).doLayout();
            }
        };

        ElementBase opened = new WrapFrameLayout()
                .setElement(new VerticalLayout()
                        .add(new Text("Close").setOnClick(e -> {
                            //select closed state
                            layout.selectFirst();
                            ((LayoutBase) e.getRoot()).doLayout();
                        }))
                        .add(new TextField()
                                .setHint("Name")
                                .setText(topic.name)
                                .setOnType(tf -> {
                                    topic.name = tf.getText();
                                    closed.setText(tf.getText());
                                })
                                .setWidthOverride(ElementBase.FILL))
                        .add(new TextField()
                                .setHint("Dialog line")
                                .setText(topic.dialogLine)
                                .setOnType(tf -> topic.dialogLine = tf.getText())
                                .setWidthOverride(ElementBase.FILL))
                        .add(new TextField()
                                .setHint("Script")
                                .setText(topic.script)
                                .setFontRenderer(TESItems.ClientThings.monospaceFontRenderer)
                                .setOnType(tf -> topic.script = tf.getText())
                                .setWidthOverride(ElementBase.FILL))
                        .add(new Button()
                                .setText("Remove")
                                .setOnClick(b -> mc.displayGuiScreen(
                                        new GuiConfirmationDialog("Are you sure you want to remove topic " + topic.name + "?", this,
                                                () -> {
                                                    ((VerticalLayout) containerLayout).remove(layout);
                                                    elements.remove(layout);
                                                }))))
                );

        layout.setFirstState(closed).setSecondState(opened).selectFirst();
        layout.setWidthOverride(ElementBase.FILL);
        return layout;
    }
}
