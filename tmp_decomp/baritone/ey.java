/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.process.IBaritoneProcess;
import baritone.api.utils.Helper;
import baritone.api.utils.IPlayerContext;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class ey
implements IBaritoneProcess,
Helper {
    public final a a;
    public final IPlayerContext a;

    public ey(a a2) {
        this.a = a2;
        this.a = a2.getPlayerContext();
    }

    @Override
    public boolean isTemporary() {
        return false;
    }
}

