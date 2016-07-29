package ru.iammaxim.tesitems.Magic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Maxim on 11.07.2016.
 */
public class EntityRangedSpellEffect extends Entity {
    private SpellEffectBase effect;
    private float range;
    private EntityPlayer caster;

    public EntityRangedSpellEffect(World worldIn) {
        super(worldIn);
    }

    public void setCaster(EntityPlayer caster) {
        this.caster = caster;
    }

    public void setEffect(SpellEffectBase effect) {
        this.effect = effect;
    }

    public void setRange(float range) {
        this.range = range;
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {}

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));
        entities.forEach((entity) -> {
            effect.castTargetEntity(caster, entity);
        });
        kill();
    }
}
