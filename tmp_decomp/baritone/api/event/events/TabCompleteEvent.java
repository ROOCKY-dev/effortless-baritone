/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.event.events;

import baritone.api.event.events.type.Cancellable;

public final class TabCompleteEvent
extends Cancellable {
    public final String prefix;
    public String[] completions;

    public TabCompleteEvent(String string) {
        this.prefix = string;
        this.completions = null;
    }
}

