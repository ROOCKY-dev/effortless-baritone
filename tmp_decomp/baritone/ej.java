/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import baritone.ey;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ej
extends ey {
    private boolean a;
    private boolean b;
    private int a;

    public ej(a a2) {
        super(a2);
    }

    @Override
    public final boolean isActive() {
        return ((ey)this).a.player() != null && ((ey)this).a.world() != null;
    }

    public final boolean a() {
        this.a = true;
        return this.b && this.a > 1;
    }

    @Override
    public final PathingCommand onTick(boolean bl2, boolean bl3) {
        this.b = bl3;
        if (this.a) {
            this.a = false;
            if (((ey)this).a.player().getDeltaMovement().multiply(1.0, 0.0, 1.0).length() < 1.0E-5) {
                ++this.a;
            }
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        this.a = 0;
        return new PathingCommand(null, PathingCommandType.DEFER);
    }

    @Override
    public final void onLostControl() {
    }

    @Override
    public final String displayName0() {
        return "inventory pauser";
    }

    @Override
    public final double priority() {
        return 5.1;
    }

    @Override
    public final boolean isTemporary() {
        return true;
    }
}

