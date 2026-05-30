/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypePost;
import baritone.api.command.datatypes.RelativeCoordinate;
import baritone.api.utils.BetterBlockPos;
import java.util.stream.Stream;

public enum RelativeBlockPos implements IDatatypePost<BetterBlockPos, BetterBlockPos>
{
    INSTANCE;


    @Override
    public final BetterBlockPos apply(IDatatypeContext object, BetterBlockPos betterBlockPos) {
        if (betterBlockPos == null) {
            betterBlockPos = BetterBlockPos.ORIGIN;
        }
        object = object.getConsumer();
        return new BetterBlockPos((Double)object.getDatatypePost(RelativeCoordinate.INSTANCE, Double.valueOf(betterBlockPos.x)), (Double)object.getDatatypePost(RelativeCoordinate.INSTANCE, Double.valueOf(betterBlockPos.y)), (Double)object.getDatatypePost(RelativeCoordinate.INSTANCE, Double.valueOf(betterBlockPos.z)));
    }

    @Override
    public final Stream<String> tabComplete(IDatatypeContext object) {
        if ((object = object.getConsumer()).hasAny() && !object.has(4)) {
            while (object.has(2) && object.peekDatatypeOrNull(RelativeCoordinate.INSTANCE) != null) {
                object.get();
            }
            return object.tabCompleteDatatype(RelativeCoordinate.INSTANCE);
        }
        return Stream.empty();
    }
}

