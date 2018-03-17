package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
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
 * Created by maxim on 2/21/17 at 4:10 PM.
 */
public class GuiSpellEditorList extends Screen {

    private VerticalLayout list;

    public GuiSpellEditorList() {
        contentLayout.setElement(new FrameLayout().setElement(new HeaderLayout("Loading spellbook...")));

        root.getScreen().addCallback("spellbookUpdated", o -> init());

        TESItems.networkWrapper.sendToServer(new MessageSpellbook().setNeedScripts());

        root.doLayout();
    }

    private void init() {
        VerticalLayout layout = new VerticalLayout();
        layout.add(new HeaderLayout("Spell list"));
        layout.add(list = (VerticalLayout) new VerticalLayout().setWidthOverride(ElementBase.FILL));
        layout.add(new Button("Create new spell").setOnClick(b -> {
            new GuiSpellEditor(null);
        }));
        contentLayout.setElement(new ScrollableLayout().setElement(new VerticalLayout().add(layout)));

        IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());

        for (SpellBase spell : cap.getSpellbook()) {
            list.add(new Text(spell.name).setOnClick(b -> {
                new GuiSpellEditor(spell);
            }));
        }

        root.doLayout();
    }
}
