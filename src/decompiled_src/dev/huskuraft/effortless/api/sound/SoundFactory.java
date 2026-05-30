/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.sound;

import dev.huskuraft.effortless.api.core.ResourceLocation;
import dev.huskuraft.effortless.api.platform.PlatformLoader;
import dev.huskuraft.effortless.api.sound.SoundInstance;
import dev.huskuraft.effortless.api.sound.SoundSource;

public interface SoundFactory {
    public static SoundFactory getInstance() {
        return PlatformLoader.getSingleton(new SoundFactory[0]);
    }

    public SoundInstance createSimpleSoundInstance(ResourceLocation var1, SoundSource var2, float var3, float var4, boolean var5, int var6, SoundInstance.Attenuation var7, double var8, double var10, double var12, boolean var14);
}
