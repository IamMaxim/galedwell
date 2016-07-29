package ru.iammaxim.tesitems.Magic;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Maxim on 19.07.2016.
 */
public class RenderEntityRangedSpellEffect<T extends Entity> extends Render<T> {
    private SpellEffectBase effect;

    public RenderEntityRangedSpellEffect(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    public void setEffect(SpellEffectBase effect) {
        this.effect = effect;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        effect.renderInWorld(renderManager, x, y, z, partialTicks);
    }
}
