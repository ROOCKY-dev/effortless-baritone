/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package baritone.api.event.events;

import net.minecraft.core.BlockPos;

public final class BlockInteractEvent {
    private final BlockPos pos;
    private final Type type;

    public BlockInteractEvent(BlockPos blockPos, Type type) {
        this.pos = blockPos;
        this.type = type;
    }

    public final BlockPos getPos() {
        return this.pos;
    }

    public final Type getType() {
        return this.type;
    }

    public static enum Type {
        START_BREAK,
        USE;

    }
}

