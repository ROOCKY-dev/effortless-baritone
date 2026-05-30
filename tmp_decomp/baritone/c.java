/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.a;
import baritone.api.behavior.IBehavior;
import baritone.api.utils.IPlayerContext;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class c
implements IBehavior {
    public final a a;
    public final IPlayerContext a;

    public c(a a2) {
        this.a = a2;
        this.a = a2.getPlayerContext();
    }
}

