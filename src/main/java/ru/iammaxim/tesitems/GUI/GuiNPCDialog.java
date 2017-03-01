package ru.iammaxim.tesitems.GUI;

import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.Dialogs.DialogTopic;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.HorizontalDivider;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.GUI.Elements.VerticalDivider;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Networking.MessageDialogSelectTopic;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/24/16.
 */
public class GuiNPCDialog extends Screen implements IGuiUpdatable {
    public VerticalLayout topics;
    private boolean updated = true;
    private int rightPaneWidth = 160;
    private ScrollableLayout historyScrollableLayout;
    private VerticalLayout historyLayout;

    public GuiNPCDialog() {
        EntityPlayer player = mc.thePlayer;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        NPC npc = cap.getLatestNPC();

        contentLayout.setElement(new TwoPaneLayout()
                        .setLeftElement(new FixedSizeLayout()
                                .setFixedWidth(res.getScaledWidth() - rightPaneWidth - 2 * FancyFrameLayout.frameSize)
                                .setFixedHeight((int) (res.getScaledHeight() * 0.9 - 2 * FancyFrameLayout.frameSize))
                                .setElement(historyScrollableLayout = (ScrollableLayout) new ScrollableLayout()
                                        .setElement(historyLayout = new VerticalLayout())))
                        .setRightElement(
                                new HorizontalLayout()
                                        .add(new VerticalDivider())
                                        .add(new VerticalLayout()
                                                .add(new Text(npc.name).center(true).setWidthOverride(ElementBase.FILL))
                                                .add(new HorizontalDivider())
                                                .add(new ScrollableLayout()
                                                        .setMinHeight(1000000)
                                                        .setElement(topics = (VerticalLayout) new VerticalLayout()
                                                                .setPaddingLeft(0))
                                                        .setPaddingTop(0)
                                                        .setWidthOverride(ElementBase.FILL))
                                                .setVerticalMargin(4)
                                                .setWidthOverride(ElementBase.FILL))
                                        .setSpacing(0))
                        .setRightWidth(rightPaneWidth));

        cap.getLatestDialog().topics.forEach((name, topic) -> addTopic(topic));
        root.doLayout();
    }

    public void addTopic(DialogTopic topic) {
        Text topicElement;
        topics.add(topicElement = (Text) new Text(topic.name) {
            @Override
            public void update() {
                try {
                    if (width == 0) {
                        return;
                    }
                    strs = fontRenderer.listFormattedStringToWidth(text, width);
                    textWidth = fontRenderer.getStringWidth(strs.get(0));
                    dirty = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.setOnClick(e -> {
            if (!updated()) {
                return;
            }

            Text topicName, dialogLine;
            historyLayout.add(topicName = (Text) new Text(topic.name) {
                @Override
                public void update() {
                    try {
                        if (width == 0) {
                            return;
                        }
                        strs = fontRenderer.listFormattedStringToWidth(text, width);
                        textWidth = fontRenderer.getStringWidth(strs.get(0));
                        dirty = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.setColor(ResManager.CLICKABLE_TEXT_COLOR).setTopMargin(8));
            historyLayout.add(dialogLine = new Text(topic.dialogLine) {
                @Override
                public void update() {
                    try {
                        if (width == 0) {
                            return;
                        }
                        strs = fontRenderer.listFormattedStringToWidth(text, width);
                        textWidth = fontRenderer.getStringWidth(strs.get(0));
                        dirty = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            historyLayout.doLayout();
            topicName.update();
            dialogLine.update();

            GuiNPCDialog.this.root.doLayout();
            historyScrollableLayout.scrollToBottom();
            TESItems.networkWrapper.sendToServer(new MessageDialogSelectTopic(topic));
        }));
        topics.doLayout();
        topicElement.update();
    }

    @Override
    public void update() {
        updated = true;
    }

    @Override
    public void unupdate() {
        updated = false;
    }

    @Override
    public boolean updated() {
        return updated;
    }
}
