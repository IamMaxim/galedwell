package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.FrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

public class GuiAttributes extends Screen {
    private IPlayerAttributesCapability cap;
    private Table table;


    public GuiAttributes() {
        contentLayout.setPadding(4);
        contentLayout.setElement(
                new VerticalLayout()
                        .add(new HeaderLayout("Attributes"))
                        .add(new ScrollableLayout()
                                .setElement(new VerticalLayout()
                                        .add(table = new Table(
                                                        (TableEntry) new TableEntry(0)
                                                                .add(new Text("Attribute").center(true)._setwidth(90))
                                                                .add(new Text("Progress").center(true)._setwidth(90))
                                                                .add(new Text("Level").center(true)._setwidth(90))
                                                                .add(new Text("Status").center(true)._setwidth(50))
                                                )
                                        )
                                )
                        )
        );

        table.setWidth(table.getHeader().getWidth());

        EntityPlayer player = TESItems.getClientPlayer();
        cap = TESItems.getCapability(player);
        cap.getAttributes().forEach(this::addAttribute);

        root.doLayout();
    }

    private void addAttribute(String name, float value) {
        table.add((ElementBase) new TableEntry()
                .add(new FrameLayout()
                        .setElement(new Text(I18n.format(name)))
                        .setPadding(2, 4, 2, 4))
                .add(new FrameLayout()
                        .setElement(new ProgressBar().setProgress(getSkillProgress(name, value)))
                        .setPadding(3, 4, 3, 4))
                .add(new FrameLayout()
                        .setElement(new Text(I18n.format(getSkillLevel(name, value))).center(true))
                        .setPadding(2, 4, 2, 4))
                .add(new FrameLayout()
                        .setElement(new Text(I18n.format(getSkillStatus(name, value))).center(true))
                        .setPadding(2, 4, 2, 4))
        );
    }

    private String getSkillStatus(String name, float value) {
        // TODO: return meaningful information
        return "Status";
    }

    private String getSkillLevel(String name, float value) {
        switch ((int) value) {
            case 0:
                return I18n.format("skill_level.newbie");
            case 1:
                return I18n.format("skill_level.expert");
            case 2:
                return I18n.format("skill_level.master");
            case 3:
                return I18n.format("skill_level.legend");
        }

        return String.valueOf(value);
    }

    private float getSkillProgress(String name, float value) {
        return value - (int) value;
    }
}
