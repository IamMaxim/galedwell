package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Magic.SpellEffect;
import ru.iammaxim.tesitems.Magic.SpellTypes.SpellBaseSelf;
import ru.iammaxim.tesitems.Magic.SpellTypes.SpellBaseTarget;
import ru.iammaxim.tesitems.Networking.MessageSpell;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.ResourceManager;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.TESItems;
import ru.iammaxim.tesitems.Utils.ClientThings;

/**
 * Created by maxim on 2/21/17 at 11:15 PM.
 */
public class GuiSpellEditor extends Screen implements IGuiUpdatable {
    public SpellBase spell;
    public VerticalLayout effectsLayout;
    IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());
    private int index = -1;
    private boolean updated = true;

    public GuiSpellEditor(SpellBase spell) {
        if (spell != null) {
            this.spell = spell.copy();
            if (this.spell == null) {
                System.out.println("U COPIED SPELL TO NULL! HOW U MADE IT!??");
                ScreenStack.forceClose();
            }
            index = cap.getSpellbook().indexOf(spell);
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
                        if (i == 0) { // self
                            this.spell = new SpellBaseSelf("");
                        } else if (i == 1) { // target
                            this.spell = new SpellBaseTarget("");
                        }
                        init();
                        show();
                    }).show();
        }

        root.getScreen().addCallback("spellIndexChanged", o -> {
            index = (int) o;
        });

        root.getScreen().addCallback("update", o -> update());
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
                // convert to target
                spell = new SpellBaseTarget(spell.name, spell.effects);
                init();
            } else if (spell.getSpellType() == SpellBase.TARGET) {
                // convert to self
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

        layout.add(new Button("Save").setOnClick(b -> {
            if (!updated())
                return;
            TESItems.networkWrapper.sendToServer(new MessageSpell(spell, index));
            new GuiAlertDialog("Spell saved").show();
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
//                                .add(new TextField().setHint("Effect name").setOnType(tf -> {
//                                    String name = tf.getText();
//                                    SpellEffect effect1 = SpellEffectManager.get(name);
//                                    if (effect1 != null)
//                                        availability.setText("Available").setColor(0xff00ff00);
//                                    else
//                                        availability.setText("Unavailable").setColor(0xffff0000);
//                                }))
//                                .add(availability)
                                .add(new TextField().setHint("Name").setText(effect.getName()).setOnType(tf -> effect.setName(tf.getText())))
                                .add(new HorizontalDivider())
                                .add(new Text("Power"))
                                .add(new TextField().setHint("Power").setText(String.valueOf(effect.getPower())).setOnType(tf -> {
                                    try {
                                        effect.setPower(Float.parseFloat(tf.getText()));
                                    } catch (NumberFormatException e) {
                                    }
                                }))
                                .add(new Text("Range"))
                                .add(new TextField().setHint("Range").setText(String.valueOf(effect.getRange())).setOnType(tf -> {
                                    try {
                                        effect.setRange(Float.parseFloat(tf.getText()));
                                    } catch (NumberFormatException e) {
                                    }
                                }))
                                .add(new HorizontalDivider())
                                .add(new Text("Script"))
                                .add(new TextField().setHint("Script").setText(effect.getScript()).setFontRenderer(ClientThings.monospaceFontRenderer).setOnType(tf -> effect.setScript(tf.getText())))
                ).setWidthOverride(ElementBase.FILL)).selectFirst().setWidthOverride(ElementBase.FILL);
        return layout;
    }

    @Override
    public void update() {
        updated = true;
    }

    @Override
    public void unupdate() {
        updated = false;
    }

    @Override
    public boolean updated() {
        return updated;
    }
}
