/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.renderer;

public class RenderFadeEntry<T> {
    private static final int FADE_TICKS = 5;
    private final T value;
    private int ticksTillRemoval;

    public RenderFadeEntry(T value) {
        this.value = value;
        this.ticksTillRemoval = 1;
    }

    public void tick() {
        --this.ticksTillRemoval;
    }

    public boolean isAlive() {
        return this.ticksTillRemoval >= -5;
    }

    public boolean isFading() {
        return this.ticksTillRemoval < 0;
    }

    public T getValue() {
        return this.value;
    }
}
