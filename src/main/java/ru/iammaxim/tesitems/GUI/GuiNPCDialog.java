package ru.iammaxim.tesitems.GUI;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Networking.MessageDialogSelectTopic;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/24/16.
 */
public class GuiNPCDialog extends Screen {
    public VerticalLayout topics;
    private boolean updated = true;

    public GuiNPCDialog() {
        EntityPlayer player = mc.thePlayer;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        NPC npc = cap.getLatestNPC();

        ScrollableLayout historyScrollableLayout;
        VerticalLayout historyLayout;

        contentLayout.setElement(new TwoPaneLayout()
                        .setLeftElement(historyScrollableLayout = (ScrollableLayout) new ScrollableLayout()
                                .setElement(historyLayout = new VerticalLayout()))
                        .setRightElement(new HorizontalLayout()
                                .add(new VerticalDivider())
                                .add(new VerticalLayout()
                                        .add(new Text(npc.name))
                                        .add(new HorizontalDivider())
                                        .add(new ScrollableLayout()
                                                .setMinHeight(1000000)
                                                .setElement(topics = (VerticalLayout) new VerticalLayout()
                                                        .setPaddingLeft(0))
                                                .setPaddingTop(0)
                                                .setWidthOverride(ElementBase.FILL))
                                        .setVerticalMargin(4))
                                .setSpacing(0))
                        .setRightWidth(160)
/*                .setMinHeight(200)*/);

        cap.getLatestDialog().topics.forEach((name, topic) -> {
            System.out.println("adding topic");

            topics.add(new Text(topic.name).setOnClick(e -> {
                if (!updated) {
                    System.out.println("not updated yet");
                    return;
                }

                System.out.println("updating...");

                historyLayout.add(new Text(topic.name).setColor(0xFF0066CC).setTopMargin(8));
                historyLayout.add(new Text(topic.name));

                GuiNPCDialog.this.root.doLayout();
                historyScrollableLayout.scrollToBottom();
                TESItems.networkWrapper.sendToServer(new MessageDialogSelectTopic(topic));
            }));
        });

        root.doLayout();
    }

    public void setUpdated() {
        updated = true;
    }

/*    private EntityPlayer player;
    private IPlayerAttributesCapability cap;
    private NPC npc;
    private VerticalLayout historyElement;
    private ScrollableLayout leftElement;
    private boolean updated = true;

    public VerticalLayout topics;
    public ScrollableLayout topicsScrollableLayout;

    public GuiNPCDialog() {
        super();
        player = mc.thePlayer;
        cap = TESItems.getCapability(player);
        npc = cap.getLatestNPC();

        root = new ScreenCenteredLayout() {
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
                element.setBounds(left + paddingLeft, top + paddingTop, right - paddingRight, bottom - paddingBottom);
                ((LayoutBase) element).doLayout();
            }

            @Override
            public void checkClick(int mouseX, int mouseY) {
                if (updated)
                    super.checkClick(mouseX, mouseY);
            }
        };
        contentLayout = new FancyFrameLayout();
        contentLayout.setPadding(0);
        root.setElement(contentLayout);
        DialogWindowLayout root1 = new DialogWindowLayout();
        leftElement = new ScrollableLayout() {
            @Override
            public void scrollToBottom() {
                int h = element.getHeight();
                scroll = h - height;
                if (scroll < 0) scroll = 0;
                ((VerticalLayout) element).setTop(top + paddingTop - scroll);
                ((LayoutBase)element).doLayout();
            }
        };
        leftElement.setPadding(0);
        VerticalLayout leftLayout = new VerticalLayout() {
            @Override
            public int getHeight() {
                int height = 0;
                for (ElementBase e : elements) {
                    height += e.getHeight() + spacing;
                }
                height += paddingTop + paddingBottom + marginBottom + marginTop;
                return height;
            }
        };
        historyElement = new VerticalLayout() {
            @Override
            public void doLayout() {
                int y = top + paddingTop;
                int x_left = left + paddingLeft;
                int x_right = x_left + width - paddingLeft - paddingRight;
                for (ElementBase e : elements) {
                    int h = ((Text)e).getNonDirtyHeight();
                    e.setBounds(x_left, y, x_right, y + h);
                    y += h + spacing;
                }
            }

            @Override
            public int getHeight() {
                int height = 0;
                for (ElementBase e : elements) {
                    int h = ((Text)e).getNonDirtyHeight();
                    height += h;
                }
                height += (elements.size() - 1) * spacing;
                height += paddingTop + paddingBottom + marginBottom + marginTop;
                return height;
            }
        };

        leftLayout.add(historyElement);
        leftElement.setElement(leftLayout);
        leftElement.setPaddingLeft(8);
        HorizontalLayout rightElement = new HorizontalLayout();
        rightElement.setSpacing(0);
        root1.setLeftElement(leftElement);
        root1.setRightElement(rightElement);
        rightElement.add(new VerticalDivider());
        VerticalLayout rightLayout = new VerticalLayout() {
            @Override
            public int getWidth() {
                return parent.width() - paddingLeft - paddingRight - marginLeft - marginRight;
            }
        };
        rightElement.add(rightLayout);
        rightLayout.setVerticalMargin(4);
        rightLayout.add(new Text(npc.name).center(true)).add(new HorizontalDivider());
        topics = new VerticalLayout();
        topics.setHorizontalMargin(2);
        topicsScrollableLayout = new ScrollableLayout();
        topicsScrollableLayout.setHorizontalMargin(2);
        topicsScrollableLayout.setElement(topics);
        topicsScrollableLayout.setPadding(0);
        rightLayout.add(topicsScrollableLayout);
        contentLayout.setElement(root1);

        cap.getLatestDialog().topics.forEach((name, t) -> topics.add(buildTopicElement(topics, t)));

        contentLayout.doLayout();
        leftElement.setHeight(root1.getHeight());
        topicsScrollableLayout.setHeight(rightElement.getHeight() - 24);
        contentLayout.doLayout();
    }

    public Text buildTopicElement(ElementBase root, DialogTopic topic) {
        Text text = new Text(topic.name) {
            @Override
            public void click(int relativeX, int relativeY) {
                historyAppendTopic(topic.name);
                historyAppendText(topic.dialogLine);
                GuiNPCDialog.this.root.doLayout();
                leftElement.scrollToBottom();
                TESItems.networkWrapper.sendToServer(new MessageDialogSelectTopic(topic));
            }
        };
        return text;
    }

    public void historyAppendText(String appendStr) {
        Text t = new Text() {
            @Override
            protected void update() {
                if (width == 0) {
                    System.out.println("width is 0");
                    return;
                }
                strs = fontRenderer.listFormattedStringToWidth(text, width);
                dirty = false;
            }

            @Override
            public void draw(int mouseX, int mouseY) {
                int x = left + paddingLeft;
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
        t._setwidth(historyElement.width());
        historyElement.add(t);
        t.setText(appendStr);
    }

    public void historyAppendTopic(String appendStr) {
        Text t = new Text() {
            @Override
            protected void update() {
                if (width == 0) {
                    System.out.println("width is 0");
                    return;
                }
                strs = fontRenderer.listFormattedStringToWidth(text, width);
                dirty = false;
            }

            @Override
            public void draw(int mouseX, int mouseY) {
                int x = left + paddingLeft;
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
        t._setwidth(historyElement.width());
        historyElement.add(t);
        t.setTopMargin(8);
        t.setText(appendStr);
        t.setColor(0xFF0066CC);
    }

    public void setUpdated() {
        updated = true;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    private class DialogWindowLayout extends LayoutBase {
        private static final int rightElementWidth = 140;
        private ScaledResolution res;

        private LayoutBase leftElement, rightElement;

        public DialogWindowLayout() {
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
    }*/
}
