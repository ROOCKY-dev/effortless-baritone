/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.border.WorldBorder
 */
package baritone;

import net.minecraft.world.level.border.WorldBorder;

public final class fu {
    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public fu(WorldBorder worldBorder) {
        this.a = worldBorder.getMinX();
        this.b = worldBorder.getMaxX();
        this.c = worldBorder.getMinZ();
        this.d = worldBorder.getMaxZ();
    }

    public final boolean a(int n2, int n3) {
        return (double)(n2 + 1) > this.a && (double)n2 < this.b && (double)(n3 + 1) > this.c && (double)n3 < this.d;
    }

    public final boolean b(int n2, int n3) {
        return (double)n2 > this.a && (double)(n2 + 1) < this.b && (double)n3 > this.c && (double)(n3 + 1) < this.d;
    }
}

