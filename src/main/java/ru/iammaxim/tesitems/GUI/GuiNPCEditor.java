package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.text.TextComponentString;
import ru.iammaxim.tesitems.Fractions.Faction;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by maxim on 11/5/16 at 9:10 PM.
 */
public class GuiNPCEditor extends GuiScreen {
    private static final int paddingTop = 30, paddingBottom = 30;
    GuiFancyFrameLayout container;
    GuiVerticalLinearLayout layout;
    private ScaledResolution res;
    private EntityPlayer player;
    private EntityNPC npc;

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        layout.resize();
        res = new ScaledResolution(mcIn);
    }

    public GuiNPCEditor(EntityPlayer player) {
        this.player = player;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        npc = cap.getLatestNPC();
        res = new ScaledResolution(Minecraft.getMinecraft());
        List<Faction> factions = npc.getFactions();

        container = new GuiFancyFrameLayout(null);
        layout = new GuiVerticalLinearLayout(container);
        layout.width = 300;
        layout.setSpace(4);
        layout.setPadding(6, 6, 6, 6);
        //layout.setBackgroundColor(0x90000000);

        //NPC name
        GuiTextField nameField = new GuiTextField(layout, I18n.format("tesitems:NPCEditor.npcName")).setText(npc.getName());
        layout.add(nameField);

        //Factions
        layout.add(new GuiDivider(layout));
        layout.add(new GuiText(layout, I18n.format("tesitems:NPCEditor.factions")).centerHorizontally(true));
        GuiVerticalLinearLayout factionsLayout = new GuiVerticalLinearLayout(layout);
        factionsLayout.width = 270;
        factionsLayout.setSpace(4);
        factionsLayout.centerHorizontally(true);

        factionsLayout.add(new GuiTextField(factionsLayout, I18n.format("tesitems:NPCEditor.factionID")));
        factionsLayout.add(new GuiText(factionsLayout, I18n.format("tesitems:NPCEditor.factionLabel")));
        factionsLayout.add(new GuiDivider(factionsLayout));

        layout.add(factionsLayout);

        layout.add(new GuiButton(layout, I18n.format("tesitems:NPCEditor.addFaction")).setOnClick(() -> {
            //factions.add(null);
            factionsLayout.add(new GuiTextField(factionsLayout, I18n.format("tesitems:NPCEditor.factionID")));
            factionsLayout.add(new GuiText(factionsLayout, I18n.format("tesitems:NPCEditor.factionLabel")));
            factionsLayout.add(new GuiDivider(factionsLayout));
            layout.doLayout((res.getScaledHeight() - layout.getHeight()) / 2, layout.left);
            container.matchToChild();
            container.calculateBounds((res.getScaledHeight() - container.getHeight())/2, (res.getScaledWidth() - container.getWidth())/2);
//            mc.displayGuiScreen(new GuiAlertDialog("Test alert dialog"));
        }).centerHorizontally(true));

        //Save button
        layout.add(new GuiDivider(layout));
        layout.add(new GuiButton(layout, I18n.format("tesitems:NPCEditor.save")).setOnClick(() -> {
            npc.setName(nameField.getText());
        }));
        layout.add(new GuiButton(layout, "Test Alert Dialog").setOnClick(() ->
                mc.displayGuiScreen(new GuiAlertDialog("This is example of alert dialog"))));

        int w = layout.calculateWidth();
        int left = (res.getScaledWidth() - w) / 2;
        int top = (res.getScaledHeight() - layout.getHeight()) / 2;
        layout.setBounds(top, left, res.getScaledHeight() - paddingBottom, left + w);
        layout.doLayout(top, left);

        container.set(layout);
        container.matchToChild();
        container.calculateBounds((res.getScaledHeight() - container.getHeight())/2, (res.getScaledWidth() - container.getWidth())/2);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        container.draw(mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        layout.keyTyped(typedChar, keyCode);
    }
}
