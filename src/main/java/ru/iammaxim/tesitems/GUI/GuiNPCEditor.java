package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.text.TextComponentString;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.io.IOException;

/**
 * Created by maxim on 11/5/16 at 9:10 PM.
 */
public class GuiNPCEditor extends GuiScreen {
    private static final int paddingTop = 30, paddingBottom = 30;

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

        layout = new GuiVerticalLinearLayout();
        layout.width = 300;
        layout.setSpace(4);
        layout.setPadding(6, 6, 6, 6);

        layout.add(new GuiText(layout, "text1"));
        layout.add(new GuiText(layout, "text2"));
        layout.add(new GuiTextField(layout, "Hint..."));
        layout.add(new GuiText(layout, "text3"));
        layout.add(new GuiText(layout, "text4"));

        int w = layout.calculateWidth();
        int left = (res.getScaledWidth() - w) / 2;
        int top = paddingTop;
        layout.setBounds(top, left, res.getScaledHeight() - paddingBottom, left + w);
        layout.doLayout(top, left);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        layout.draw(mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        layout.keyTyped(typedChar, keyCode);
    }
}
