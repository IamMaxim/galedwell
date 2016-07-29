package ru.iammaxim.tesitems.Magic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import ru.iammaxim.tesitems.Magic.SpellTypes.SpellBaseSelf;
import ru.iammaxim.tesitems.Magic.SpellTypes.SpellBaseTarget;

public abstract class SpellBase {
    private String name;
    public SpellEffectBase[] effects;

    /*
    SPELLTYPES:
    1 - self
    2 - target terrain
    3 - target entity
     */

    private int spellType;

    protected static final float MAX_DISTANCE = 100;
    protected static final float PARTIAL_TICK_TIME = 1.0f;

    public SpellBase(String name, SpellEffectBase... effects) {
        this.name = name;
        this.effects = effects;
    }

    public String getName() {
        return name;
    }

    public RayTraceResult rayTrace(EntityPlayer player, double distance) {
        Vec3d vec3 = getPosition(player, PARTIAL_TICK_TIME);
        Vec3d vec31 = player.getLook(PARTIAL_TICK_TIME);
        Vec3d vec32 = vec3.addVector(vec31.xCoord * distance, vec31.yCoord * distance, vec31.zCoord * distance);
        return player.worldObj.rayTraceBlocks(vec3, vec32, false, false, true);
    }

    public Vec3d getPosition(EntityPlayer player, float partialTickTime) {
        if (partialTickTime == 1.0F) {
            return new Vec3d(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
        } else {
            double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)partialTickTime;
            double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)partialTickTime + (player.getEyeHeight() - player.getDefaultEyeHeight());
            double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTickTime;
            return new Vec3d(d0, d1, d2);
        }
    }

    public abstract void cast(EntityPlayer caster);

    public int getSpellType() {
        return spellType;
    }

    public void setSpellType(int spellType) {
        this.spellType = spellType;
    }

    public static SpellBase createSpell(int spellType, String name, SpellEffectBase[] effects) {
        switch (spellType) {
            case 0: //cast self
                return new SpellBaseSelf(name, effects);
            case 1: //cast target
                return new SpellBaseTarget(name, effects);
            default:
                return null;
        }
    }
}
