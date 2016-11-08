package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;
import ru.iammaxim.tesitems.Fractions.Faction;
import ru.iammaxim.tesitems.GUI.elements.*;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 11/5/16 at 9:10 PM.
 */
public class GuiNPCEditor extends GuiScreen {
    private static final int paddingTop = 30, paddingBottom = 30;
    ScreenCenteredLayout root;
    private ScaledResolution res;
    private EntityPlayer player;
    private EntityNPC npc;
    private ScrollableLayout scrollableLayout;
    private ArrayList<VerticalLayout> factionEntries = new ArrayList<>();

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        res = new ScaledResolution(mcIn);
        int width = root.getWidth();
        int height = root.getHeight();
        root.setBounds((res.getScaledWidth() - width)/2, (res.getScaledHeight() - height)/2, (res.getScaledWidth() + width)/2, (res.getScaledHeight() + height)/2);
        scrollableLayout.setHeight((int) (res.getScaledHeight() * 0.8f));
        root.doLayout();
        root.onRescale();
    }

    public GuiNPCEditor(EntityPlayer player) {
        this.player = player;
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        npc = cap.getLatestNPC();
        res = new ScaledResolution(Minecraft.getMinecraft());

        root = new ScreenCenteredLayout(null);
        FancyFrameLayout fancyFrameLayout = new FancyFrameLayout(root);
        root.setElement(fancyFrameLayout);
        scrollableLayout = new ScrollableLayout(fancyFrameLayout);
        fancyFrameLayout.setElement(scrollableLayout);
        VerticalLayout layout = new VerticalLayout(scrollableLayout);
        scrollableLayout.setElement(layout);


        TextField npcName = new TextField(layout).setHint("Name").setText(npc.getName());
        layout.add(npcName);
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

        int width = root.getWidth();
        int height = root.getHeight();
        scrollableLayout.setHeight((int) (res.getScaledHeight() * 0.8f));
        root.setBounds((res.getScaledWidth() - width)/2, (res.getScaledHeight() - height)/2, (res.getScaledWidth() + width)/2, (res.getScaledHeight() + height)/2);
        root.doLayout();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        root.checkClick(mouseX, mouseY);
        root.draw(mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        root.keyTyped(typedChar, keyCode);
    }
}
