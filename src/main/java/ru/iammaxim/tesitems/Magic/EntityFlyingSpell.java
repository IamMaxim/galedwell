package ru.iammaxim.tesitems.Magic;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Maxim on 19.07.2016.
 */
public class EntityFlyingSpell extends Entity {
    private SpellBase spell;
    private int liveTime = 0;

    public EntityFlyingSpell(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    public void setSpell(SpellBase spell) {
        this.spell = spell;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (worldObj.getBlockState(new BlockPos(posX, posY, posZ)).getBlock() != Blocks.AIR) {
            System.out.println("Collision detected at " + posX + " " + posY + " " + posZ);
            for (SpellEffect effect : spell.effects) {
                EntityRangedSpellEffect entity = new EntityRangedSpellEffect(worldObj);
                entity.setPosition(posX, posY, posZ);
                entity.setRange(effect.getRange());
                entity.setEffect(effect);
                worldObj.spawnEntityInWorld(entity);
            }
        }
        if (liveTime == 80) kill();
        liveTime++;
    }
}
