/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.phys.AABB
 */
package baritone.api.selection;

import baritone.api.utils.BetterBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;

public interface ISelection {
    public BetterBlockPos pos1();

    public BetterBlockPos pos2();

    public BetterBlockPos min();

    public BetterBlockPos max();

    public Vec3i size();

    public AABB aabb();

    public ISelection expand(Direction var1, int var2);

    public ISelection contract(Direction var1, int var2);

    public ISelection shift(Direction var1, int var2);
}

