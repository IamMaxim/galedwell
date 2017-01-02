package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.Dialogs.DialogTopic;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HorizontalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
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
            ElementBase element = getTopicElement(topics, topic, false);
            elements.put(element, topic);
            topics.add(element);
        }));
        root2.add(new HorizontalDivider(root2));
        HorizontalLayout root3 = new HorizontalLayout(root2).center(true);
        root2.add(root3);
        root3.add(new Button(root3).setText("Save").setOnClick(b -> {
            mc.displayGuiScreen(new GuiAlertDialog("Faction saved", this));

            //check for empty topics
            Iterator<ElementBase> it = elements.keySet().iterator();
            while (it.hasNext()) {
                ElementBase e = it.next();
                DialogTopic t = elements.get(e);
                System.out.println("checking " + t.name + " " + t.dialogLine);
                if (t.name.isEmpty() || t.dialogLine.isEmpty()) {
                    System.out.println("removing topic: " + t.name + " with dialog line: " + t.dialogLine);
                    elements.remove(e);
                    topics.remove(e);
                    root.doLayout();
                }
            }

            finalFaction.topics.clear();
            elements.forEach((e, t) -> finalFaction.topics.add(t));

            System.out.println("gonna save " + finalFaction.writeToNBT().toString());

            TESItems.networkWrapper.sendToServer(new MessageFaction(finalFaction));
        }));
        root3.add(new Button(root3).setText("Remove").setOnClick(b -> {
            mc.displayGuiScreen(new GuiConfirmationDialog("Are you sure you want to remove faction " + finalFaction.name + "?", this, () -> {
                //check if this faction exists or newly created
                if (finalFaction.id != -1)
                    TESItems.networkWrapper.sendToServer(new MessageFactionRemove(finalFaction.id));
                mc.displayGuiScreen(new GuiFactionList());
            }));
        }));
        root3.add(new Button(root3).setText("Back").setOnClick(b -> mc.displayGuiScreen(new GuiFactionList())));

        faction.topics.forEach(t -> {
            ElementBase e = getTopicElement(topics, t, false);
            elements.put(e, t);
            topics.add(e);
        });

        root.doLayout();
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

    /*private class GuiFaction {
        public boolean opened = false;
        public ElementBase root, element;
        public Faction faction;

        public GuiFaction(ElementBase root) {

        }

        public GuiFaction(ElementBase root, Faction faction) {
            this.root = root;
            this.faction = cloneFaction(faction);
        }

        private ElementBase getElement(boolean _opened) {
            if (_opened) {
                VerticalLayout layout = new VerticalLayout(root);
                layout.add(new Text(layout, "[Edit: " + faction.name + "]") {
                    @Override
                    public void click(int relativeX, int relativeY) {
                        opened = false;
                        element = getElement(false);
                        ((LayoutBase) root).doLayout();
                    }
                });
                layout.add(new TextField(layout).setHint("Name").setText(faction.name).setOnType(tf -> faction.name = tf.getText()));
                layout.add(new Text(layout, "Faction ID: " + faction.id));
                layout.add(new Text(layout, "Topics"));
                layout.add(new HorizontalDivider(layout));
                VerticalLayout topicsLayout = new VerticalLayout(layout);
                layout.add(topicsLayout);
                layout.add(new HorizontalDivider(layout));
                layout.add(new Button(layout).setText("Add topic").setOnClick(b -> topicsLayout.add(getTopicElement(layout, new DialogTopic()))));

                //fill topics layout with actual topics
                faction.topics.forEach(t -> {
                    topicsLayout.add(getTopicElement(topicsLayout, t));
                });

                return layout;
            } else {
                return new Text(root, faction.name) {
                    @Override
                    public void click(int relativeX, int relativeY) {
                        opened = true;
                        element = getElement(true);
                        ((LayoutBase) root).doLayout();
                    }

                    @Override
                    public void draw(int mouseX, int mouseY) {
                        super.draw(mouseX, mouseY);
                    }
                };
            }
        }
    }*/

    private ElementBase getTopicElement(ElementBase _parent, DialogTopic topic, boolean opened) {
        if (opened) {
            VerticalLayout layout = new VerticalLayout(_parent);
            layout.add(new HorizontalDivider(layout));
            layout.add(new Text(layout, "Close") {
                @Override
                public void click(int relativeX, int relativeY) {
                    VerticalLayout p = ((VerticalLayout)_parent);
                    p.getElements().set(p.getElements().indexOf(layout), getTopicElement(_parent, topic, false));
                    ((LayoutBase)getRoot()).doLayout();
                }
            });
            layout.add(new TextField(layout).setHint("Name").setText(topic.name).setOnType(tf -> topic.name = tf.getText()));
            layout.add(new TextField(layout).setHint("Dialog line").setText(topic.dialogLine).setOnType(tf -> topic.dialogLine = tf.getText()));
            layout.add(new Button(layout).setText("Remove").setOnClick(b -> {
                mc.displayGuiScreen(new GuiConfirmationDialog("Are you sure you want to remove topic " + topic.name + "?", this, () -> {
                    ((VerticalLayout) _parent).remove(layout);
                    elements.remove(layout);
                }));
            }));
            layout.add(new HorizontalDivider(layout));
            return layout;
        } else {
            return new Text(root, topic.name) {
                @Override
                public void click(int relativeX, int relativeY) {
                    VerticalLayout p = ((VerticalLayout)_parent);
                    p.getElements().set(p.getElements().indexOf(this), getTopicElement(_parent, topic, true));
                    ((LayoutBase)getRoot()).doLayout();
                }
            };
        }
    }
}
