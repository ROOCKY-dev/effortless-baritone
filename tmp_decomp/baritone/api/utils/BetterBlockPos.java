/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.util.Mth
 */
package baritone.api.utils;

import baritone.api.utils.SettingsUtil;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

public final class BetterBlockPos
extends BlockPos {
    private static final int NUM_X_BITS = 26;
    private static final int NUM_Z_BITS = 26;
    private static final int NUM_Y_BITS = 12;
    private static final int Y_SHIFT = 26;
    private static final int X_SHIFT = 38;
    private static final long X_MASK = 0x3FFFFFFL;
    private static final long Y_MASK = 4095L;
    private static final long Z_MASK = 0x3FFFFFFL;
    public static final BetterBlockPos ORIGIN = new BetterBlockPos(0, 0, 0);
    public final int x;
    public final int y;
    public final int z;

    public BetterBlockPos(int n2, int n3, int n4) {
        super(n2, n3, n4);
        this.x = n2;
        this.y = n3;
        this.z = n4;
    }

    public BetterBlockPos(double d2, double d3, double d4) {
        this(Mth.floor((double)d2), Mth.floor((double)d3), Mth.floor((double)d4));
    }

    public BetterBlockPos(BlockPos blockPos) {
        this(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static BetterBlockPos from(BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        return new BetterBlockPos(blockPos);
    }

    public final int hashCode() {
        return (int)BetterBlockPos.longHash(this.x, this.y, this.z);
    }

    public static long longHash(BetterBlockPos betterBlockPos) {
        return BetterBlockPos.longHash(betterBlockPos.x, betterBlockPos.y, betterBlockPos.z);
    }

    public static long longHash(int n2, int n3, int n4) {
        long l2 = 11206370049L + (long)n2;
        l2 = 8734625L * l2 + (long)n3;
        return 2873465L * l2 + (long)n4;
    }

    public final boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof BetterBlockPos) {
            object = (BetterBlockPos)((Object)object);
            return ((BetterBlockPos)((Object)object)).x == this.x && ((BetterBlockPos)((Object)object)).y == this.y && ((BetterBlockPos)((Object)object)).z == this.z;
        }
        return (object = (BlockPos)object).getX() == this.x && object.getY() == this.y && object.getZ() == this.z;
    }

    public final BetterBlockPos above() {
        return new BetterBlockPos(this.x, this.y + 1, this.z);
    }

    public final BetterBlockPos above(int n2) {
        if (n2 == 0) {
            return this;
        }
        return new BetterBlockPos(this.x, this.y + n2, this.z);
    }

    public final BetterBlockPos below() {
        return new BetterBlockPos(this.x, this.y - 1, this.z);
    }

    public final BetterBlockPos below(int n2) {
        if (n2 == 0) {
            return this;
        }
        return new BetterBlockPos(this.x, this.y - n2, this.z);
    }

    public final BetterBlockPos relative(Direction direction) {
        direction = direction.getNormal();
        return new BetterBlockPos(this.x + direction.getX(), this.y + direction.getY(), this.z + direction.getZ());
    }

    public final BetterBlockPos relative(Direction direction, int n2) {
        if (n2 == 0) {
            return this;
        }
        direction = direction.getNormal();
        return new BetterBlockPos(this.x + direction.getX() * n2, this.y + direction.getY() * n2, this.z + direction.getZ() * n2);
    }

    public final BetterBlockPos north() {
        return new BetterBlockPos(this.x, this.y, this.z - 1);
    }

    public final BetterBlockPos north(int n2) {
        if (n2 == 0) {
            return this;
        }
        return new BetterBlockPos(this.x, this.y, this.z - n2);
    }

    public final BetterBlockPos south() {
        return new BetterBlockPos(this.x, this.y, this.z + 1);
    }

    public final BetterBlockPos south(int n2) {
        if (n2 == 0) {
            return this;
        }
        return new BetterBlockPos(this.x, this.y, this.z + n2);
    }

    public final BetterBlockPos east() {
        return new BetterBlockPos(this.x + 1, this.y, this.z);
    }

    public final BetterBlockPos east(int n2) {
        if (n2 == 0) {
            return this;
        }
        return new BetterBlockPos(this.x + n2, this.y, this.z);
    }

    public final BetterBlockPos west() {
        return new BetterBlockPos(this.x - 1, this.y, this.z);
    }

    public final BetterBlockPos west(int n2) {
        if (n2 == 0) {
            return this;
        }
        return new BetterBlockPos(this.x - n2, this.y, this.z);
    }

    public final double distanceSq(BetterBlockPos betterBlockPos) {
        double d2 = (double)this.x - (double)betterBlockPos.x;
        double d3 = (double)this.y - (double)betterBlockPos.y;
        double d4 = (double)this.z - (double)betterBlockPos.z;
        double d5 = d2;
        double d6 = d3;
        double d7 = d4;
        return d5 * d5 + d6 * d6 + d7 * d7;
    }

    public final double distanceTo(BetterBlockPos betterBlockPos) {
        double d2 = (double)this.x - (double)betterBlockPos.x;
        double d3 = (double)this.y - (double)betterBlockPos.y;
        double d4 = (double)this.z - (double)betterBlockPos.z;
        double d5 = d2;
        double d6 = d3;
        double d7 = d4;
        return Math.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
    }

    @Nonnull
    public final String toString() {
        return String.format("BetterBlockPos{x=%s,y=%s,z=%s}", SettingsUtil.maybeCensor(this.x), SettingsUtil.maybeCensor(this.y), SettingsUtil.maybeCensor(this.z));
    }

    public static long serializeToLong(int n2, int n3, int n4) {
        return ((long)n2 & 0x3FFFFFFL) << 38 | ((long)n3 & 0xFFFL) << 26 | (long)n4 & 0x3FFFFFFL;
    }

    public static BetterBlockPos deserializeFromLong(long l2) {
        int n2 = (int)(l2 >> 38);
        int n3 = (int)(l2 << 26 >> 52);
        int n4 = (int)(l2 << 38 >> 38);
        return new BetterBlockPos(n2, n3, n4);
    }
}

