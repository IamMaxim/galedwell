package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.Layouts.FixedSizeLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 11/5/16 at 5:42 PM.
 */
public class GuiJournal extends Screen {
    public GuiJournal() {
        IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());

        contentLayout.setElement(new FixedSizeLayout()
                .setFixedWidth((int) (res.getScaledWidth() * 0.8))
                .setFixedHeight((int) (res.getScaledHeight() * 0.8))
                .setElement(new ScrollableLayout()
                        .setElement(new Text(cap.getJournal()))
                )
        );
    }
}
