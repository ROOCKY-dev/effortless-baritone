/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.event.events;

import baritone.api.event.events.type.EventState;
import java.util.function.BiFunction;

public final class TickEvent {
    private static int overallTickCount;
    private final EventState state;
    private final Type type;
    private final int count;

    public TickEvent(EventState eventState, Type type, int n2) {
        this.state = eventState;
        this.type = type;
        this.count = n2;
    }

    public final int getCount() {
        return this.count;
    }

    public final Type getType() {
        return this.type;
    }

    public final EventState getState() {
        return this.state;
    }

    public static synchronized BiFunction<EventState, Type, TickEvent> createNextProvider() {
        return (arg_0, arg_1) -> TickEvent.lambda$createNextProvider$0(overallTickCount++, arg_0, arg_1);
    }

    private static /* synthetic */ TickEvent lambda$createNextProvider$0(int n2, EventState eventState, Type type) {
        return new TickEvent(eventState, type, n2);
    }

    public static enum Type {
        IN,
        OUT;

    }
}

