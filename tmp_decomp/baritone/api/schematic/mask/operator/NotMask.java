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
import net.minecraft.world.level.block.state.BlockState;

public final class NotMask
extends AbstractMask {
    private final Mask source;

    public NotMask(Mask mask) {
        super(mask.widthX(), mask.heightY(), mask.lengthZ());
        this.source = mask;
    }

    @Override
    public final boolean partOfMask(int n2, int n3, int n4, BlockState blockState) {
        return !this.source.partOfMask(n2, n3, n4, blockState);
    }

    public static final class Static
    extends AbstractMask
    implements StaticMask {
        private final StaticMask source;

        public Static(StaticMask staticMask) {
            super(staticMask.widthX(), staticMask.heightY(), staticMask.lengthZ());
            this.source = staticMask;
        }

        @Override
        public final boolean partOfMask(int n2, int n3, int n4) {
            return !this.source.partOfMask(n2, n3, n4);
        }
    }
}

