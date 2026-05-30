/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.sound;

import dev.huskuraft.effortless.api.platform.PlatformReference;
import dev.huskuraft.effortless.api.sound.Sound;

public interface SoundSet
extends PlatformReference {
    public float volume();

    public float pitch();

    public Sound breakSound();

    public Sound stepSound();

    public Sound placeSound();

    public Sound hitSound();

    public Sound fallSound();
}
