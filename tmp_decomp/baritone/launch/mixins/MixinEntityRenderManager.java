/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.EntityRenderDispatcher
 *  org.spongepowered.asm.mixin.Mixin
 */
package baritone.launch.mixins;

import baritone.fp;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={EntityRenderDispatcher.class})
public class MixinEntityRenderManager
implements fp {
    @Override
    public double renderPosX() {
        return ((EntityRenderDispatcher)this).camera.getPosition().x;
    }

    @Override
    public double renderPosY() {
        return ((EntityRenderDispatcher)this).camera.getPosition().y;
    }

    @Override
    public double renderPosZ() {
        return ((EntityRenderDispatcher)this).camera.getPosition().z;
    }
}

