package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Magic.SpellEffect;
import ru.iammaxim.tesitems.Magic.SpellEffectManager;
import ru.iammaxim.tesitems.Magic.SpellTypes.SpellBaseSelf;
import ru.iammaxim.tesitems.Magic.SpellTypes.SpellBaseTarget;
import ru.iammaxim.tesitems.ResourceManager;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;

/**
 * Created by maxim on 2/21/17 at 11:15 PM.
 */
public class GuiSpellEditor extends Screen {
    public SpellBase spell;
    public VerticalLayout effectsLayout;

    public GuiSpellEditor(SpellBase spell) {
        if (spell != null) {
            this.spell = spell.copy();
            init();
            show();
        } else {
            contentLayout.setElement(new Text("Loading..."));
            root.doLayout();
            new GuiSelectionDialog("Select spell type", "Self", "Target")
                    .setOnDismiss(gui -> {
                    }/*ScreenStack.close()*/)
                    .setOnSelect(i -> {
                        System.out.println("onSelect: " + i);
                        if (i == 0) { //self
                            this.spell = new SpellBaseSelf("");
                        } else if (i == 1) { //target
                            this.spell = new SpellBaseTarget("");
                        }
                        init();
                        show();
                    }).show();
        }
    }

    @Override
    public boolean close() {
        new GuiConfirmationDialog("Are you sure you don't want to save changes?", ScreenStack::forceClose).show();
        return false;
    }

    public void init() {
        VerticalLayout layout = new VerticalLayout();
        layout.add(new HeaderLayout("Spell editor"));
        layout.add(new TextField().setHint("Name").setText(spell.name).setOnType(tf -> spell.name = tf.getText()).setWidthOverride(ElementBase.FILL));
        layout.add(new Text("Type: " + (spell.getSpellType() == SpellBase.SELF ? "self" : "target")));
        layout.add(new Button("Convert to " + (spell.getSpellType() == SpellBase.SELF ? "target" : "self")).setOnClick(b -> {
            if (spell.getSpellType() == SpellBase.SELF) {
                //convert to target
                spell = new SpellBaseTarget(spell.name, spell.effects);
                init();
            } else if (spell.getSpellType() == SpellBase.TARGET) {
                //convert to self
                spell = new SpellBaseSelf(spell.name, spell.effects);
                init();
            }
        }));
        layout.add(new HorizontalDivider());
        layout.add(new HeaderLayout("Effects"));
        effectsLayout = (VerticalLayout) new VerticalLayout().setWidthOverride(ElementBase.FILL);

        for (SpellEffect effect : spell.effects) {
            effectsLayout.add(getEffectElement(effect));
        }

        layout.add(effectsLayout);

        layout.add(new Button("Add effect").setOnClick(b -> {
            SpellEffect e = new SpellEffect("New effect", "", new ValueObject(), ResourceManager.getResource("tesitems:magic/spellEffectDefaultTexture"), 0f, 0f);
            SpellEffect[] old = spell.effects;
            spell.effects = new SpellEffect[spell.effects.length + 1];
            System.arraycopy(old, 0, spell.effects, 0, old.length);
            spell.effects[spell.effects.length - 1] = e;
            effectsLayout.add(getEffectElement(e));
            root.doLayout();
        }));

        contentLayout.setElement(new ScrollableLayout().setElement(layout).setPadding(4));
        root.doLayout();
    }

    private ElementBase getEffectElement(SpellEffect effect) {
        final DoubleStateFrameLayout layout = new DoubleStateFrameLayout();
        Text availability = new Text("Available").setColor(0xff00ff00);
        layout.setFirstState(new Text(effect.getName()).setWidthOverride(ElementBase.FILL).setOnClick(e -> layout.selectSecond()))
                .setSecondState(new WrapFrameLayout().setElement(
                        new VerticalLayout()
                                .add(new Text("Close").setOnClick(e -> layout.selectFirst()))
                                .add(new TextField().setHint("Effect name").setOnType(tf -> {
                                    String name = tf.getText();
                                    SpellEffect effect1 = SpellEffectManager.get(name);
                                    if (effect1 != null)
                                        availability.setText("Available").setColor(0xff00ff00);
                                    else
                                        availability.setText("Unavailable").setColor(0xffff0000);
                                }))
                                .add(availability)
                ).setWidthOverride(ElementBase.FILL)).selectFirst().setWidthOverride(ElementBase.FILL);
        return layout;
    }
}
