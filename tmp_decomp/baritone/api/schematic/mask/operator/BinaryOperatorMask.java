/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockState
 */
package baritone.api.schematic.mask.operator;

import baritone.api.schematic.mask.AbstractMask;
import baritone.api.schematic.mask.Mask;
import baritone.api.schematic.mask.StaticMask;
import baritone.api.utils.BooleanBinaryOperator;
import net.minecraft.world.level.block.state.BlockState;

public final class BinaryOperatorMask
extends AbstractMask {
    private final Mask a;
    private final Mask b;
    private final BooleanBinaryOperator operator;

    public BinaryOperatorMask(Mask mask, Mask mask2, BooleanBinaryOperator booleanBinaryOperator) {
        super(Math.max(mask.widthX(), mask2.widthX()), Math.max(mask.heightY(), mask2.heightY()), Math.max(mask.lengthZ(), mask2.lengthZ()));
        this.a = mask;
        this.b = mask2;
        this.operator = booleanBinaryOperator;
    }

    @Override
    public final boolean partOfMask(int n2, int n3, int n4, BlockState blockState) {
        return this.operator.applyAsBoolean(BinaryOperatorMask.partOfMask(this.a, n2, n3, n4, blockState), BinaryOperatorMask.partOfMask(this.b, n2, n3, n4, blockState));
    }

    private static boolean partOfMask(Mask mask, int n2, int n3, int n4, BlockState blockState) {
        return n2 < mask.widthX() && n3 < mask.heightY() && n4 < mask.lengthZ() && mask.partOfMask(n2, n3, n4, blockState);
    }

    public static final class Static
    extends AbstractMask
    implements StaticMask {
        private final StaticMask a;
        private final StaticMask b;
        private final BooleanBinaryOperator operator;

        public Static(StaticMask staticMask, StaticMask staticMask2, BooleanBinaryOperator booleanBinaryOperator) {
            super(Math.max(staticMask.widthX(), staticMask2.widthX()), Math.max(staticMask.heightY(), staticMask2.heightY()), Math.max(staticMask.lengthZ(), staticMask2.lengthZ()));
            this.a = staticMask;
            this.b = staticMask2;
            this.operator = booleanBinaryOperator;
        }

        @Override
        public final boolean partOfMask(int n2, int n3, int n4) {
            return this.operator.applyAsBoolean(Static.partOfMask(this.a, n2, n3, n4), Static.partOfMask(this.b, n2, n3, n4));
        }

        private static boolean partOfMask(StaticMask staticMask, int n2, int n3, int n4) {
            return n2 < staticMask.widthX() && n3 < staticMask.heightY() && n4 < staticMask.lengthZ() && staticMask.partOfMask(n2, n3, n4);
        }
    }
}

