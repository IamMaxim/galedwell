package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.NPC.NPC;
import ru.iammaxim.tesitems.Networking.MessageNPCUpdate;
import ru.iammaxim.tesitems.Networking.MessageOpenNPCInventory;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by maxim on 11/5/16 at 9:10 PM.
 */
public class GuiNPCEditor extends Screen {
    private EntityPlayer player;
    private NPC npc;
    private ScrollableLayout scrollableLayout;
    private VerticalLayout factionsLayout;
    private HashMap<VerticalLayout, Faction> factionEntries = new HashMap<>();

    public GuiNPCEditor(EntityPlayer player) {
        this.player = player;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        npc = cap.getLatestNPC();
        res = new ScaledResolution(Minecraft.getMinecraft());
        contentLayout.setElement(new ScrollableLayout().setElement(
                new VerticalLayout()
                        .add(new TextField().setHint("Name").setText(npc.name).setOnType(tf -> npc.setName(tf.getText())).setWidthOverride(ElementBase.FILL))
                        .add(new TextField().setHint("Skin name").setText(npc.skinName).setOnType(tf -> npc.setSkinName(tf.getText())).setWidthOverride(ElementBase.FILL))
                        .add(new CheckBox().setChecked(npc.isInvulnerable()).setText("Invulnerable").setOnClick(((cb, newState) -> npc.setInvulnerable(newState))))
                        .add(new HorizontalDivider())
                        .add(new HeaderLayout("Factions").setWidthOverride(ElementBase.FILL))
                        .add(factionsLayout = new VerticalLayout())
                        .add(new Button("Add faction").setOnClick(b -> addFaction(new Faction(""))))
                        .add(new HorizontalDivider())
                        .add(new Button("Open inventory").center(true).setOnClick(b ->
                                TESItems.networkWrapper.sendToServer(new MessageOpenNPCInventory())).setWidthOverride(ElementBase.FILL))
                        .add(new Button("Save").center(true).setOnClick(b -> {
                                    npc.clearFactions();
                                    Iterator<Map.Entry<VerticalLayout, Faction>> it = factionEntries.entrySet().iterator();
                                    while (it.hasNext()) {
                                        Map.Entry<VerticalLayout, Faction> entry = it.next();
                                        Faction f = entry.getValue();
                                        if (f == null) {
                                            it.remove();
                                            factionsLayout.remove(entry.getKey());
                                        } else {
                                            npc.addFaction(f.id);
                                        }
                                    }

                                    TESItems.networkWrapper.sendToServer(new MessageNPCUpdate(npc.getNBT()));
                                    new GuiAlertDialog("Changes updated").show();
                                }).setWidthOverride(ElementBase.FILL)
                        )));

        npc.getFactions().forEach(this::addFaction);
        root.doLayout();
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
    }

    private void addFaction(Faction f) {
        VerticalLayout fl = new VerticalLayout();
        Text factionName = new Text("Faction name will be here if all is right");
        if (!f.name.equals(""))
            factionName.setText(f.name);
        fl.add(new TextField().setText(f.id + "").setHint("Faction ID").setOnType(tf -> {
            try {
                Faction newFaction;
                if ((newFaction = FactionManager.getFaction(Integer.parseInt(tf.getText()))) != null)
                    factionName.setText(newFaction.name);
                factionEntries.put(fl, newFaction);
            } catch (NumberFormatException e) {
                factionEntries.put(fl, null);
            }
        }));
        fl.add(factionName);
        factionEntries.put(fl, f);
        factionsLayout.add(fl);
    }
}
