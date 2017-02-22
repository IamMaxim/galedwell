package ru.iammaxim.tesitems.Magic.SpellTypes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import ru.iammaxim.tesitems.Magic.EntityFlyingSpell;
import ru.iammaxim.tesitems.Magic.SpellBase;
import ru.iammaxim.tesitems.Magic.SpellEffect;

/**
 * Created by Maxim on 17.07.2016.
 */
public class SpellBaseTarget extends SpellBase {
    public SpellBaseTarget(String name, SpellEffect... effects) {
        super(name, effects);
    }

    @Override
    public void cast(EntityPlayer caster) {
        EntityFlyingSpell entity = new EntityFlyingSpell(caster.worldObj);
        entity.setPosition(caster.posX, caster.posY, caster.posZ);
        entity.motionX = -MathHelper.sin(caster.rotationYawHead * 0.017453292F) * MathHelper.cos(caster.rotationPitch * 0.017453292F);
        entity.motionY = -MathHelper.sin((caster.rotationPitch) * 0.017453292F);
        entity.motionZ = MathHelper.cos(caster.rotationYawHead * 0.017453292F) * MathHelper.cos(caster.rotationPitch * 0.017453292F);
        entity.motionX += caster.motionX;
        entity.motionZ += caster.motionZ;
        if (!caster.onGround) entity.motionY += caster.motionY;
    }
}
