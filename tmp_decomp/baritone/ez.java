/*
 * Decompiled with CFR 0.152.
 */
package baritone;

import baritone.api.utils.IPlayerContext;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ez {
    final IPlayerContext a;
    boolean a;
    int a = 0;

    ez(IPlayerContext iPlayerContext) {
        this.a = iPlayerContext;
    }

    public final void a() {
        if (this.a.player() != null && this.a) {
            this.a.playerController().setHittingBlock(false);
            this.a.playerController().resetBlockRemoving();
            this.a = false;
        }
    }
}

