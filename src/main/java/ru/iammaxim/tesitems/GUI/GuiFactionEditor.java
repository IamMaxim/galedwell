package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.renderer.Tessellator;
import ru.iammaxim.tesitems.Dialogs.DialogTopic;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.Networking.MessageFaction;
import ru.iammaxim.tesitems.Networking.MessageFactionRemove;
import ru.iammaxim.tesitems.TESItems;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by maxim on 01.01.2017.
 */
public class GuiFactionEditor extends Screen {
    public VerticalLayout topics;
    public HashMap<ElementBase, DialogTopic> elements = new HashMap<>();

    public GuiFactionEditor(Faction faction) {
        Faction finalFaction = cloneFaction(faction);
        ScrollableLayout root1 = new ScrollableLayout(contentLayout);
        root1.setHeight((int) (res.getScaledHeight() * 0.8));
        contentLayout.setElement(root1);
        VerticalLayout root2 = new VerticalLayout(root1);
        root1.setElement(root2);
//        root2.add(new Text(root2, "Faction editor").center(true));
        root2.add(new HeaderLayout(root2, "Faction editor"));
        root2.add(new TextField(root2).setHint("Name").setText(finalFaction.name).setOnType(tf -> finalFaction.name = tf.getText()));
        root2.add(new Text(root2, "id: " + finalFaction.id));
        root2.add(new HorizontalDivider(root2));
        root2.add(new HeaderLayout(root2, "Topics"));
        root2.add(topics = new VerticalLayout(root2));
        root2.add(new Button(root2, "Add topic").setOnClick(b -> {
            DialogTopic topic = new DialogTopic();
            topic.name = "New topic";
            ElementBase element = getTopicElement(topics, topic);
            elements.put(element, topic);
            topics.add(element);
        }));
        root2.add(new HorizontalDivider(root2));
        HorizontalLayout root3 = new HorizontalLayout(root2).center(true);
        root2.add(root3);
        root3.add(new Button(root3).setText("Save").setOnClick(b -> {
            mc.displayGuiScreen(new GuiAlertDialog("Faction saved", this));

            //check for empty topics
            checkForEmptyTopics();

            finalFaction.topics.clear();
            elements.forEach((e, t) -> {
                System.out.println("adding topic " + t.name);
                finalFaction.topics.add(t);
            });

            System.out.println("gonna save " + finalFaction.writeToNBT().toString());

            TESItems.networkWrapper.sendToServer(new MessageFaction(finalFaction));
        }));
        root3.add(new Button(root3).setText("Remove").setOnClick(b ->
                mc.displayGuiScreen(new GuiConfirmationDialog("Are you sure you want to remove faction " + finalFaction.name + "?", this,
                        () -> {
                            //check if this faction exists or newly created
                            if (finalFaction.id != -1)
                                TESItems.networkWrapper.sendToServer(new MessageFactionRemove(finalFaction.id));
                            mc.displayGuiScreen(new GuiFactionList());
                        }))));
        root3.add(new Button(root3).setText("Back").setOnClick(b -> mc.displayGuiScreen(new GuiFactionList())));

        faction.topics.forEach(t -> {
            ElementBase e = getTopicElement(topics, t);
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
            t.conditions.forEach(c -> destTopic.conditions.add(c.clone()));
        });
        return dest;
    }

    private ElementBase getTopicElement(ElementBase _parent, DialogTopic topic) {
        DoubleStateFrameLayout layout = new DoubleStateFrameLayout(_parent);

        Text closed = new Text(root, topic.name) {
            @Override
            public void click(int relativeX, int relativeY) {
                //select opened state
                layout.selectSecond();
                ((LayoutBase) getRoot()).doLayout();
            }
        };

        VerticalLayout opened = new VerticalLayout(_parent) {
            @Override
            public void draw(int mouseX, int mouseY) {
                drawColoredRect(Tessellator.getInstance(), left, top, right, bottom, 0x33000000);
                super.draw(mouseX, mouseY);
            }
        };
        opened.add(new HorizontalDivider(opened));
        opened.add(new Text(opened, "Close") {
            @Override
            public void click(int relativeX, int relativeY) {
                //select closed state
                layout.selectFirst();
                ((LayoutBase) getRoot()).doLayout();
            }
        });
        opened.add(new TextField(opened).setHint("Name").setText(topic.name).setOnType(tf -> {
            topic.name = tf.getText();
            closed.setText(tf.getText());
        }));
        opened.add(new TextField(opened).setHint("Dialog line").setText(topic.dialogLine).setOnType(tf -> topic.dialogLine = tf.getText()));
        opened.add(new TextField(opened).setHint("Script").setText(topic.script).setOnType(tf -> topic.script = tf.getText()));
        opened.add(new Button(opened).setText("Remove").setOnClick(b -> mc.displayGuiScreen(new GuiConfirmationDialog("Are you sure you want to remove topic " + topic.name + "?", this,
                () -> {
                    ((VerticalLayout) _parent).remove(opened);
                    elements.remove(opened);
                }))));
        opened.add(new HorizontalDivider(opened));

        layout.setFirstState(closed).setSecondState(opened).selectFirst();

        return layout;
    }
}
