/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.MultiPlayerGameMode
 *  net.minecraft.core.BlockPos
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package baritone.launch.mixins;

import baritone.fs;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={MultiPlayerGameMode.class})
public abstract class MixinPlayerController
implements fs {
    @Override
    @Accessor(value="isDestroying")
    public abstract void setIsHittingBlock(boolean var1);

    @Override
    @Accessor(value="isDestroying")
    public abstract boolean isHittingBlock();

    @Accessor(value="destroyBlockPos")
    public abstract BlockPos getCurrentBlock();

    @Override
    @Invoker(value="ensureHasSentCarriedItem")
    public abstract void callSyncCurrentPlayItem();

    @Override
    @Accessor(value="destroyDelay")
    public abstract void setDestroyDelay(int var1);
}

