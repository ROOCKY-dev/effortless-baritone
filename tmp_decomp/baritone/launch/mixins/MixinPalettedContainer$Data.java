/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BitStorage
 *  net.minecraft.world.level.chunk.Palette
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package baritone.launch.mixins;

import baritone.fr;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.chunk.Palette;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets={"net/minecraft/world/level/chunk/PalettedContainer$Data"})
public abstract class MixinPalettedContainer$Data<T>
implements fr.a<T> {
    @Override
    @Accessor
    public abstract Palette<T> getPalette();

    @Override
    @Accessor
    public abstract BitStorage getStorage();
}

