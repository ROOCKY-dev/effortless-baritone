/*
 * Decompiled with CFR 0.152.
 */
package dev.huskuraft.effortless.api.events.input;

import dev.huskuraft.effortless.api.core.InteractionHand;
import dev.huskuraft.effortless.api.core.InteractionType;
import dev.huskuraft.effortless.api.events.EventResult;

public interface InteractionInput {
    public EventResult onInteractionInput(InteractionType var1, InteractionHand var2);
}
