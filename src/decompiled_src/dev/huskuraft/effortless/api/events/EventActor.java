/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events;

import dev.huskuraft.effortless.api.events.EventResult;

@FunctionalInterface
public interface EventActor<T> {
    public EventResult get(T var1);
}
