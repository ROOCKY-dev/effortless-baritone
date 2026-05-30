/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.nightconfig.core.utils;

abstract class AbstractObserved {
    protected final Runnable callback;

    protected AbstractObserved(Runnable callback) {
        this.callback = callback;
    }
}
