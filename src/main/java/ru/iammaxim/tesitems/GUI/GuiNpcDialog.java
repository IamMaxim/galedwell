package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 7/24/16.
 */
public class GuiNpcDialog extends GuiScreen {
    ScaledResolution res;
    private static final int bgColor = 0x77000000, nameColor = 0xffffffff;

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private int xoffset = 0, yoffset = -100, w = 400, h = 200;
    private EntityNPC npc;

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        res = new ScaledResolution(mc);
        setGuiSize(w, h);
    }

    @Override
    public void onGuiClosed() {
        //TODO: back normal camera
    }

    @Override
    public void initGui() {
        //TODO: init fake camera
    }

    public GuiNpcDialog() {
        mc = Minecraft.getMinecraft();
        res = new ScaledResolution(mc);
        setGuiSize(w, h);
        npc = TESItems.getCapatibility(mc.thePlayer).getLatestNPC();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int left, right;
        if (xoffset + w <= res.getScaledWidth()/2) {
            left = res.getScaledWidth() / 2 + xoffset;
        } else {
            left = res.getScaledWidth() - xoffset - w;
        }
        right = left + w;
        //draw background
        drawRect(left, res.getScaledHeight() / 2 + yoffset, right, res.getScaledHeight() / 2 + yoffset + h, bgColor);
        //draw name
        GL11.glPushMatrix();
        GL11.glScalef(2, 2, 2);
        drawCenteredString(mc.fontRendererObj, npc.getName(), (left + right) / 4, (res.getScaledHeight() / 2 + yoffset - 20) / 2, nameColor);
        GL11.glPopMatrix();
    }
}
