package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.Image;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.FrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Networking.MessageSpellbook;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 17.07.2016.
 */
public class GuiSpellSelect extends Screen {
    private VerticalLayout list;
    private ElementBase activeSpell;

    public GuiSpellSelect() {
        contentLayout.setElement(new FrameLayout().setElement(new HeaderLayout("Loading spellbook...")));

        root.getScreen().addCallback("spellbookUpdated", o -> {
            init();
        });

        TESItems.networkWrapper.sendToServer(new MessageSpellbook());

        root.doLayout();
    }

    private void init() {
        contentLayout.setElement(new VerticalLayout()
                .add(new HeaderLayout("Select spell"))
                .add(new ScrollableLayout()
                        .setElement(list = (VerticalLayout) new VerticalLayout()
                                .setWidthOverride(ElementBase.FILL))
                        .setWidthOverride(ElementBase.FILL))
                .setPadding(4));


        IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());

        int i = 0;
        for (SpellBase spell : cap.getSpellbook()) {
            if (spell == null) {
                System.out.println("SPELL IS NULL!");
                continue;
            }

            ElementBase e = getElement(spell);
            list.add(e);
            if (i == cap.getCurrentSpell())
                e.setBackground(new Image(ResManager.inv_entry_bg_selected));
            i++;
        }
    }

    private ElementBase getElement(SpellBase spell) {
        IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());

        FrameLayout fl = new FrameLayout();
        fl.setElement(new Text(spell.name));
        fl.setOnClick(e -> {
            if (activeSpell != null)
                activeSpell.setBackground(null);

            fl.setBackground(new Image(ResManager.inv_entry_bg_selected));

            cap.setCurrentSpell(list.getElements().indexOf(fl));
        });

        return fl;
    }
}
