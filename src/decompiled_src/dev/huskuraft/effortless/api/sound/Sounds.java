/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.sound;

import dev.huskuraft.effortless.api.platform.ContentFactory;
import dev.huskuraft.effortless.api.sound.Sound;

public enum Sounds {
    UI_BUTTON_CLICK,
    UI_TOAST_IN,
    UI_TOAST_OUT;


    public Sound sound() {
        return ContentFactory.getInstance().getSound(this);
    }
}
