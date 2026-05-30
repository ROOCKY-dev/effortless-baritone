/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.pathing.goals.Goal;
import baritone.api.process.ICustomGoalProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.ey;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class dy
extends ey
implements ICustomGoalProcess {
    private Goal a;
    private Goal b;
    private a a;

    public dy(baritone.a a2) {
        super(a2);
    }

    @Override
    public final void setGoal(Goal goal) {
        this.a = goal;
        this.b = goal;
        if (((ey)this).a.getElytraProcess().isActive()) {
            ((ey)this).a.getElytraProcess().pathTo(goal);
        }
        if (this.a == baritone.dy$a.a) {
            this.a = baritone.dy$a.b;
        }
        if (this.a == baritone.dy$a.d) {
            this.a = baritone.dy$a.c;
        }
    }

    @Override
    public final void path() {
        this.a = baritone.dy$a.c;
    }

    @Override
    public final Goal getGoal() {
        return this.a;
    }

    @Override
    public final Goal mostRecentGoal() {
        return this.b;
    }

    @Override
    public final boolean isActive() {
        return this.a != baritone.dy$a.a;
    }

    @Override
    public final PathingCommand onTick(boolean bl2, boolean bl3) {
        switch (this.a.ordinal()) {
            case 1: {
                return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
            }
            case 2: {
                PathingCommand pathingCommand = new PathingCommand(this.a, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
                this.a = baritone.dy$a.d;
                return pathingCommand;
            }
            case 3: {
                if (bl2) {
                    this.onLostControl();
                    return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                if (this.a == null || this.a.isInGoal(((ey)this).a.playerFeet()) && this.a.isInGoal(((ey)this).a.a.a())) {
                    this.onLostControl();
                    if (((Boolean)baritone.a.a().disconnectOnArrival.value).booleanValue()) {
                        ((ey)this).a.world().disconnect();
                    }
                    if (((Boolean)baritone.a.a().notificationOnPathComplete.value).booleanValue()) {
                        this.logNotification("Pathing complete", false);
                    }
                    return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                return new PathingCommand(this.a, PathingCommandType.SET_GOAL_AND_PATH);
            }
        }
        throw new IllegalStateException("Unexpected state " + String.valueOf((Object)this.a));
    }

    @Override
    public final void onLostControl() {
        this.a = baritone.dy$a.a;
        this.a = null;
    }

    @Override
    public final String displayName0() {
        return "Custom Goal " + String.valueOf(this.a);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected static final class a
    extends Enum<a> {
        public static final /* enum */ a a = new a();
        public static final /* enum */ a b = new a();
        public static final /* enum */ a c = new a();
        public static final /* enum */ a d = new a();
        private static final /* synthetic */ a[] a;

        public static a[] values() {
            return (a[])a.clone();
        }

        public static a valueOf(String string) {
            return Enum.valueOf(a.class, string);
        }

        static {
            a = new a[]{a, b, c, d};
        }
    }
}

