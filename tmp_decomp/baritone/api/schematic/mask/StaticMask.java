/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.schematic.mask;

import baritone.api.schematic.mask.Mask;
import baritone.api.schematic.mask.PreComputedMask;
import baritone.api.schematic.mask.operator.BinaryOperatorMask;
import baritone.api.schematic.mask.operator.NotMask;
import baritone.api.utils.BooleanBinaryOperators;
import net.minecraft.world.level.block.state.BlockState;

public interface StaticMask
extends Mask {
    public boolean partOfMask(int var1, int var2, int var3);

    @Override
    default public boolean partOfMask(int n2, int n3, int n4, BlockState blockState) {
        return this.partOfMask(n2, n3, n4);
    }

    @Override
    default public StaticMask not() {
        return new NotMask.Static(this);
    }

    default public StaticMask union(StaticMask staticMask) {
        return new BinaryOperatorMask.Static(this, staticMask, BooleanBinaryOperators.OR);
    }

    default public StaticMask intersection(StaticMask staticMask) {
        return new BinaryOperatorMask.Static(this, staticMask, BooleanBinaryOperators.AND);
    }

    default public StaticMask xor(StaticMask staticMask) {
        return new BinaryOperatorMask.Static(this, staticMask, BooleanBinaryOperators.XOR);
    }

    default public StaticMask compute() {
        return new PreComputedMask(this);
    }
}

