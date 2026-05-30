/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.schematic.mask;

import baritone.api.schematic.mask.AbstractMask;
import baritone.api.schematic.mask.StaticMask;

final class PreComputedMask
extends AbstractMask
implements StaticMask {
    private final boolean[][][] mask = new boolean[this.heightY()][this.lengthZ()][this.widthX()];

    public PreComputedMask(StaticMask staticMask) {
        super(staticMask.widthX(), staticMask.heightY(), staticMask.lengthZ());
        for (int i2 = 0; i2 < this.heightY(); ++i2) {
            for (int i3 = 0; i3 < this.lengthZ(); ++i3) {
                for (int i4 = 0; i4 < this.widthX(); ++i4) {
                    this.mask[i2][i3][i4] = staticMask.partOfMask(i4, i2, i3);
                }
            }
        }
    }

    @Override
    public final boolean partOfMask(int n2, int n3, int n4) {
        return this.mask[n3][n4][n2];
    }
}

