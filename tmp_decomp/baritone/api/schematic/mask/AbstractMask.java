/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.schematic.mask;

import baritone.api.schematic.mask.Mask;

public abstract class AbstractMask
implements Mask {
    private final int widthX;
    private final int heightY;
    private final int lengthZ;

    public AbstractMask(int n2, int n3, int n4) {
        this.widthX = n2;
        this.heightY = n3;
        this.lengthZ = n4;
    }

    @Override
    public int widthX() {
        return this.widthX;
    }

    @Override
    public int heightY() {
        return this.heightY;
    }

    @Override
    public int lengthZ() {
        return this.lengthZ;
    }
}

