package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.GUI.Elements.ScrollableLayout;
import ru.iammaxim.tesitems.NPC.EntityNPC;

/**
 * Created by maxim on 11/9/16 at 9:34 PM.
 */
public class GuiDialogEditor extends Screen {
    private EntityPlayer player;
    private EntityNPC npc;
    private ScrollableLayout scrollableLayout;

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        scrollableLayout.setHeight((int) (res.getScaledHeight() * 0.8f));
    }

    public GuiDialogEditor(EntityPlayer player) {
        super();
        this.player = player;

        scrollableLayout = new ScrollableLayout(contentLayout);
        contentLayout.setElement(scrollableLayout);
        scrollableLayout.setHeight((int) (res.getScaledHeight() * 0.8f));

    }
}
