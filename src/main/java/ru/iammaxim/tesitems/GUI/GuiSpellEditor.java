package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.Magic.SpellBase;

/**
 * Created by maxim on 2/21/17 at 11:15 PM.
 */
public class GuiSpellEditor extends Screen {
    public SpellBase spell;

    public GuiSpellEditor(SpellBase spell) {
        if (spell != null)
            this.spell = spell;
        else {

        }
    }
}
