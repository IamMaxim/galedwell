package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.Dialogs.DialogTopic;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.GUI.Elements.*;

import java.util.ArrayList;

/**
 * Created by maxim on 01.01.2017.
 */
public class GuiFactionEditor extends Screen {
    public VerticalLayout factions;
    public ArrayList<GuiFaction> factionList;

    public GuiFactionEditor(Faction faction) {
        ScreenCenteredLayout root1 = new ScreenCenteredLayout(root);
        contentLayout.setElement(root1);
        VerticalLayout root2 = new VerticalLayout(root1);
        root1.setElement(root2);
        root2.add(new Text(root2, "Faction editor").center(true));
        root2.add(new TextField(root2).setHint("Name").setText(faction.name).setOnType(tf -> faction.name = tf.getText()));
        root2.add(new HorizontalDivider(root2));
        ScrollableLayout root3 = new ScrollableLayout(root2);
        root3.setElement(factions = new VerticalLayout(root3));
        root2.add(root3);
        root2.add(new HorizontalDivider(root2));
        root2.add(new Button(root2, "Add faction"));

        GuiFaction guiFaction;
        if (faction == null)
            guiFaction = new GuiFaction(root3);
        else
            guiFaction = new GuiFaction(root3, faction);

        root3.setElement(guiFaction.getElement(false));
        root.doLayout();
    }

    private class GuiFaction {
        public boolean opened = false;
        public ElementBase root, element;
        public Faction faction;

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
                layout.add(new Button(layout).setText("Add topic").center(true).setOnClick(b -> topicsLayout.add(getTopicElement(layout, new DialogTopic()))));

                //fill topics layout with actual topics
                faction.topics.forEach(t -> {
                    topicsLayout.add(getTopicElement(topicsLayout, t));
                });

                return layout;
            } else {
                return new Text(root, "[Edit: " + faction.name + "]") {
                    @Override
                    public void click(int relativeX, int relativeY) {
                        opened = true;
                        element = getElement(true);
                        ((LayoutBase) root).doLayout();
                    }
                };
            }
        }

        private ElementBase getTopicElement(ElementBase parent, DialogTopic topic) {
            VerticalLayout layout = new VerticalLayout(parent);
            layout.add(new TextField(layout).setHint("Name").setText(topic.name).setOnType(tf -> topic.name = tf.getText()));
            layout.add(new TextField(layout).setHint("Dialog line").setText(topic.dialogLine).setOnType(tf -> topic.dialogLine = tf.getText()));
            layout.add(new Button(layout).setText("Delete").setOnClick(b -> ((VerticalLayout) parent).remove(layout)));
            return layout;
        }

    }
}
