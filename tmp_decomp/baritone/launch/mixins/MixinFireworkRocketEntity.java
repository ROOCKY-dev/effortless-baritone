/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.FireworkRocketEntity
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package baritone.launch.mixins;

import baritone.fq;
import java.util.OptionalInt;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={FireworkRocketEntity.class})
public abstract class MixinFireworkRocketEntity
extends Entity
implements fq {
    @Shadow
    @Final
    private static EntityDataAccessor<OptionalInt> DATA_ATTACHED_TO_TARGET;
    @Shadow
    private LivingEntity attachedToEntity;

    @Shadow
    public abstract boolean isAttachedToEntity();

    private MixinFireworkRocketEntity(Level level) {
        super(EntityType.FIREWORK_ROCKET, level);
    }

    @Override
    public LivingEntity getBoostedEntity() {
        Entity entity;
        if (this.isAttachedToEntity() && this.attachedToEntity == null && (entity = this.level().getEntity(((OptionalInt)this.entityData.get(DATA_ATTACHED_TO_TARGET)).getAsInt())) instanceof LivingEntity) {
            this.attachedToEntity = (LivingEntity)entity;
        }
        return this.attachedToEntity;
    }
}

