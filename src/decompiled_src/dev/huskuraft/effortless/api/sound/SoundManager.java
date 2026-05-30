/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.sound;

import dev.huskuraft.effortless.api.sound.SoundInstance;
import dev.huskuraft.effortless.api.sound.Sounds;

public interface SoundManager {
    public void play(SoundInstance var1);

    public void stop(SoundInstance var1);

    public void playDelayed(SoundInstance var1, int var2);

    public boolean isActive(SoundInstance var1);

    public void pause();

    public void stop();

    public void destroy();

    public void resume();

    default public void playButtonClickSound() {
        this.playButtonClickSound(0.2f);
    }

    default public void playButtonClickSound(float volume) {
        this.play(SoundInstance.createMaster(Sounds.UI_BUTTON_CLICK.sound(), volume, 0.75f));
    }
}
