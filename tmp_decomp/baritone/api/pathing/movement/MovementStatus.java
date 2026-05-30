/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.pathing.movement;

public enum MovementStatus {
    PREPPING(false),
    WAITING(false),
    RUNNING(false),
    SUCCESS(true),
    UNREACHABLE(true),
    FAILED(true),
    CANCELED(true);

    private final boolean complete;

    private MovementStatus(boolean bl2) {
        this.complete = bl2;
    }

    public final boolean isComplete() {
        return this.complete;
    }
}

