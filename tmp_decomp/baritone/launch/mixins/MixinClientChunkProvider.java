/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientChunkCache
 *  net.minecraft.client.multiplayer.ClientLevel
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package baritone.launch.mixins;

import baritone.fn;
import baritone.fo;
import java.lang.reflect.Field;
import java.util.Arrays;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={ClientChunkCache.class})
public class MixinClientChunkProvider
implements fo {
    @Final
    @Shadow
    ClientLevel level;

    @Override
    public ClientChunkCache createThreadSafeCopy() {
        fn fn2 = this.extractReferenceArray();
        ClientChunkCache clientChunkCache = new ClientChunkCache(this.level, fn2.viewDistance() - 3);
        fn fn3 = ((fo)clientChunkCache).extractReferenceArray();
        fn3.copyFrom(fn2);
        if (fn3.viewDistance() != fn2.viewDistance()) {
            throw new IllegalStateException(fn3.viewDistance() + " " + fn2.viewDistance());
        }
        return clientChunkCache;
    }

    @Override
    public fn extractReferenceArray() {
        for (Field field : ClientChunkCache.class.getDeclaredFields()) {
            if (!fn.class.isAssignableFrom(field.getType())) continue;
            try {
                return (fn)field.get(this);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException(illegalAccessException);
            }
        }
        throw new RuntimeException(Arrays.toString(ClientChunkCache.class.getDeclaredFields()));
    }
}

