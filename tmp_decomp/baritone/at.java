/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.cache.IWaypoint;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.datatypes.ForWaypoints;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.utils.BetterBlockPos;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class at
extends Command {
    public at(a a2) {
        super(a2, "farm");
    }

    @Override
    public final void execute(String string, IArgConsumer object) {
        object.requireMax(2);
        int n2 = 0;
        BetterBlockPos betterBlockPos = null;
        if (object.has(1)) {
            n2 = object.getAs(Integer.class);
        }
        if (object.has(1)) {
            object = (IWaypoint[])object.getDatatypeFor(ForWaypoints.INSTANCE);
            switch (((IWaypoint[])object).length) {
                case 0: {
                    throw new CommandInvalidStateException("No waypoints found");
                }
                case 1: {
                    object = object[0];
                    break;
                }
                default: {
                    throw new CommandInvalidStateException("Multiple waypoints were found");
                }
            }
            betterBlockPos = object.getLocation();
        }
        this.baritone.getFarmProcess().farm(n2, betterBlockPos);
        this.logDirect("Farming");
    }

    @Override
    public final Stream<String> tabComplete(String string, IArgConsumer iArgConsumer) {
        return Stream.empty();
    }

    @Override
    public final String getShortDesc() {
        return "Farm nearby crops";
    }

    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The farm command starts farming nearby plants. It harvests mature crops and plants new ones.", "", "Usage:", "> farm - farms every crop it can find.", "> farm <range> - farm crops within range from the starting position.", "> farm <range> <waypoint> - farm crops within range from waypoint.");
    }
}

