/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction$Axis
 */
package baritone.api.command.datatypes;

import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.Locale;
import java.util.stream.Stream;
import net.minecraft.core.Direction;

public enum ForAxis implements IDatatypeFor<Direction.Axis>
{
    INSTANCE;


    @Override
    public final Direction.Axis get(IDatatypeContext iDatatypeContext) {
        return Direction.Axis.valueOf((String)iDatatypeContext.getConsumer().getString().toUpperCase(Locale.US));
    }

    @Override
    public final Stream<String> tabComplete(IDatatypeContext iDatatypeContext) {
        return new TabCompleteHelper().append(Stream.of(Direction.Axis.values()).map(Direction.Axis::getName).map(String::toLowerCase)).filterPrefix(iDatatypeContext.getConsumer().getString()).stream();
    }
}

