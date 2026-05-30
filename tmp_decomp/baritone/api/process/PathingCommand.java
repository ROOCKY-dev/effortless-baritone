/*
 * Decompiled with CFR 0.152.
 */
package baritone.api.process;

import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommandType;
import java.util.Objects;

public class PathingCommand {
    public final Goal goal;
    public final PathingCommandType commandType;

    public PathingCommand(Goal goal, PathingCommandType pathingCommandType) {
        Objects.requireNonNull(pathingCommandType);
        this.goal = goal;
        this.commandType = pathingCommandType;
    }

    public String toString() {
        return String.valueOf((Object)this.commandType) + " " + String.valueOf(this.goal);
    }
}

