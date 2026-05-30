/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.event.events;

import baritone.api.event.events.type.EventState;

public final class ChunkEvent {
    private final EventState state;
    private final Type type;
    private final int x;
    private final int z;

    public ChunkEvent(EventState eventState, Type type, int n2, int n3) {
        this.state = eventState;
        this.type = type;
        this.x = n2;
        this.z = n3;
    }

    public final EventState getState() {
        return this.state;
    }

    public final Type getType() {
        return this.type;
    }

    public final int getX() {
        return this.x;
    }

    public final int getZ() {
        return this.z;
    }

    public final boolean isPostPopulate() {
        return this.state == EventState.POST && this.type.isPopulate();
    }

    public static enum Type {
        LOAD,
        UNLOAD,
        POPULATE_FULL,
        POPULATE_PARTIAL;


        public final boolean isPopulate() {
            return this == POPULATE_FULL || this == POPULATE_PARTIAL;
        }
    }
}

