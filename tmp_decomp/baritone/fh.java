/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.ca;

public final class fh
extends PathingCommand {
    public final ca a;

    public fh(Goal goal, PathingCommandType pathingCommandType, ca ca2) {
        super(goal, pathingCommandType);
        this.a = ca2;
    }
}

