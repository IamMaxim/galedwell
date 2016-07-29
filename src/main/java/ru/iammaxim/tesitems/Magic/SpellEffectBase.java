package ru.iammaxim.tesitems.Magic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

/**
 * Created by Maxim on 11.07.2016.
 */
public abstract class SpellEffectBase {
    protected abstract ResourceLocation getTexture();
    protected float power, range;
    public SpellEffectBase(Float power, Float range) {
        this.power = power;
        this.range = range;
    }
    public float getPower() {
        return power;
    }
    public float getRange() {
        return range;
    }
    protected abstract void applyEffect(EntityLivingBase entity);
    public abstract void castSelf(EntityPlayer caster);
    public abstract void castTargetEntity(EntityPlayer caster, EntityLivingBase target);
    public abstract void castTargetTerrain(EntityPlayer caster, Vec3d castPos);
    public abstract void renderInWorld(RenderManager renderManager, double x, double y, double z, float partialTicks);
    protected void renderFlatTextureModel(RenderManager renderManager, double x, double y, double z, float partialTicks) {
        //renderManager.renderEngine.bindTexture(getTexture());

        GlStateManager.pushMatrix();
        renderManager.renderEngine.bindTexture(getTexture());
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        //GlStateManager.scale(this.scale, this.scale, this.scale);
        TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Items.FIRE_CHARGE);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        float f = textureatlassprite.getMinU();
        float f1 = textureatlassprite.getMaxU();
        float f2 = textureatlassprite.getMinV();
        float f3 = textureatlassprite.getMaxV();
        GlStateManager.rotate(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(renderManager.options.thirdPersonView == 2 ? -1 : 1) * -renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        vertexbuffer.pos(-0.5D, -0.25D, 0.0D).tex((double)f, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(0.5D, -0.25D, 0.0D).tex((double)f1, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(0.5D, 0.75D, 0.0D).tex((double)f1, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(-0.5D, 0.75D, 0.0D).tex((double)f, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }
}