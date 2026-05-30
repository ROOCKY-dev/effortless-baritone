/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.datatypes;

import baritone.api.IBaritone;
import baritone.api.command.argument.IArgConsumer;

public interface IDatatypeContext {
    public IBaritone getBaritone();

    public IArgConsumer getConsumer();
}

