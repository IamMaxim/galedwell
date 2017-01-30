package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Networking.MessageNPCUpdate;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by maxim on 11/5/16 at 9:10 PM.
 */
public class GuiNPCEditor extends Screen {
    private EntityPlayer player;
    private NPC npc;
    private ScrollableLayout scrollableLayout;
    private HashMap<VerticalLayout, Faction> factionEntries = new HashMap<>();

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
//        scrollableLayout.setHeight((int) (res.getScaledHeight() * 0.8f));
    }

    public GuiNPCEditor(EntityPlayer player) {
        this.player = player;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        npc = cap.getLatestNPC();
        res = new ScaledResolution(Minecraft.getMinecraft());

        scrollableLayout = new ScrollableLayout();
        contentLayout.setElement(scrollableLayout);
        VerticalLayout layout = new VerticalLayout();
        scrollableLayout.setElement(layout);


        TextField npcName = new TextField().setHint("Name").setText(npc.name).setOnType(tf -> npc.setName(tf.getText()));
        CheckBox invulnerability = new CheckBox().setChecked(npc.isInvulnerable()).setText("Invulnerable").setOnClick(((cb, newState) -> npc.setInvulnerable(newState)));
        layout.add(npcName);
        layout.add(invulnerability);
        layout.add(new HorizontalDivider());
        layout.add(new Text("Factions").center(true));

        VerticalLayout factionsLayout = new VerticalLayout();
        npc.getFactions().forEach(f -> {
            VerticalLayout fl = new VerticalLayout();
            Text factionName = new Text(f.name + "");
            fl.add(new TextField().setHint("Faction ID").setText(f.id + "").setOnType(tf -> {
                try {
                    Faction newFaction;
                    if ((newFaction = FactionManager.getFaction(Integer.parseInt(tf.getText()))) != null) {
                        factionName.setText(newFaction.name);
                        factionEntries.put(fl, newFaction);
                    }
                } catch (NumberFormatException e) {}
            }));
            fl.add(factionName);
            factionEntries.put(fl, f);
            factionsLayout.add(fl);
        });
        layout.add(factionsLayout);

        layout.add(new Button("Add faction").setOnClick(b -> {
            Faction f = new Faction("");
            VerticalLayout fl = new VerticalLayout();
            Text factionName = new Text("Faction name will be here if all is right");
            fl.add(new TextField().setHint("Faction ID").setOnType(tf -> {
                try {
                    Faction newFaction;
                    if ((newFaction = FactionManager.getFaction(Integer.parseInt(tf.getText()))) != null) {
                        factionName.setText(newFaction.name);
                        factionEntries.put(fl, newFaction);
                    }
                } catch (NumberFormatException e) {}
            }));
            fl.add(factionName);
            factionEntries.put(fl, f);
            factionsLayout.add(fl);
        }));

        layout.add(new HorizontalDivider());
        layout.add(new Button().setText("Save").setOnClick(b -> {
            npc.getFactions().clear();
            Iterator<VerticalLayout> it = factionEntries.keySet().iterator();
            while (it.hasNext()) {
                VerticalLayout fl = it.next();
                Faction f = factionEntries.get(fl);
                if (FactionManager.getFaction(f.id) == null)
                    it.remove();
                else npc.addFaction(f);
            }

            TESItems.networkWrapper.sendToServer(new MessageNPCUpdate(npc.getNBT()));
            mc.displayGuiScreen(new GuiAlertDialog("Changes updated", this));
        }));

        int width = root.getWidth();
        int height = root.getHeight();
//        scrollableLayout.setHeight((int) (res.getScaledHeight() * 0.8f));
        root.setBounds((res.getScaledWidth() - width)/2, (res.getScaledHeight() - height)/2, (res.getScaledWidth() + width)/2, (res.getScaledHeight() + height)/2);
        root.doLayout();
    }
}
