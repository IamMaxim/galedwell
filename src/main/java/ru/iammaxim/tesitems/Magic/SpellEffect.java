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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import ru.iammaxim.tesitems.ResourceManager;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.GaledwellLang;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Entity.ValueEntity;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Objects.Player.ValuePlayer;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Parser.InvalidTokenException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFloat;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.Scripting.ScriptEngine;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 11.07.2016.
 */
public class SpellEffect {
    private String name;
    protected float power, range;
    private ValueObject object;
    private ResourceLocation texture;
    private String script;

    public SpellEffect(String name, String script, ValueObject object, ResourceLocation texture, Float power, Float range) {
        this.name = name;
        this.script = script;
        this.power = power;
        this.range = range;
        this.object = object;
        this.texture = texture;
    }

    public SpellEffect() {
    }

    public static SpellEffect readFromNBT(NBTTagCompound tag) {
        SpellEffect effect = new SpellEffect();
        effect.name = tag.getString("name");
        effect.script = tag.getString("script");
        effect.power = tag.getFloat("power");
        effect.range = tag.getFloat("range");
        effect.object = new ValueObject();
        if (TESItems.getSide() == Side.SERVER)
            try {
                GaledwellLang.loadSrcInto(effect.script, effect.object);
            } catch (InvalidTokenException e) {
                e.printStackTrace();
            }
        effect.texture = ResourceManager.getResource(tag.getString("texture"));

        return effect;
    }

    protected ResourceLocation getTexture() {
        return texture;
    }

    public float getPower() {
        return power;
    }

    public float getRange() {
        return range;
    }

    protected void applyEffect(EntityLivingBase entity, float rangeNormalized) {
        ValueFunction applyEffect = (ValueFunction) object.getField("applyEffect");

        if (applyEffect != null)
            try {
                applyEffect.call(ScriptEngine.runtime, object, new ValueEntity(entity), new ValueFloat(power), new ValueFloat(1.0f));
            } catch (InvalidOperationException e) {
                e.printStackTrace();
            }
    }

    public void castSelf(EntityPlayer caster) {
        ValueFunction applyEffect = (ValueFunction) object.getField("castSelf");

        if (applyEffect != null)
            try {
                applyEffect.call(ScriptEngine.runtime, object, new ValuePlayer(caster), new ValueFloat(power));
            } catch (InvalidOperationException e) {
                e.printStackTrace();
            }
    }

    public void castTargetEntity(EntityPlayer caster, EntityLivingBase target, float rangeNormalized) {
        ValueFunction applyEffect = (ValueFunction) object.getField("castTarget");

        if (applyEffect != null)
            try {
                applyEffect.call(ScriptEngine.runtime, object, new ValuePlayer(caster), new ValueEntity(target), new ValueFloat(power), new ValueFloat(rangeNormalized));
            } catch (InvalidOperationException e) {
                e.printStackTrace();
            }
    }

    public void castTargetTerrain(EntityPlayer caster, Vec3d castPos) {
        ValueFunction applyEffect = (ValueFunction) object.getField("castTerrain");

        if (applyEffect != null)
            try {
                applyEffect.call(ScriptEngine.runtime, object, new ValuePlayer(caster), new ValueFloat(power));
            } catch (InvalidOperationException e) {
                e.printStackTrace();
            }
    }

    public void renderInWorld(RenderManager renderManager, double x, double y, double z, float partialTicks) {
        renderFlatTextureModel(renderManager, x, y, z, partialTicks);
    }

    protected void renderFlatTextureModel(RenderManager renderManager, double x, double y, double z, float partialTicks) {
        //renderManager.renderEngine.bindTexture(getTexture());

        GlStateManager.pushMatrix();
        renderManager.renderEngine.bindTexture(getTexture());
        GlStateManager.translate((float) x, (float) y, (float) z);
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
        GlStateManager.rotate((float) (renderManager.options.thirdPersonView == 2 ? -1 : 1) * -renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        vertexbuffer.pos(-0.5D, -0.25D, 0.0D).tex((double) f, (double) f3).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(0.5D, -0.25D, 0.0D).tex((double) f1, (double) f3).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(0.5D, 0.75D, 0.0D).tex((double) f1, (double) f2).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(-0.5D, 0.75D, 0.0D).tex((double) f, (double) f2).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    ;

    public NBTTagCompound writeToNBT(boolean writeScripts) {
        NBTTagCompound tag = new NBTTagCompound();
        if (writeScripts)
            tag.setString("script", script);
        else
            tag.setString("script", "");
        tag.setString("name", name);
        tag.setFloat("power", power);
        tag.setFloat("range", range);
        tag.setString("texture", texture.toString());
        return tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}