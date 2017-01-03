package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.Dialogs.DialogTopic;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Networking.MessageDialogSelectTopic;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.io.IOException;

/**
 * Created by maxim on 7/24/16.
 */
public class GuiNPCDialog extends Screen {
    private EntityPlayer player;
    private IPlayerAttributesCapability cap;
    private EntityNPC npc;
    private String history = "History text.\nSome more.\nAnd more.\ndksfglksdjfgklsdfgkjsdklgjsdlkfgjsdklgsdklfgsdkfgdskfgdklfgjdsklgdklsgjdklsgjkldklfjgkldsjglkdsjglkdsjgkldsjglkdsgjdlksgjdsklgjdslkgjdsklgjdslkgjdsklgjdsfklgfdjsgklfdsjklsjgfsdklgjfsdklgjfdslkgjskldfjglkdsfgjkldsfg";
    private Text historyElement;
    private ScrollableLayout leftElement;
    private boolean updated = true;

    public VerticalLayout topics;
    public ScrollableLayout topicsScrollableLayout;

    public GuiNPCDialog() {
        super();
        player = mc.thePlayer;
        cap = TESItems.getCapability(player);
        npc = cap.getLatestNPC();

        root = new ScreenCenteredLayout(null) {
            @Override
            public void doLayout() {
                ScaledResolution res = new ScaledResolution(mc);
                int width = getWidth();
                int height = getHeight();
                setBounds(
                        (res.getScaledWidth() - width)/2,
                        (res.getScaledHeight() - height)/2,
                        (res.getScaledWidth() + width)/2,
                        (res.getScaledHeight() + height)/2);
                element.setBounds(left + padding, top + padding, right - padding, bottom - padding);
                ((LayoutBase) element).doLayout();
            }

            @Override
            public void checkClick(int mouseX, int mouseY) {
                if (updated)
                    super.checkClick(mouseX, mouseY);
            }
        };
        contentLayout = new FancyFrameLayout(root);
        root.setElement(contentLayout);
        DialogWindowLayout root1 = new DialogWindowLayout(contentLayout);
        leftElement = new ScrollableLayout(root1) {
            @Override
            public void doLayout() {
                System.out.println("leftElement doLayout()");
                super.doLayout();
            }

            @Override
            public void scrollToBottom() {
                int h = element.getHeight();
                scroll = h - height;
                if (scroll < 0) scroll = 0;
                System.out.println(h + " " + height + " " + scroll);
                ((VerticalLayout) element).setTop(top + padding - scroll);
                ((LayoutBase)element).doLayout();
            }
        };
        VerticalLayout leftLayout = new VerticalLayout(leftElement) {
            @Override
            public void doLayout() {
                System.out.println("leftLayout doLayout()");
                super.doLayout();
            }

            @Override
            public int getHeight() {
                int height = 0;
                height += ((Text)elements.get(0)).getNonDirtyHeight();
                height += 2 * padding + 2 * marginV;
                return height;
            }
        };
        historyElement = new Text(leftLayout) {
            @Override
            public void draw(int mouseX, int mouseY) {
                 int x = left + leftPadding;
                int y = top;
                for (String str : strs) {
                    if (center) {
                        fontRenderer.drawString(str, x + (width - textWidth) / 2, y, color);
                        y += lineHeight + lineSpacing;
                    } else {
                        fontRenderer.drawString(str, x, y, color);
                        y += lineHeight + lineSpacing;
                    }
                }
            }
        };
        leftLayout.add(historyElement);
        leftElement.setElement(leftLayout);

        //todo: remove
        historyElement.setText(history);

        HorizontalLayout rightElement = new HorizontalLayout(root1);
        rightElement.setSpacing(0);
        root1.setLeftElement(leftElement);
        root1.setRightElement(rightElement);
        rightElement.add(new VerticalDivider(rightElement));
        VerticalLayout rightLayout = new VerticalLayout(rightElement) {
            @Override
            public int getWidth() {
                return parent.width() - 2 * padding - 2 * marginH;
            }
        };
        rightElement.add(rightLayout);
        rightLayout.add(new Text(rightLayout, npc.getName()).setLeftPadding(4)).add(new HorizontalDivider(rightLayout));
        topics = new VerticalLayout(rightLayout);
        topics.setHorizontalMargin(4);
        topicsScrollableLayout = new ScrollableLayout(rightElement);
        topicsScrollableLayout.setElement(topics);
        rightLayout.add(topicsScrollableLayout);
        contentLayout.setElement(root1);

        cap.getDialog().topics.forEach((name, t) -> {
            System.out.println("adding topic " + name);
            topics.add(buildTopicElement(topics, t));
        });

        contentLayout.doLayout();
        leftElement.setHeight(root1.getHeight());
        topicsScrollableLayout.setHeight(rightElement.getHeight());
        contentLayout.doLayout();
    }

    public Text buildTopicElement(ElementBase root, DialogTopic topic) {
        Text text = new Text(root, topic.name) {
            @Override
            public void click(int relativeX, int relativeY) {
                System.out.println("clicked topic");
                historyAppend(topic.name);
                historyAppend(topic.dialogLine);
                ((LayoutBase)root).doLayout();
                leftElement.scrollToBottom();
                TESItems.networkWrapper.sendToServer(new MessageDialogSelectTopic(topic));
            }
        };
        return text;
    }

    public void historyAppend(String appendStr) {
        history += "\n\n" + appendStr;
        historyElement.setText(history);
    }

    public void setUpdated() {
        updated = true;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    private class DialogWindowLayout extends LayoutBase {
        private static final int rightElementWidth = 100;
        private ScaledResolution res;

        private LayoutBase leftElement, rightElement;

        public DialogWindowLayout(ElementBase parent) {
            super(parent);
            res = new ScaledResolution(mc);
        }

        @Override
        public void checkClick(int mouseX, int mouseY) {
            leftElement.checkClick(mouseX, mouseY);
            rightElement.checkClick(mouseX, mouseY);
        }

        @Override
        public int getWidth() {
            return (int) (res.getScaledWidth() * 0.8f);
        }

        @Override
        public int getHeight() {
            return (int) (res.getScaledHeight() * 0.7f);
        }

        @Override
        public void doLayout() {
            int x = left;
            int y = top;
            int w = (int) (res.getScaledWidth() * 0.8f);
            int h = getHeight();
            int x2 = x + w - rightElementWidth;
            leftElement.setBounds(x, y, x2, y + h);
            rightElement.setBounds(x2, y, x + w, y + h);
            leftElement.doLayout();
            rightElement.doLayout();
        }

        @Override
        public void draw(int mouseX, int mouseY) {
            leftElement.draw(mouseX, mouseY);
            rightElement.draw(mouseX, mouseY);
        }

        public void setLeftElement(LayoutBase leftElement) {
            this.leftElement = leftElement;
        }

        public void setRightElement(LayoutBase rightElement) {
            this.rightElement = rightElement;
        }
    }
}
