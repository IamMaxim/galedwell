package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.Dialogs.DialogTopic;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Networking.MessageDialogSelectTopic;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by maxim on 7/24/16.
 */
public class GuiNPCDialog extends Screen {
    private EntityPlayer player;
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
            public void scrollToBottom() {
                int h = element.getHeight();
                scroll = h - height;
                if (scroll < 0) scroll = 0;
                ((VerticalLayout) element).setTop(top + padding - scroll);
                ((LayoutBase)element).doLayout();
            }
        };
        leftElement.setHorizontalMargin(8);
        leftElement.setPadding(0);
        VerticalLayout leftLayout = new VerticalLayout(leftElement) {
            @Override
            public int getHeight() {
                int height = 0;
                for (ElementBase e : elements) {
                    height += e.getHeight() + spacing;
                }
                height += 2 * padding + marginBottom + marginTop;
                return height;
            }
        };
        historyElement = new VerticalLayout(leftLayout) {
            @Override
            public void doLayout() {
                int y = top + padding;
                int x_left = left + padding;
                int x_right = x_left + width - 2 * padding;
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
                height += 2 * padding + marginBottom + marginTop;
                return height;
            }
        };
        leftLayout.add(historyElement);
        leftElement.setElement(leftLayout);

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
        rightLayout.add(new Text(rightLayout, npc.name).setLeftPadding(4)).add(new HorizontalDivider(rightLayout));
        topics = new VerticalLayout(rightLayout);
        topics.setHorizontalMargin(4);
        topicsScrollableLayout = new ScrollableLayout(rightLayout);
        topicsScrollableLayout.setElement(topics);
        rightLayout.add(topicsScrollableLayout);
        contentLayout.setElement(root1);

        cap.getLatestDialog().topics.forEach((name, t) -> topics.add(buildTopicElement(topics, t)));

        contentLayout.doLayout();
        leftElement.setHeight(root1.getHeight());
        topicsScrollableLayout.setHeight(rightElement.getHeight() - 16);
        contentLayout.doLayout();
    }

    public Text buildTopicElement(ElementBase root, DialogTopic topic) {
        Text text = new Text(root, topic.name) {
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
        Text t = new Text(historyElement) {
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
        t._setwidth(historyElement.width());
        historyElement.add(t);
        t.setText(appendStr);
    }

    public void historyAppendTopic(String appendStr) {
        Text t = new Text(historyElement) {
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
