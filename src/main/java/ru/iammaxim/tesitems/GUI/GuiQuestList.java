package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.GuiScrollingList;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.TESItems;

import java.util.List;

/**
 * Created by Maxim on 20.07.2016.
 */
public class GuiQuestList extends GuiScreen {
    private static final int w = 300, h = 500, entryHeight = 15;
    private EntityPlayer player;
    private IPlayerAttributesCapability cap;
    private List<Quest> questList;
    private int selected;
    private List<SpellBase> spellbook;

    public GuiQuestList() {
        super();
        mc = Minecraft.getMinecraft();
        player = mc.thePlayer;
        cap = TESItems.getCapatibility(player);
        spellbook = cap.getSpellbook();
        selected = cap.getCurrentSpell();
        setGuiSize(w, h);
        ScaledResolution res = new ScaledResolution(this.mc);
        spellList = new GuiScrollingList(mc, w, h, (res.getScaledHeight() - h) / 2, (res.getScaledHeight() + h) / 2, (res.getScaledWidth() - w) / 2, entryHeight, width, height) {
            @Override
            protected int getSize() {
                return spellbook.size();
            }

            @Override
            protected void elementClicked(int index, boolean doubleClick) {
                cap.setCurrentSpell(index);
                selected = index;
            }

            @Override
            protected boolean isSelected(int index) {
                return index == selected;
            }

            @Override
            protected void drawBackground() {
                drawRect((res.getScaledWidth() - w) / 2, (res.getScaledHeight() - h) / 2, (res.getScaledWidth() + w) / 2, (res.getScaledHeight() + h) / 2, 0x99000000);
            }

            @Override
            protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
                mc.fontRendererObj.drawString(spellbook.get(slotIdx).getName(), (res.getScaledWidth() - w) / 2 + 4, slotTop + 2, 0xffffffff);
            }
        };
    }

    GuiScrollingList spellList;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        spellList.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
