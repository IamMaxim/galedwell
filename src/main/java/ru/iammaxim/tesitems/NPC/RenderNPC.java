package ru.iammaxim.tesitems.NPC;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Maxim on 19.07.2016.
 */
public class RenderNPC extends RenderLivingBase<EntityNPC> {
    private boolean smallArms;
    private static final ResourceLocation texture = new ResourceLocation("tesitems:textures/models/npc/default.png");

    public RenderNPC(RenderManager renderManager) {
        this(renderManager, false);
    }

    public RenderNPC(RenderManager renderManager, boolean useSmallArms) {
        super(renderManager, new ModelPlayer(0.0f, useSmallArms), 0.5F); //changed 0.0f to 1.0f
        smallArms = useSmallArms;
        addLayer(new LayerBipedArmor(this));
        addLayer(new LayerHeldItem(this));
        addLayer(new LayerArrow(this));
        addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
    }

    public ModelPlayer getMainModel() {
        return (ModelPlayer) super.getMainModel();
    }

    public void doRender(EntityNPC entity, double x, double y, double z, float entityYaw, float partialTicks) {
        double d0 = y;

            /* //never sneaking
            if (entity.isSneaking()) {
                d0 = y - 0.125D;
            }
            */
        setModelVisibilities(entity);
        GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
        super.doRender(entity, x, d0, z, entityYaw, partialTicks);
        GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityNPC entity) {
        return DefaultPlayerSkin.getDefaultSkin(Minecraft.getMinecraft().thePlayer.getUniqueID());
        //return texture;
    }

    private void setModelVisibilities(EntityNPC npc) {
        ModelPlayer model = this.getMainModel();
        ItemStack mainItem = npc.getHeldItemMainhand();
        ItemStack offItem = npc.getHeldItemOffhand();
        model.setInvisible(true);
        model.bipedHeadwear.showModel = npc.isWearing(EnumPlayerModelParts.HAT);
        model.bipedBodyWear.showModel = npc.isWearing(EnumPlayerModelParts.JACKET);
        model.bipedLeftLegwear.showModel = npc.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
        model.bipedRightLegwear.showModel = npc.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
        model.bipedLeftArmwear.showModel = npc.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
        model.bipedRightArmwear.showModel = npc.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
        model.isSneak = npc.isSneaking();
        ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
        ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

        if (mainItem != null) {
            modelbiped$armpose = ModelBiped.ArmPose.ITEM;

            if (npc.getItemInUseCount() > 0) {
                EnumAction enumaction = mainItem.getItemUseAction();

                if (enumaction == EnumAction.BLOCK) {
                    modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
                } else if (enumaction == EnumAction.BOW) {
                    modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
                }
            }
        }

        if (offItem != null) {
            modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

            if (npc.getItemInUseCount() > 0) {
                EnumAction enumaction1 = offItem.getItemUseAction();

                if (enumaction1 == EnumAction.BLOCK) {
                    modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
                }
            }
        }

        if (npc.getPrimaryHand() == EnumHandSide.RIGHT) {
            model.rightArmPose = modelbiped$armpose;
            model.leftArmPose = modelbiped$armpose1;
        } else {
            model.rightArmPose = modelbiped$armpose1;
            model.leftArmPose = modelbiped$armpose;
        }
    }

    protected ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
        return entity.getLocationSkin();
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

    protected void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
        float f = 0.9375F;
        GlStateManager.scale(f, f, f);
    }

    public void renderRightArm(EntityNPC npc) {
        float f = 1.0F;
        GlStateManager.color(f, f, f);
        float f1 = 0.0625F;
        ModelPlayer modelplayer = this.getMainModel();
        setModelVisibilities(npc);
        GlStateManager.enableBlend();
        modelplayer.swingProgress = 0.0F;
        modelplayer.isSneak = false;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, npc);
        modelplayer.bipedRightArm.rotateAngleX = 0.0F;
        modelplayer.bipedRightArm.render(0.0625F);
        modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedRightArmwear.render(0.0625F);
        GlStateManager.disableBlend();
    }

    public void renderLeftArm(EntityNPC npc) {
        float f = 1.0F;
        GlStateManager.color(f, f, f);
        float f1 = 0.0625F;
        ModelPlayer modelplayer = this.getMainModel();
        setModelVisibilities(npc);
        GlStateManager.enableBlend();
        modelplayer.isSneak = false;
        modelplayer.swingProgress = 0.0F;
        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, npc);
        modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArm.render(0.0625F);
        modelplayer.bipedLeftArmwear.rotateAngleX = 0.0F;
        modelplayer.bipedLeftArmwear.render(0.0625F);
        GlStateManager.disableBlend();
    }
}
