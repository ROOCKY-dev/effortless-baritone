/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.schematic;

import baritone.api.schematic.ISchematic;

public abstract class AbstractSchematic
implements ISchematic {
    public int x;
    public int y;
    public int z;

    public AbstractSchematic() {
        this(0, 0, 0);
    }

    public AbstractSchematic(int n2, int n3, int n4) {
        this.x = n2;
        this.y = n3;
        this.z = n4;
    }

    @Override
    public int widthX() {
        return this.x;
    }

    @Override
    public int heightY() {
        return this.y;
    }

    @Override
    public int lengthZ() {
        return this.z;
    }
}

