/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.schematic.mask.shape;

import baritone.api.schematic.mask.AbstractMask;
import baritone.api.schematic.mask.StaticMask;

public final class SphereMask
extends AbstractMask
implements StaticMask {
    private final double centerX;
    private final double centerY;
    private final double centerZ;
    private final double radiusSqX;
    private final double radiusSqY;
    private final double radiusSqZ;
    private final boolean filled;

    public SphereMask(int n2, int n3, int n4, boolean bl2) {
        super(n2, n3, n4);
        this.centerX = (double)n2 / 2.0;
        this.centerY = (double)n3 / 2.0;
        this.centerZ = (double)n4 / 2.0;
        this.radiusSqX = this.centerX * this.centerX;
        this.radiusSqY = this.centerY * this.centerY;
        this.radiusSqZ = this.centerZ * this.centerZ;
        this.filled = bl2;
    }

    @Override
    public final boolean partOfMask(int n2, int n3, int n4) {
        double d2;
        double d3;
        double d4 = Math.abs((double)n2 + 0.5 - this.centerX);
        if (this.outside(d4, d3 = Math.abs((double)n3 + 0.5 - this.centerY), d2 = Math.abs((double)n4 + 0.5 - this.centerZ))) {
            return false;
        }
        return this.filled || this.outside(d4 + 1.0, d3, d2) || this.outside(d4, d3 + 1.0, d2) || this.outside(d4, d3, d2 + 1.0);
    }

    private boolean outside(double d2, double d3, double d4) {
        double d5 = d2;
        double d6 = d3;
        double d7 = d4;
        return d5 * d5 / this.radiusSqX + d6 * d6 / this.radiusSqY + d7 * d7 / this.radiusSqZ > 1.0;
    }
}

