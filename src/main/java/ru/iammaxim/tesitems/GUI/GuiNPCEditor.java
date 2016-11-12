package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.GUI.elements.*;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Networking.NPCUpdateMessage;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;

/**
 * Created by maxim on 11/5/16 at 9:10 PM.
 */
public class GuiNPCEditor extends Screen {
    private static final int paddingTop = 30, paddingBottom = 30;
    private EntityPlayer player;
    private EntityNPC npc;
    private ScrollableLayout scrollableLayout;
    private ArrayList<VerticalLayout> factionEntries = new ArrayList<>();

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        scrollableLayout.setHeight((int) (res.getScaledHeight() * 0.8f));
    }

    public GuiNPCEditor(EntityPlayer player) {
        this.player = player;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        npc = cap.getLatestNPC();
        res = new ScaledResolution(Minecraft.getMinecraft());

        scrollableLayout = new ScrollableLayout(contentLayout);
        contentLayout.setElement(scrollableLayout);
        VerticalLayout layout = new VerticalLayout(scrollableLayout);
        scrollableLayout.setElement(layout);


        TextField npcName = new TextField(layout).setHint("Name").setText(npc.getName()).setOnType(tf -> {
            npc.setName(tf.getText());
        });
        CheckBox invulnerability = new CheckBox(layout).setChecked(npc.isInvulnerable()).setText("Invulnerable").setOnClick(((cb, newState) -> {
            npc.setInvulnerable(newState);
        }));
        layout.add(npcName);
        layout.add(invulnerability);
        layout.add(new Divider(layout));
        layout.add(new Text(layout, "Factions").center(true));

        VerticalLayout factionsLayout = new VerticalLayout(layout);
        npc.getFactions().forEach(f -> {
            VerticalLayout fl = new VerticalLayout(factionsLayout);
            fl.add(new TextField(fl).setHint("Faction ID").setText(f.id + ""));
            fl.add(new Text(fl, f.name + ""));
            factionEntries.add(fl);
            factionsLayout.add(fl);
        });
        layout.add(factionsLayout);

        layout.add(new Button(layout).setText("Add faction").setOnClick(b -> {
            VerticalLayout fl = new VerticalLayout(factionsLayout);
            fl.add(new TextField(fl).setHint("Faction ID"));
            fl.add(new Text(fl, "Faction name will be here if all is right"));
            factionEntries.add(fl);
            factionsLayout.add(fl);
        }));

        layout.add(new Divider(layout));
        layout.add(new Button(layout).setText("Update").setOnClick(b -> {
            TESItems.networkWrapper.sendToServer(new NPCUpdateMessage(npc.serializeNBT()));
            mc.displayGuiScreen(new GuiAlertDialog("Changes updated", this));
        }));

        int width = root.getWidth();
        int height = root.getHeight();
        scrollableLayout.setHeight((int) (res.getScaledHeight() * 0.8f));
        root.setBounds((res.getScaledWidth() - width)/2, (res.getScaledHeight() - height)/2, (res.getScaledWidth() + width)/2, (res.getScaledHeight() + height)/2);
        root.doLayout();
    }
}
