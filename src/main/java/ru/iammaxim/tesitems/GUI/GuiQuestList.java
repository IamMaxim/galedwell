package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.GuiScrollingList;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Questing.QuestInstance;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maxim on 20.07.2016.
 */
public class GuiQuestList extends GuiScreen {
    private static final int w = 300, entryHeight = 15, paddingTop = 40, paddingBottom = 40;
    private int h;
    private EntityPlayer player;
    private IPlayerAttributesCapability cap;
    private HashMap<Integer, QuestInstance> questList;
    private int selected;
    GuiScrollingList guiQuestList;
    ScaledResolution res;

    public GuiQuestList() {
        mc = Minecraft.getMinecraft();
        player = mc.thePlayer;
        cap = TESItems.getCapability(player);
        questList = cap.getQuests();

//        setGuiSize(w, h);
        res = new ScaledResolution(this.mc);
        h = res.getScaledHeight() - paddingTop - paddingBottom;
        guiQuestList = new GuiQuestListList(mc, w, h,
                paddingTop,
                res.getScaledHeight() - paddingBottom,
                (res.getScaledWidth() - w)/2,
                entryHeight,
                res.getScaledWidth(),
                res.getScaledHeight(),
                questList);
    }

    @Override
    public void onResize(Minecraft mcIn, int nw, int nh) {
        super.onResize(mcIn, w, h);
        res = new ScaledResolution(mcIn);
        h = res.getScaledHeight() - paddingTop - paddingBottom;
        guiQuestList = new GuiQuestListList(mc, w, h,
                paddingTop,
                res.getScaledHeight() - paddingBottom,
                (res.getScaledWidth() - w)/2,
                entryHeight,
                res.getScaledWidth(),
                res.getScaledHeight(),
                questList);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        guiQuestList.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private class GuiQuestListList extends GuiScrollingList {
        private List<QuestInstance> questList = new ArrayList<>();
        private ScaledResolution res;

        public GuiQuestListList(Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight, int screenWidth, int screenHeight, HashMap<Integer, QuestInstance> questList) {
            super(client, width, height, top, bottom, left, entryHeight, screenWidth, screenHeight);
            res = new ScaledResolution(client);
            questList.forEach((k,v) -> this.questList.add(v));
        }

        @Override
        protected int getSize() {
            return questList.size();
        }

        @Override
        protected void elementClicked(int index, boolean doubleClick) {
            selected = index;
        }

        @Override
        protected boolean isSelected(int index) {
            return index == selected;
        }

        @Override
        protected void drawBackground() {
            //drawRect((res.getScaledWidth() - w) / 2, (res.getScaledHeight() - h) / 2, (res.getScaledWidth() + w) / 2, (res.getScaledHeight() + h) / 2, 0x99000000);
        }

        @Override
        protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
            mc.fontRendererObj.drawString(questList.get(slotIdx).quest.name, (res.getScaledWidth() - w) / 2 + 4, slotTop + 2, 0xffffffff);
        }
    }
}
