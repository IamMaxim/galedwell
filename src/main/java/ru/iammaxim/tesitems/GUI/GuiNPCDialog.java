package ru.iammaxim.tesitems.GUI;

import net.minecraft.entity.player.EntityPlayer;
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
                        .setRightWidth(160)
/*                .setMinHeight(200)*/);

        cap.getLatestDialog().topics.forEach((name, topic) -> {
            topics.add(new Text(topic.name).setOnClick(e -> {
                if (!updated) {
                    return;
                }

                historyLayout.add(new Text(topic.name).setColor(0xFF0066CC).setTopMargin(8));
                Text dialogLine;
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
                dialogLine.update();

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
}
