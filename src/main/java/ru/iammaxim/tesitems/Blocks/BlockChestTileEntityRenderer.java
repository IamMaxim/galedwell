package ru.iammaxim.tesitems.Blocks;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by maxim on 02.03.2017.
 */
public class BlockChestTileEntityRenderer extends TileEntitySpecialRenderer<BlockChestTileEntity> {
    private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation("textures/entity/chest/normal.png");
    private final ModelChest simpleChest = new ModelChest();

    public BlockChestTileEntityRenderer() {
    }

    @Override
    public void renderTileEntityAt(BlockChestTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        ModelChest modelchest;

        modelchest = this.simpleChest;

        if (destroyStage >= 0) {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            this.bindTexture(TEXTURE_NORMAL);
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();

        if (destroyStage < 0) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }

        GlStateManager.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        int j = 0;
        //rotate block
        if (te != null) {
            int i = te.getBlockMetadata();
            if (i == 2) j = 180;
            if (i == 3) j = 0;
            if (i == 4) j = 90;
            if (i == 5) j = -90;
        }

        GlStateManager.rotate((float) j, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
//        float f = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
        float f = 0;


//        f = 1.0F - f;
//        f = 1.0F - f * f * f;
        modelchest.chestLid.rotateAngleX = -(f * ((float) Math.PI / 2F));
        modelchest.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
