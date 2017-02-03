package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.GuiScrollingList;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

import java.util.List;

/**
 * Created by Maxim on 17.07.2016.
 */
public class GuiSpellSelect extends Screen {
    public GuiSpellSelect() {
        contentLayout.setElement(new HeaderLayout("Select spell"));
    }
}
