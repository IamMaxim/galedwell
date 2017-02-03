package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.HorizontalDivider;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.Networking.MessageOpenEditFaction;
import ru.iammaxim.tesitems.Player.AdminTemporaryStorage;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 01.01.2017.
 */
public class GuiFactionList extends Screen {
    private VerticalLayout factionsLayout;

    public GuiFactionList() {
        contentLayout.setPadding(0);
        ScrollableLayout root1 = new ScrollableLayout();
        root1.setPadding(4);
        contentLayout.setElement(root1);
//        root1.setHeight((int) (res.getScaledHeight() * 0.8));
        VerticalLayout root2 = new VerticalLayout();
        root1.setElement(root2);
        root2.add(new HeaderLayout("Faction list"));
        root2.add(new HorizontalDivider());
        factionsLayout = new VerticalLayout();
        root2.add(factionsLayout);
        root2.add(new HorizontalDivider());
        root2.add(new Button("New faction").setOnClick(b -> {
            Faction f = new Faction("");
            f.id = -1;
            AdminTemporaryStorage.lastEditedFaction = f;
            TESItems.getMinecraft().displayGuiScreen(new GuiFactionEditor());
        }));

        FactionManager.factions.forEach((id, f) -> factionsLayout.add(new Text(f.name).setColor(0xFF0066CC).setOnClick(e ->
                /*mc.displayGuiScreen(new GuiFactionEditor(f))*/
                TESItems.networkWrapper.sendToServer(new MessageOpenEditFaction(id))
        )));

        root.doLayout();
    }
}
