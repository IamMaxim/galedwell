package ru.iammaxim.tesitems.Magic;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Maxim on 18.07.2016.
 */
public class RenderEntityFlyingSpell<T extends Entity> extends Render<T> {
    private SpellBase spell;

    public RenderEntityFlyingSpell(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    public void setSpell(SpellBase spell) {
        this.spell = spell;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        for(SpellEffectBase effect : spell.effects) {
            effect.renderInWorld(renderManager, x, y, z, partialTicks);
        }
    }
}
