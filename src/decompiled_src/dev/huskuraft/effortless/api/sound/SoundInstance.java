/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.sound;

import dev.huskuraft.effortless.api.math.Vector3d;
import dev.huskuraft.effortless.api.math.Vector3f;
import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.sound.Sound;
import dev.huskuraft.effortless.api.sound.SoundFactory;
import dev.huskuraft.effortless.api.sound.SoundSource;

public interface SoundInstance
extends PlatformReference {
    public static SoundInstance create(Sound sound, SoundSource source, float volume, float pitch, boolean looping, int delay, Attenuation attenuation, double x, double y, double z, boolean relative) {
        return SoundFactory.getInstance().createSimpleSoundInstance(sound.getId(), source, volume, pitch, looping, delay, attenuation, x, y, z, relative);
    }

    public static SoundInstance createMaster(Sound sound, float volume, float pitch) {
        return SoundInstance.create(sound, SoundSource.MASTER, volume, pitch, false, 0, Attenuation.NONE, 0.0, 0.0, 0.0, true);
    }

    public static SoundInstance createMaster(Sound sound, float pitch) {
        return SoundInstance.createMaster(sound, 0.25f, pitch);
    }

    public static SoundInstance createMusic(Sound sound) {
        return SoundInstance.create(sound, SoundSource.MUSIC, 1.0f, 1.0f, false, 0, Attenuation.NONE, 0.0, 0.0, 0.0, true);
    }

    public static SoundInstance createRecord(Sound sound, Vector3f location) {
        return SoundInstance.create(sound, SoundSource.RECORDS, 4.0f, 1.0f, false, 0, Attenuation.LINEAR, location.x(), location.y(), location.z(), false);
    }

    public static SoundInstance createBlock(Sound sound, float volume, float pitch, Vector3d location) {
        return SoundInstance.create(sound, SoundSource.BLOCKS, volume, pitch, false, 0, Attenuation.LINEAR, location.x(), location.y(), location.z(), false);
    }

    public static enum Attenuation {
        NONE,
        LINEAR;

    }
}
