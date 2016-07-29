package ru.iammaxim.tesitems.Magic.SpellEffects;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import ru.iammaxim.tesitems.Magic.SpellEffectBase;

/**
 * Created by Maxim on 11.07.2016.
 */
public class SpellEffectHeal extends SpellEffectBase {
    private static final ResourceLocation texture = new ResourceLocation("tesitems:textures/magic/spellEffectHeal.png");

    @Override
    protected ResourceLocation getTexture() {
        return texture;
    }

    public SpellEffectHeal(Float power, Float range) {
        super(power, range);
    }

    @Override
    protected void applyEffect(EntityLivingBase entity) {
        entity.heal(power);
    }

    @Override
    public void castSelf(EntityPlayer caster) {
        applyEffect(caster);
    }

    @Override
    public void castTargetEntity(EntityPlayer caster, EntityLivingBase target) {
        applyEffect(target);
    }

    @Override
    public void castTargetTerrain(EntityPlayer caster, Vec3d castPos) {
    }

    @Override
    public void renderInWorld(RenderManager renderManager, double x, double y, double z, float partialTicks) {
        renderFlatTextureModel(renderManager, x, y, z, partialTicks);
    }
}
