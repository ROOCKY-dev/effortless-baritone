/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.schematic;

import baritone.api.schematic.ISchematic;

public class CompositeSchematicEntry {
    public final ISchematic schematic;
    public final int x;
    public final int y;
    public final int z;

    public CompositeSchematicEntry(ISchematic iSchematic, int n2, int n3, int n4) {
        this.schematic = iSchematic;
        this.x = n2;
        this.y = n3;
        this.z = n4;
    }
}

